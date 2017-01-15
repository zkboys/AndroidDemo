package com.zkboys.sdk.service.impl;

import com.zkboys.sdk.httpjson.BaseService;
import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceClient;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.MerchantInfo;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.service.MerchantService;

public class MerchantServiceImpl extends BaseService implements MerchantService {

    private static final String GET_MERCHANTS = "/v1/merchants.json";

    public MerchantServiceImpl(ServiceClient serviceClient) {
        super(serviceClient);
    }

    @Override
    public ServiceTicket getMerchants(Callback<Results<MerchantInfo>> callback) {
        return serviceClient.get(
                true,
                GET_MERCHANTS,
                null,
                null, callback
        );
    }
}
