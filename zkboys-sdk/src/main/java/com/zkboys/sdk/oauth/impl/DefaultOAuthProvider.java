package com.zkboys.sdk.oauth.impl;

import com.zkboys.sdk.common.TypeInfo;
import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.oauth.OAuthContext;
import com.zkboys.sdk.oauth.OAuthLifeCycleListener;
import com.zkboys.sdk.oauth.model.OAuthClient;
import com.zkboys.sdk.oauth.model.OAuthToken;
import com.zkboys.sdk.httpjson.ServiceClient;

import java.util.HashMap;
import java.util.Map;

/**
 * OAuth Provider 具体实现，可以获取到oauth相关的信息
 */
public class DefaultOAuthProvider extends AbstractOauthProvider {

    private static final String REFRESH_TOKEN_URL = "/oauth/refresh_token.json";

    public DefaultOAuthProvider(OAuthContext oauthContext, OAuthClient oauthClient, OAuthLifeCycleListener oauthLifecycleListener) {
        super(oauthContext, oauthClient, oauthLifecycleListener);
    }

    /**
     * 获取AccessToken
     *
     * @param serviceClient
     * @return
     * @throws ServiceException
     * @throws NetworkException
     */
    @Override
    public synchronized String getAccessToken(ServiceClient serviceClient) throws ServiceException, NetworkException {
        OAuthToken oauthToken = getOAuthContext().load();

        // 本地oauthToken不存在
        if (oauthToken == null) {
            throw new ServiceException(ServiceException.INVALID_ACCESS_TOKEN, ServiceException.INVALID_ACCESS_TOKEN_MESSAGE);
        }

        // 本地未过期
        if (System.currentTimeMillis() + 60 * 5000 < oauthToken.getExpiresIn()) {
            return oauthToken.getAccessToken();
        }

        // 本地token已经过期，根据refreshToken从远程获取
        boolean authenticated = false;
        String url = REFRESH_TOKEN_URL;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appKey", getOAuthClient().appKey);
        params.put("refreshToken", oauthToken.getRefreshToken());

        Map<String, String> headers = null;
        TypeInfo typeInfo = TypeInfo.createNormalType(OAuthToken.class);

        //同步请求token
        oauthToken = serviceClient.get(authenticated, url, params, headers, typeInfo);

        oauthToken.setExpiresIn(System.currentTimeMillis() + oauthToken.getExpiresIn() * 1000);

        getOAuthContext().store(oauthToken);

        return oauthToken.getAccessToken();
    }


}
