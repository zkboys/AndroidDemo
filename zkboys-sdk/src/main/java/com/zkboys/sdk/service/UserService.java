package com.zkboys.sdk.service;

import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.model.UserInfo;

public interface UserService {
    ServiceTicket getUserById(String userId, Callback<UserInfo> callback);

    ServiceTicket getAllUsers(Callback<Results<UserInfo>> callback);

    ServiceTicket getCurrentLoginUser(Callback<UserInfo> callback);
}
