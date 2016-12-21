package com.zkboys.sdk.oauth.impl;

import com.zkboys.sdk.oauth.OAuthContext;
import com.zkboys.sdk.oauth.OAuthLifeCycleListener;
import com.zkboys.sdk.oauth.OAuthProvider;
import com.zkboys.sdk.oauth.model.OAuthClient;

/**
 * Oauth Provider 可以获取到oauth相关的信息
 */
public abstract class AbstractOauthProvider implements OAuthProvider {

    protected OAuthContext oauthContext;
    protected OAuthClient oauthClient;
    protected OAuthLifeCycleListener oauthLifeCycleListener;

    public AbstractOauthProvider(OAuthContext oauthContext,
                                 OAuthClient oauthClient,
                                 OAuthLifeCycleListener oauthLifeCycleListener) {

        this.oauthContext = oauthContext;
        this.oauthClient = oauthClient;
        this.oauthLifeCycleListener = oauthLifeCycleListener;
    }

    @Override
    public OAuthContext getOAuthContext() {
        return oauthContext;
    }

    @Override
    public OAuthClient getOAuthClient() {
        return oauthClient;
    }

    @Override
    public OAuthLifeCycleListener getOAuthLifeCycleListener() {
        return oauthLifeCycleListener;
    }
}
