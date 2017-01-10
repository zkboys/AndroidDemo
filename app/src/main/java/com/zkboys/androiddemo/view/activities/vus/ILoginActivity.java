package com.zkboys.androiddemo.view.activities.vus;

import android.app.Application;

import com.zkboys.sdk.httpjson.ServiceTicket;

public interface ILoginActivity {

    Application getApplication();

    void addServiceTicket(ServiceTicket serviceTicket);

    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void clearUserNameError();

    void clearPasswordError();

    void showUserNameError(int stringId);

    void showPasswordError(int stringId);

    void focusUserName();

    void focusPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity();

    void showFailedError(String msg);

    void clearFailedError();
}