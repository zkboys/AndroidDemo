package com.zkboys.sdk.service.impl;

import com.zkboys.sdk.common.MapTool;
import com.zkboys.sdk.common.TypeInfo;
import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.httpjson.BaseService;
import com.zkboys.sdk.oauth.model.OAuthToken;
import com.zkboys.sdk.service.AuthorizeService;
import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceClient;
import com.zkboys.sdk.httpjson.ServiceTicket;

/**
 * 验证服务，用户登录，注册，退出登录等操作
 */
public class AuthorizeServiceImpl extends BaseService implements AuthorizeService {


    public static final String OAUTH_ACCESS_TOKEN_URL = "/oauth/sign_in.json";
    public static final String OAUTH_ACCESS_REGISTER_URL = "/oauth/register.json";

    public AuthorizeServiceImpl(ServiceClient serviceClient) {
        super(serviceClient);
    }

    /**
     * 同步获取access token 即：同步登录
     *
     * @param username
     * @param password
     * @param scope
     * @throws ServiceException
     * @throws NetworkException
     */
    @Override
    public void accessToken(String username, String password, String scope) throws ServiceException, NetworkException {
        OAuthToken oAuthToken = serviceClient.get(
                false,
                OAUTH_ACCESS_TOKEN_URL,
                MapTool.create()
                        .put("userName", username)
                        .put("passWord", password)
                        .put("scope", scope)
                        .value(),
                null,
                TypeInfo.createNormalType(OAuthToken.class)
        );

        // 将token存入本地，即记住用户，下次不用再登录
        oAuthToken.setExpiresIn(System.currentTimeMillis() + oAuthToken.getExpiresIn() * 1000);
        serviceClient.getOAuthProvider().getOAuthContext().store(oAuthToken);
    }

    /**
     * 异步获取access token 即：异步登录，单不存储用户token
     *
     * @param username
     * @param password
     * @param token
     * @param callback
     * @return
     */
    @Override
    public ServiceTicket accessToken(String username, String password, String token, Callback<OAuthToken> callback) {
        return serviceClient.get(
                false,
                OAUTH_ACCESS_TOKEN_URL,
                MapTool.create()
                        .put("userName", username)
                        .put("passWord", password)
                        .put("token", token)
                        .value(),
                null, callback
        );
    }

    /**
     * 异步注册
     *
     * @param username
     * @param password
     * @param callback
     * @return
     */
    @Override
    public ServiceTicket register(String username, String password, Callback<OAuthToken> callback) {
        return serviceClient.post(
                false,
                OAUTH_ACCESS_REGISTER_URL,
                MapTool.create()
                        .put("username", username)
                        .put("validCode", password)
                        .value(),
                null, callback);
    }

    /**
     * 退出登录
     *
     * @throws ServiceException
     * @throws NetworkException
     */
    @Override
    public void logout() throws ServiceException, NetworkException {
        serviceClient.getOAuthProvider().getOAuthContext().clean();
    }

}
