package com.zkboys.sdk.httpjson;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.zkboys.sdk.common.CallbackUtil;
import com.zkboys.sdk.common.MD5Util;
import com.zkboys.sdk.common.TypeInfo;
import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.oauth.OAuthProvider;
import com.zkboys.sdk.oauth.model.OAuthToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 默认的Service客户端实现
 */
public class HttpJsonServiceClient extends AbstractHttpJsonServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpJsonServiceClient.class);
    static final String CHARSET = "UTF-8";
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    // 单位秒
    private final static int CONNECT_TIMEOUT = 60;
    private final static int READ_TIMEOUT = 100;
    private final static int WRITE_TIMEOUT = 60;
    private final static String VERSION = "1";
    private OkHttpClient okHttpClient;

    private enum REQUEST_TYPE {GET, POST, PUT, DELETE,}

    public HttpJsonServiceClient(String baseUrl, Context context, OAuthProvider oauthProvider) {
        super(baseUrl, context, oauthProvider);
    }


    @Override
    public <T> ServiceTicket get(boolean authenticated, String url, Map<String, Object> params, Map<String, String> headers, Callback<T> callback) {
        return call(authenticated, url, params, headers, REQUEST_TYPE.GET, callback);
    }

    @Override
    public <T> T get(boolean authenticated, String url, Map<String, Object> params, Map<String, String> headers, TypeInfo typeInfo) throws NetworkException, ServiceException {
        return call(authenticated, url, params, headers, typeInfo, REQUEST_TYPE.GET);
    }

    @Override
    public <T> ServiceTicket post(boolean authenticated, String url, Map<String, Object> params, Map<String, String> headers, Callback<T> callback) {
        return call(authenticated, url, params, headers, REQUEST_TYPE.POST, callback);
    }

    @Override
    public <T> T post(boolean authenticated, String url, Map<String, Object> params, Map<String, String> headers, TypeInfo typeInfo) throws NetworkException, ServiceException {
        return call(authenticated, url, params, headers, typeInfo, REQUEST_TYPE.POST);
    }

    @Override
    public <T> ServiceTicket put(boolean authenticated, String url, Map<String, Object> params, Map<String, String> headers, Callback<T> callback) {
        return call(authenticated, url, params, headers, REQUEST_TYPE.PUT, callback);
    }

    @Override
    public <T> T put(boolean authenticated, String url, Map<String, Object> params, Map<String, String> headers, TypeInfo typeInfo) throws NetworkException, ServiceException {
        return call(authenticated, url, params, headers, typeInfo, REQUEST_TYPE.PUT);
    }

    @Override
    public <T> ServiceTicket delete(boolean authenticated, String url, Map<String, Object> params, Map<String, String> headers, Callback<T> callback) {
        return call(authenticated, url, params, headers, REQUEST_TYPE.DELETE, callback);
    }

    @Override
    public <T> T delete(boolean authenticated, String url, Map<String, Object> params, Map<String, String> headers, TypeInfo typeInfo) throws NetworkException, ServiceException {
        return call(authenticated, url, params, headers, typeInfo, REQUEST_TYPE.DELETE);
    }

    @Override
    public <T> ServiceTicket uploadFile(boolean authenticated, String url, Map<String, Object> params, File file, String filename, String fileUrl, Map<String, String> headers, Callback<T> callback) {
        return callWithFile(authenticated, url, params, file, filename, fileUrl, headers, callback);
    }

    @Override
    public <T> ServiceTicket uploadFiles(boolean authenticated, String name, Map<String, Object> params, Map<String, File> files, Map<String, String> headers, Callback<T> callback) {
        return null;
    }

    /**
     * 加签算法
     *
     * @param authenticated 是否需要登录
     * @param url           请求url
     * @param params        请求参数
     * @return
     * @throws ServiceException
     * @throws NetworkException
     */
    private Map<String, String> sign(boolean authenticated, String url, Map<String, Object> params) throws ServiceException, NetworkException {
        Map<String, String> signParams = new HashMap<>();

        signParams.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        signParams.put("ticket", UUID.randomUUID().toString());
        signParams.put("version", VERSION);
        signParams.put("appKey", this.oauthProvider.getOAuthClient().appKey);

        if (authenticated) {
            signParams.put("accessToken", this.oauthProvider.getAccessToken(this));
        }

        String paramsSignString = "";

        if (params != null) {
            List<String> paramsKeys = new ArrayList<>();

            for (String key : params.keySet()) {
                paramsKeys.add(key);
            }
            Collections.sort(paramsKeys);

            for (int i = 0; i < paramsKeys.size(); i++) {
                String key = paramsKeys.get(i);
                String value = params.get(key).toString();
                paramsSignString += key + value;
            }
        }
        List<String> list = new ArrayList<>(signParams.values());

        list.add(url);
        list.add(paramsSignString);
        list.add(this.oauthProvider.getOAuthClient().appSecret);
        Collections.sort(list);
        String str = "";

        for (int i = 0, size = list.size(); i < size; i++) {
            str += list.get(i);
            if (i + 1 < size) {
                str += "&";
            }
        }
        signParams.put("sign", MD5Util.md5(str).toUpperCase());

        return signParams;
    }

    private synchronized OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient()
                    .newBuilder()
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)        // 设置读取超时时间
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)      // 设置写的超时时间
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)  // 设置连接超时时间
                    .build();
        }
        return okHttpClient;
    }

    /**
     * 发送异步请求
     *
     * @param authenticated 是否需要登录
     * @param url           发送请求的url
     * @param params        请求参数
     * @param headers       自定义headers
     * @param callback      请求回调函数
     * @return ServiceTicket 请求的句柄，可以取消当前请求
     */
    public <T> ServiceTicket call(final boolean authenticated, final String url, final Map<String, Object> params,
                                  final Map<String, String> headers, final REQUEST_TYPE requestType, final Callback<T> callback) {

        final GeneralServiceTicket serviceTicket = new GeneralServiceTicket();

        if (!callback.onPreExecute(serviceTicket, params, headers)) {
            callback.onPostExecute();
            return serviceTicket;
        }
        // TODO 这个判断是什么鬼？
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
            final AsyncTask<Object, Integer, Object> requestTask;

            requestTask = new AsyncTask<Object, Integer, Object>() {
                T resultObj = null;

                private T executeCall() throws NetworkException, ServiceException, IOException {
                    // 获取callback的泛型类型
                    TypeInfo typeInfo = CallbackUtil.getCallbackGenericType(callback);
                    Call call = buildCall(authenticated, url, params, headers, requestType);

                    if (serviceTicket.isCanceled()) {
                        return null;
                    }
                    serviceTicket.setCall(call);

                    Response response = call.execute();

                    resultObj = HttpJsonResponseHelper.getResult(response, typeInfo);

                    return resultObj;
                }

                // 异步发送请求
                @Override
                protected Object doInBackground(Object... params) {
                    try {
                        return executeCall();
                    } catch (ServiceException e) {
                        if (ServiceException.ACCESS_TOKEN_HAS_EXPIRED == e.getCode()) { // access token 过期异常
                            // 将本地token设置成过期，再次发送请求，加签时，会通过OAuthProvider从新根据refresh_token 获取access token
                            OAuthToken oAuthToken = oauthProvider.getOAuthContext().load();
                            oAuthToken.setExpiresIn(0L);
                            oauthProvider.getOAuthContext().store(oAuthToken);
                            try {
                                return executeCall();
                            } catch (IOException e1) {
                                return new NetworkException(e1);
                            } catch (Exception e1) {
                                return e1;
                            }
                        }
                        return e;
                    } catch (IOException e) {
                        return new NetworkException(e);
                    } catch (Exception e) {
                        return e;
                    }
                }

                // 请求结束
                @Override
                protected void onPostExecute(Object result) {
                    if (serviceTicket.isCanceled()) {
                        return;
                    }
                    if (resultObj == result) {
                        callback.onSuccess(resultObj);
                    } else if (result instanceof ServiceException) {
                        ServiceException e = (ServiceException) result;
                        logger.error(e.getMessage(), e);

                        dealServiceException(e);

                        callback.onServiceException(e);
                    } else if (result instanceof NetworkException) {
                        NetworkException e = (NetworkException) result;
                        logger.error(e.getMessage(), e);

                        callback.onNetworkException((NetworkException) result);
                    } else if (result instanceof Exception) {
                        Exception e = (Exception) result;

                        logger.error(e.getMessage(), e);
                        callback.onServiceException(ServiceException.build((Throwable) result));
                    }
                    // 无论成功失败，都调用次方法，可以统一处理loading隐藏等公共操作
                    callback.onPostExecute();
                }
            };
            requestTask.execute();
        }
        return serviceTicket;
    }

    public <T> T call(boolean authenticated, String url, Map<String, Object> params,
                      Map<String, String> headers, final TypeInfo typeInfo, final REQUEST_TYPE requestType) throws NetworkException, ServiceException {
        try {
            Call call = buildCall(authenticated, url, params, headers, requestType);
            Response response = call.execute();
            return HttpJsonResponseHelper.getResult(response, typeInfo);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new NetworkException(e);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);

            if (ServiceException.ACCESS_TOKEN_HAS_EXPIRED == e.getCode()) {
                // 将本地token设置成过期，再次发送请求，加签时，会通过OAuthProvider从新根据refresh token 获取access token
                OAuthToken oAuthToken = oauthProvider.getOAuthContext().load();
                oAuthToken.setExpiresIn(0L);
                oauthProvider.getOAuthContext().store(oAuthToken);
                return call(authenticated, url, params, headers, typeInfo, requestType);
            }
            dealServiceException(e);

            throw e;
        }
    }

    public <T> ServiceTicket callWithFile(final boolean authenticated, final String url, Map<String, Object> parameters,
                                          final File file, final String filename, final String fileUrl, final Map<String, String> headers, final Callback<T> callback) {

        final GeneralServiceTicket serviceTicket = new GeneralServiceTicket();

        if (!callback.onPreExecute(serviceTicket, file, headers)) {
            callback.onPostExecute();
            return serviceTicket;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
            final AsyncTask<Object, Integer, Object> requestTask;
            requestTask = new AsyncTask<Object, Integer, Object>() {
                T resultObj = null;

                private T executeCall() throws NetworkException, ServiceException, IOException {
                    // 获取callback的泛型类型
                    TypeInfo typeInfo = CallbackUtil.getCallbackGenericType(callback);
                    Call call = buildCallByFile(authenticated, url, filename, fileUrl, file, headers, typeInfo);
                    if (serviceTicket.isCanceled()) {
                        return null;
                    }

                    HttpProgressRequestBody httpProgressRequestBody = new HttpProgressRequestBody(call.request().body(), new HttpProgressRequestBody.ProgressListener() {
                        @Override
                        public void onProgress(long len, long size) {
                            Log.e("size", "len = " + len + " ;size = " + size);

                            double p = BigDecimal.valueOf(Long.valueOf(len)).divide(BigDecimal.valueOf(Long.valueOf(size)), 2, BigDecimal.ROUND_HALF_EVEN).doubleValue();

                            int progress = (int) (p * 100);

                            Log.e("size", "progress = " + progress);

                            publishProgress(progress);
                        }
                    });

                    call = getOkHttpClient().newCall(call.request().newBuilder().post(httpProgressRequestBody).build());
                    serviceTicket.setCall(call);
                    Response response = call.execute();
                    resultObj = HttpJsonResponseHelper.getResult(response, typeInfo);
                    return resultObj;
                }

                @Override
                protected Object doInBackground(Object... params) {
                    try {
                        return executeCall();
                    } catch (ServiceException e) {
                        if (ServiceException.ACCESS_TOKEN_HAS_EXPIRED == e.getCode()) {
                            OAuthToken oAuthToken = oauthProvider.getOAuthContext().load();
                            oAuthToken.setExpiresIn(0L);
                            oauthProvider.getOAuthContext().store(oAuthToken);

                            try {
                                return executeCall();
                            } catch (IOException e1) {
                                return new NetworkException(e1);
                            } catch (Exception e1) {
                                return e1;
                            }
                        }
                        return e;
                    } catch (IOException e) {
                        return new NetworkException(e);
                    } catch (Exception e) {
                        return e;
                    }
                }

                @Override
                protected void onPostExecute(Object result) {
                    if (serviceTicket.isCanceled()) {
                        return;
                    }
                    if (resultObj == result) {
                        callback.onSuccess(resultObj);
                    } else if (result instanceof ServiceException) {
                        ServiceException e = (ServiceException) result;
                        logger.error(e.getMessage(), e);

                        dealServiceException(e);

                        callback.onServiceException(e);

                    } else if (result instanceof NetworkException) {
                        NetworkException e = (NetworkException) result;
                        logger.error(e.getMessage(), e);
                        callback.onNetworkException((NetworkException) result);
                    } else if (result instanceof Exception) {
                        Exception e = (Exception) result;
                        logger.error(e.getMessage(), e);
                        callback.onServiceException(ServiceException.build((Throwable) result));
                    }
                    // 无论成功失败，都调用次方法，可以统一处理loading隐藏等公共操作
                    callback.onPostExecute();
                }

                /**
                 * 进度更新
                 * @param values 更新数值
                 */
                @Override
                protected void onProgressUpdate(Integer... values) {
                    if (serviceTicket.isCanceled()) {
                        super.onProgressUpdate(values);
                        return;
                    }
                    callback.onProgressUpdate(values);
                }
            };
            requestTask.execute();
        }
        return serviceTicket;
    }

    public <T> ServiceTicket callWithFiles(boolean authenticated, String url, Map<String, Object> parameters,
                                           Map<String, File> files, Map<String, String> headers, Callback<T> callback) {
        return null;
    }

    /**
     * 处理service异常
     *
     * @param e 异常对象
     */
    private void dealServiceException(ServiceException e) {
        if (ServiceException.INVALID_ACCESS_TOKEN == e.getCode()) {
            oauthProvider.getOAuthContext().clean();
            oauthProvider.getOAuthLifeCycleListener().onAccessTokenInvalid();
        }

        if (ServiceException.INVALID_REFRESH_TOKEN == e.getCode()
                || ServiceException.REFRESH_TOKEN_HAS_EXPIRED == e.getCode()) {
            oauthProvider.getOAuthContext().clean();
            oauthProvider.getOAuthLifeCycleListener().onRefreshTokenExpired();
        }

        if (ServiceException.USER_NOT_FOUND == e.getCode()
                || ServiceException.USER_IS_INVALID == e.getCode()) {
            oauthProvider.getOAuthContext().clean();
            oauthProvider.getOAuthLifeCycleListener().onUserBlocked();
        }
    }

    private Call buildCall(boolean authenticated, String url, Map<String, Object> params,
                           Map<String, String> headers, REQUEST_TYPE requestType) throws NetworkException, ServiceException {
        Request request = getRequest(authenticated, url, params, headers, requestType);
        return getOkHttpClient().newCall(request);
    }

    private Call buildCallByFile(boolean authenticated, String name, String fileName, String fileUrl, File file,
                                 Map<String, String> headers, final TypeInfo typeInfo) throws NetworkException, ServiceException {
        Request request = getRequestByFile(authenticated, name, fileName, fileUrl, file, headers);
        return getOkHttpClient().newCall(request);
    }

    private Request getRequest(boolean authenticated, String url, Map<String, Object> params, Map<String, String> headers, REQUEST_TYPE requestType) throws ServiceException, NetworkException {
        String paramsJsonString = params == null ? "{}" : JSONObject.toJSONString(params);
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, paramsJsonString);
        Request.Builder builder = new Request.Builder();

        switch (requestType) {
            case GET:
                String paramsString = "";
                if (null != params) {
                    ArrayList<String> paramsArray = new ArrayList<>();
                    for (String key : params.keySet()) {
                        String value = params.get(key).toString();
                        paramsArray.add(key + "=" + value);
                    }
                    for (int i = 0; i < paramsArray.size(); i++) {
                        paramsString += paramsArray.get(i);
                        if (i != paramsArray.size() - 1) {
                            paramsString += "&";
                        }
                    }
                }
                if (!url.contains("?")) {
                    url += "?" + paramsString;
                } else {
                    url += "&" + paramsString;
                }
                System.out.println(url);
                builder.url(this.baseUrl + url);
                break;
            case POST:
                builder.url(this.baseUrl + url).post(body);
                break;

            case PUT:
                builder.url(this.baseUrl + url).put(body);
                break;

            case DELETE:
                builder.url(this.baseUrl + url).delete(body);
                break;
        }

        Map<String, String> signHeader = sign(authenticated, url, params);

        if (headers != null) {
            signHeader.putAll(headers);
        }
        // 加签相关的内容放到headers中
        builder.headers(Headers.of(signHeader));

        // 控制不需要缓存  全部走网络数据
        /*
        final CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.noCache();
        CacheControl cache = cacheBuilder.build();
        builder.addHeader("Connection", "close");
        builder.cacheControl(cache);
        */

        return builder.build();
    }

    /**
     * 发送文件的request
     *
     * @param authenticated
     * @param url
     * @param fileName
     * @param fileUrl
     * @param file
     * @param headers
     * @return
     * @throws ServiceException
     * @throws NetworkException
     */
    private Request getRequestByFile(boolean authenticated, String url, String fileName, String fileUrl, File file, Map<String, String> headers) throws ServiceException, NetworkException {
        // TODO 只是发送图片，有局限性，只能发png的，这里应该根据文件名的后缀，判断发送什么类型的文件，不应该只是图片，而且不应该只是png根式
        RequestBody requestBody = new MultipartBody
                .Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fileName", fileName)
                .addFormDataPart("image", fileUrl, RequestBody.create(MEDIA_TYPE_PNG, file))
                .build();

        Request.Builder builder = new Request.Builder()
                .url(this.baseUrl + url);

        Map<String, String> signHeader = sign(authenticated, url, null);
        if (headers != null) {
            signHeader.putAll(headers);
        }

        builder.headers(Headers.of(signHeader));
        builder.post(requestBody);

        return builder.build();
    }

}
