package com.zkboys.androiddemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.zkboys.androiddemo.common.C;

/**
 * SharedPreferences 方式,本地存储数据
 * 单例模式
 */
public class PreferenceUtil {
    private Context context;
    private static PreferenceUtil instance;
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;

    private PreferenceUtil(Context ctx) {
        context = ctx;
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPref.edit();
    }

    public static PreferenceUtil getInstance(Context ctx) {
        if (instance == null) {
            if (ctx != null)
                instance = new PreferenceUtil(ctx);
        }
        return instance;
    }

    public void setUserName(String userName) {
        mEditor.putString(C.USER.USER_NAME, userName);
        mEditor.commit();
    }

    public String getUserName() {
        return mSharedPref.getString(C.USER.USER_NAME, "");
    }

    public void setApplicationLogin(boolean login) {
        mEditor.putBoolean(C.APPLICATION_LOGIN, login);
        mEditor.commit();
    }

    public boolean getApplicationLogin() {
        return mSharedPref.getBoolean(C.APPLICATION_LOGIN, true);
    }

}
