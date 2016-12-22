package com.zkboys.sdk.service;

import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.ClientVersionInfo;

public interface AppService {
    ServiceTicket checkVersion(String appType, Integer clientVersion, Callback<ClientVersionInfo> callback);
}
