package com.zkboys.androiddemo.view.activities.vus;

import android.app.Application;

import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.MerchantInfo;

import java.util.List;

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

    void doNext();

    void showFailedError(String msg);

    void clearFailedError();

    void toMain();

    void toSelectStore(List<MerchantInfo> merchants);

    void showNoStoreError();
}