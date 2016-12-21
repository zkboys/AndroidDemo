package com.zkboys.sdk.oauth.impl;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.zkboys.sdk.oauth.OAuthContext;
import com.zkboys.sdk.oauth.model.OAuthToken;

/**
 * OAuth本地存储相关操作
 */
public class DefaultOAuthContext implements OAuthContext {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String EXPIRED_AT = "expired_at";

    private Context context;

    public DefaultOAuthContext(Context context) {
        this.context = context;
    }

    @Override
    public OAuthToken load() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("oauth", Activity.MODE_PRIVATE);

        String accessToken = sharedPreferences.getString(ACCESS_TOKEN, "");
        String refreshToken = sharedPreferences.getString(REFRESH_TOKEN, "");
        Long expiredAt = sharedPreferences.getLong(EXPIRED_AT, 0L);

        if ("".equals(accessToken) || "".equals(refreshToken) || 0L == expiredAt) {
            return null;
        }
        return new OAuthToken(accessToken, expiredAt, refreshToken);
    }

    @Override
    public void store(OAuthToken oauthToken) {
        if (oauthToken == null) {
            return;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("oauth", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(ACCESS_TOKEN, oauthToken.getAccessToken());
        editor.putString(REFRESH_TOKEN, oauthToken.getRefreshToken());
        editor.putLong(EXPIRED_AT, oauthToken.getExpiresIn());
        editor.commit();
    }

    @Override
    public void clean() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("oauth", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(ACCESS_TOKEN);
        editor.remove(REFRESH_TOKEN);
        editor.remove(EXPIRED_AT);
        editor.commit();
    }
}
