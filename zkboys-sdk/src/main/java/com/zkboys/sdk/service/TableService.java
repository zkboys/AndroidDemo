package com.zkboys.sdk.service;

import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.model.TablesInfo;

public interface TableService {

    ServiceTicket getTables(String mchId, String storeId, Callback<Results<TablesInfo>> callback);
}
