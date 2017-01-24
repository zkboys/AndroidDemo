package com.zkboys.androiddemo.view.login;

import com.zkboys.sdk.httpjson.ServiceTicket;

public interface ILoginPresenter {
    ServiceTicket login();
    ServiceTicket getMerchants();
    ServiceTicket getCurrentLoginUser();
}
