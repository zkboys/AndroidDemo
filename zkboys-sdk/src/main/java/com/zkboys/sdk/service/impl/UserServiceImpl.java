package com.zkboys.sdk.service.impl;

import com.zkboys.sdk.common.MapTool;
import com.zkboys.sdk.httpjson.BaseService;
import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceClient;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.model.UserInfo;
import com.zkboys.sdk.service.UserService;

public class UserServiceImpl extends BaseService implements UserService {

    private static final String GET_USER_INFO = "/v1/user.json";
    private static final String GET_ALL_USERS = "/v1/users.json";
    private static final String GET_CURRENT_LOGIN_USER = "/v1/current_user.json";

    public UserServiceImpl(ServiceClient serviceClient) {
        super(serviceClient);
    }

    @Override
    public ServiceTicket getUserById(String userId, Callback<UserInfo> callback) {
        return serviceClient.get(
                true,
                GET_USER_INFO,
                MapTool.create()
                        .put("userId", userId)
                        .value(),
                null, callback
        );
    }

    @Override
    public ServiceTicket getAllUsers(String mchId, String storeId, Callback<Results<UserInfo>> callback) {
        return serviceClient.get(
                true,
                GET_ALL_USERS,
                MapTool.create()
                        .put("mchId", mchId)
                        .put("storeId", storeId)
                        .value(),
                null, callback
        );
    }

    @Override
    public ServiceTicket getCurrentLoginUser(Callback<UserInfo> callback) {
        return serviceClient.get(
                true,
                GET_CURRENT_LOGIN_USER,
                null,
                null, callback
        );
    }

}
