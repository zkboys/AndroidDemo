package com.zkboys.androiddemo.view.main;

import com.zkboys.sdk.httpjson.ServiceTicket;

public interface IMainPresenter {
    void logout();

    ServiceTicket getTables();
}
