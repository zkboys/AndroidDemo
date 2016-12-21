package com.zkboys.sdk.service;


import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.oauth.model.OAuthToken;

/**
 * 处理登录，用户有效性验证相关的service
 */
public interface AuthorizeService {

    /**
     * 登录验证
     *
     * @param username
     * @param validCode
     * @param scope
     * @throws ServiceException
     * @throws NetworkException
     */
    void accessToken(String username, String validCode, String scope) throws ServiceException, NetworkException;

    /**
     * 异步登录验证
     *
     * @param username
     * @param validCode
     * @param token
     * @param callback
     * @return
     */
    ServiceTicket accessToken(String username, String validCode, String token, Callback<OAuthToken> callback);

    /**
     * 注册
     *
     * @param username
     * @param validCode
     * @param callback
     * @return
     */
    ServiceTicket register(String username, String validCode, Callback<OAuthToken> callback);

    /**
     * 退出
     *
     * @throws ServiceException
     * @throws NetworkException
     */
    void logout() throws ServiceException, NetworkException;

}
