package com.zkboys.sdk.oauth;

import com.zkboys.sdk.exception.NetworkException;
import com.zkboys.sdk.exception.ServiceException;
import com.zkboys.sdk.oauth.model.OAuthClient;
import com.zkboys.sdk.httpjson.ServiceClient;

/**
 * 应用的OAuth提供者，负责从来往的开放平台中获取Request_Token/Access_Token
 */
public interface OAuthProvider {

    String getAccessToken(ServiceClient serviceClient) throws ServiceException, NetworkException;

    OAuthContext getOAuthContext();

    OAuthClient getOAuthClient();

    OAuthLifeCycleListener getOAuthLifeCycleListener();

}
