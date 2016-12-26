package com.zkboys.sdk.service.impl;

import com.zkboys.sdk.common.MapTool;
import com.zkboys.sdk.httpjson.BaseService;
import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceClient;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.ClientVersionInfo;
import com.zkboys.sdk.service.AppService;

public class AppServiceImpl extends BaseService implements AppService {

    public static final String APP_CLIENT_CHECK_VERSION = "/version.json";

    public AppServiceImpl(ServiceClient serviceClient) {
        super(serviceClient);
    }

    @Override
    public ServiceTicket checkVersion(String appType, Integer clientVersion, Callback<ClientVersionInfo> callback) {
        return serviceClient.get(
                false,
                APP_CLIENT_CHECK_VERSION,
                MapTool.create()
                        .put("appType", appType)
                        .put("clientVersion", clientVersion)
                        .value(),
                null, callback
        );
    }
}
