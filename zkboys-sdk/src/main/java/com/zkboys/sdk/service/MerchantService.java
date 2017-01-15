package com.zkboys.sdk.service;

import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.MerchantInfo;
import com.zkboys.sdk.model.Results;

public interface MerchantService {
    ServiceTicket getMerchants(Callback<Results<MerchantInfo>> callback);
}
