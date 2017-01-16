package com.zkboys.sdk.service;

import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.model.TableRegionInfo;

public interface TableService {

    ServiceTicket getRegions(Callback<Results<TableRegionInfo>> callback);

    ServiceTicket cleanTable(String tableId, Callback<Boolean> callback);
}
