package com.zkboys.sdk.service.impl;

import com.zkboys.sdk.common.MapTool;
import com.zkboys.sdk.httpjson.BaseService;
import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceClient;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.model.TablesInfo;
import com.zkboys.sdk.service.TableService;

public class TableServiceImpl extends BaseService implements TableService {

    private static final String GET_TABLES = "/v1/tables.json";

    public TableServiceImpl(ServiceClient serviceClient) {
        super(serviceClient);
    }


    @Override
    public ServiceTicket getTables(String mchId, String storeId, Callback<Results<TablesInfo>> callback) {
        return serviceClient.get(
                true,
                GET_TABLES,
                MapTool.create()
                        .put("mchId", mchId)
                        .put("storeId", storeId)
                        .value(),
                null, callback
        );
    }
}
