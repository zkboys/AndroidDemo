package com.zkboys.androiddemo.presenter.vus;

import com.zkboys.sdk.httpjson.ServiceTicket;

public interface IMainPresenter {
    void logout();

    ServiceTicket getTables();
}
