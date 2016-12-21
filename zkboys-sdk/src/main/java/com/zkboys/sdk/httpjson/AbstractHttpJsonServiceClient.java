package com.zkboys.sdk.httpjson;

import android.content.Context;

import com.zkboys.sdk.oauth.OAuthProvider;

/**
 * 抽象客户端类，为具体实现做了一些必要的准备
 */
public abstract class AbstractHttpJsonServiceClient implements ServiceClient {

    protected String baseUrl;
    protected Context context;
    protected OAuthProvider oauthProvider;

    public AbstractHttpJsonServiceClient(String baseUrl, Context context, OAuthProvider oauthProvider) {
        this.baseUrl = baseUrl;
        this.context = context;
        this.oauthProvider = oauthProvider;
    }

    public OAuthProvider getOAuthProvider() {
        return oauthProvider;
    }

}
