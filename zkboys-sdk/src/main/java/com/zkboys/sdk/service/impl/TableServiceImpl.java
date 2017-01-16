package com.zkboys.sdk.service.impl;

import com.zkboys.sdk.common.MapTool;
import com.zkboys.sdk.httpjson.BaseService;
import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceClient;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.model.TableRegionInfo;
import com.zkboys.sdk.service.TableService;

public class TableServiceImpl extends BaseService implements TableService {

    private static final String GET_TABLE_REGIONS = "/v1/table_regions.json";

    private static final String CLEAN_TABLE = "/v1/clean_table.json";

    public TableServiceImpl(ServiceClient serviceClient) {
        super(serviceClient);
    }


    @Override
    public ServiceTicket getRegions(String mchId, String storeId, Callback<Results<TableRegionInfo>> callback) {
        return serviceClient.get(
                true,
                GET_TABLE_REGIONS,
                MapTool.create()
                        .put("mchId", mchId)
                        .put("storeId", storeId)
                        .value(),
                null, callback
        );
    }

    @Override
    public ServiceTicket cleanTable(String tableId, Callback<Boolean> callback) {
        return serviceClient.put(
                true,
                CLEAN_TABLE,
                MapTool.create()
                        .put("tableId", tableId)
                        .value(),
                null, callback
        );
    }
}
