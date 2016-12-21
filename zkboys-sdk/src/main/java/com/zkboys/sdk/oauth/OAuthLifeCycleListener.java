package com.zkboys.sdk.oauth;

/**
 * OAuth 生命周期监听器
 * Created by freeway on 16/5/14.
 */
public interface OAuthLifeCycleListener {

    /**
     * 发生在当会话中的AccessToken不合法时。
     */
    void onAccessTokenInvalid();

    /**
     * 用户被封号
     */
    void onUserBlocked();

    /**
     * 发生在当会话中的RefreshToken失效时。出现这种情况，系统就会变成退出登录的状态需要重新登录
     */
    void onRefreshTokenExpired();
}
