package com.zkboys.sdk.service.impl;


import com.zkboys.sdk.common.MapTool;
import com.zkboys.sdk.httpjson.BaseService;
import com.zkboys.sdk.model.Result;
import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceClient;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.service.SmsService;

public class SmsServiceImpl extends BaseService implements SmsService {

    public static final String SMS_SEND_MOBILE_MSG = "/sms/add.json";


    public SmsServiceImpl(ServiceClient serviceClient) {
        super(serviceClient);
    }

    @Override
    public ServiceTicket sendMobileMessage(String mobile, Callback<Result<Boolean>> callback) {
        return serviceClient.call(false, SMS_SEND_MOBILE_MSG,
                MapTool.create().put("mobile", mobile).value(),
                null, callback);
    }
}
