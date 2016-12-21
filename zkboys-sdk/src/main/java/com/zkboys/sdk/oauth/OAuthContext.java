package com.zkboys.sdk.oauth;


import com.zkboys.sdk.oauth.model.OAuthToken;

/**
 * 客户端OAuth存储相关操作
 */
public interface OAuthContext {

    /**
     * 从客户端本地存储中获取OAuthToken
     *
     * @return
     */
    OAuthToken load();

    /**
     * 将OAuthToken存储到客户端本地存储中
     *
     * @param oauthToken
     */
    void store(OAuthToken oauthToken);

    /**
     * 清除 客户端本地存储中的OAuthToken
     */
    void clean();

}
