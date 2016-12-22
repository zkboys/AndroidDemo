package com.zkboys.androiddemo.view.activities.vus;

import android.app.Application;

import com.zkboys.sdk.httpjson.ServiceTicket;

public interface IUserLoginView {

    Application getApplication();

    void addServiceTicket(ServiceTicket serviceTicket);

    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity();

    void showFailedError(String msg);


}