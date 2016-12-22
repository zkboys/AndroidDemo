package com.zkboys.sdk;

import android.content.Context;

import com.zkboys.sdk.httpjson.BaseService;
import com.zkboys.sdk.httpjson.HttpJsonServiceClient;
import com.zkboys.sdk.httpjson.ServiceClient;
import com.zkboys.sdk.oauth.OAuthContext;
import com.zkboys.sdk.oauth.OAuthLifeCycleListener;
import com.zkboys.sdk.oauth.OAuthProvider;
import com.zkboys.sdk.oauth.impl.DefaultOAuthProvider;
import com.zkboys.sdk.oauth.model.OAuthClient;
import com.zkboys.sdk.service.AppService;
import com.zkboys.sdk.service.AuthorizeService;
import com.zkboys.sdk.service.DeliverymanService;
import com.zkboys.sdk.service.impl.AppServiceImpl;
import com.zkboys.sdk.service.impl.AuthorizeServiceImpl;
import com.zkboys.sdk.service.impl.DeliverymanServiceImp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * API工厂，负责获取来往API Service包括 业务Service 和 OAuthProvider <br/>
 * <br/>
 * 请保持该类为单例，使用该类之前需要调用 init 方法 <br/>
 * <br/>
 * 该类的初始化需要使用方提供以下初始化参数<br/>
 * <br/>
 * appKey: API使用方申请的APP key <br/>
 * appSecret: API使用方申请的APP Secret <br/>
 * scopes: 以空格分隔的权限列表，若不传递此参数，代表请求用户的默认权限
 *
 * @author freeway
 */
public class ZKBoysSDK {

    private static final Logger logger = LoggerFactory.getLogger(HttpJsonServiceClient.class);
    private ServiceClient serviceClient;

    private static final String BASE_URL = "http://192.168.0.237:8001";
    private static final String TEST_BASE_URL = "http://192.168.0.237:8001";

    /**
     * 服务实例容器，用来缓存服务实例，不必每次都创建新的实例
     */
    private Map<String, Object> instances = new ConcurrentHashMap<String, Object>();

    public ZKBoysSDK(Context context, OAuthClient oauthClient, OAuthContext oauthContext,
                     OAuthLifeCycleListener oauthLifecycleListener, boolean isDebug) {
        if (oauthContext == null) {
            throw new RuntimeException("Pls provide OAuthContext!!!");
        }
        if (oauthClient == null) {
            throw new RuntimeException("Pls provide OAuthClient!!!");
        }
        if (oauthClient.appKey == null || oauthClient.appKey.equals("")) {
            throw new RuntimeException("Pls provide appKey!!!");
        }
        if (oauthClient.appSecret == null || oauthClient.appSecret.equals("")) {
            throw new RuntimeException("Pls provide appSecret!!!");
        }
        if (oauthClient.scopes == null) {
            oauthClient.scopes = "";
        }
        OAuthProvider oauthProvider = new DefaultOAuthProvider(oauthContext, oauthClient, oauthLifecycleListener);
        serviceClient = new HttpJsonServiceClient(isDebug ? TEST_BASE_URL : BASE_URL, context, oauthProvider);
    }

    protected <T extends BaseService> T getService(Class<T> serviceType) {
        String serviceName = serviceType.getName();
        Object instance = instances.get(serviceName);

        if (instance != null) {
            return serviceType.cast(instance);
        }

        try {
            instance = serviceType.getConstructor(ServiceClient.class).newInstance(serviceClient);
            instances.put(serviceName, instance);

            return serviceType.cast(instance);
        } catch (Exception e) {
            logger.error("service instance failure.", e);
            return null;
        }
    }

    public AuthorizeService getAuthorizeService() {
        return getService(AuthorizeServiceImpl.class);
    }

    public DeliverymanService getDeliverymanService() {
        return getService(DeliverymanServiceImp.class);
    }

    public AppService getAppService() {
        return getService(AppServiceImpl.class);
    }
}
