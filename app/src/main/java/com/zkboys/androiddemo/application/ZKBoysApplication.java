package com.zkboys.androiddemo.application;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zkboys.androiddemo.utils.PreferenceUtil;
import com.zkboys.androiddemo.view.activities.LoginActivity;
import com.zkboys.sdk.ZKBoysSDK;
import com.zkboys.sdk.oauth.impl.DefaultOAuthContext;
import com.zkboys.sdk.oauth.model.OAuthClient;
import com.zkboys.sdk.oauth.OAuthContext;
import com.zkboys.sdk.oauth.OAuthLifeCycleListener;


public class ZKBoysApplication extends Application {

    ZKBoysSDK expressSDK;
    OAuthClient oAuthClient;
    OAuthContext oAuthContext;
    OAuthLifeCycleListener oAuthLifeCycleListener;

    @Override
    public void onCreate() {
        super.onCreate();
        oAuthClient = new OAuthClient();
        oAuthClient.appKey = "1000";
        oAuthClient.appSecret = "3b4fd56df5964909b45a2640a4317be0";
        oAuthClient.scopes = "base";
        oAuthContext = new DefaultOAuthContext(this);
        oAuthLifeCycleListener = new OAuthLifeCycleListener() {
            @Override
            public void onAccessTokenInvalid() {
                Log.d("ZKBoysApplication", "onAccessTokenInvalid()");
                oAuthContext.clean();
                reLogin();
            }

            @Override
            public void onUserBlocked() {
                Log.d("ZKBoysApplication", "onUserBlocked()");

            }

            @Override
            public void onRefreshTokenExpired() {
                Log.d("ZKBoysApplication", "onRefreshTokenExpired()");
            }
        };

        expressSDK = new ZKBoysSDK(getApplicationContext(), oAuthClient, oAuthContext, oAuthLifeCycleListener, false);

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

        Fresco.initialize(this);

    }

    public ZKBoysSDK getZKBoysSDK() {
        return expressSDK;
    }

    public OAuthContext getOAuthContext() {
        return oAuthContext;
    }

    public synchronized void reLogin() {

        boolean result = PreferenceUtil.getInstance(this).getApplicationLogin();

        if (result) {

            PreferenceUtil.getInstance(this).setApplicationLogin(false);

            Intent intent = new Intent();
            intent.setClass(getBaseContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
