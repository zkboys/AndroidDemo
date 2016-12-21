package com.zkboys.sdk.service;

import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.Result;

public interface SmsService {
    ServiceTicket sendMobileMessage(String mobile, Callback<Result<Boolean>> callback);
}
