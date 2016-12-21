package com.zkboys.sdk.httpjson;

import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;

import java.util.Map;

/**
 * SDK的回调处理接口
 *
 * @param <T>
 * @author freeway
 */
public interface Callback<T> {

    /**
     * 业务调用之前的处理（如：显示提醒、参数验证）
     *
     * @param ticket
     */
    boolean onPreExecute(ServiceTicket ticket, Object object, Map<String, String> headers);

    /**
     * 业务调用之后的处理
     */
    void onPostExecute();

    /**
     * 服务调用中回调处理（如：显示进度提醒等）
     */
    void onProgressUpdate(Integer... values);

    /**
     * 处理业务的正常返回
     *
     * @param result
     */
    void onSuccess(T result);

    /**
     * 处理业务调用异常
     *
     * @param exception
     */
    void onServiceException(ServiceException exception);

    /**
     * 处理网络异常
     *
     * @param exception
     */
    void onNetworkException(NetworkException exception);


}
