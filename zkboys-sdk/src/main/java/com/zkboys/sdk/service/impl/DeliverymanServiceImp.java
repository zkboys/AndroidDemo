package com.zkboys.sdk.service.impl;

import com.zkboys.sdk.common.MapTool;
import com.zkboys.sdk.httpjson.BaseService;
import com.zkboys.sdk.model.ClientVersionInfo;
import com.zkboys.sdk.model.DeliverymanInfo;
import com.zkboys.sdk.model.DeliverymanLittleInfo;
import com.zkboys.sdk.model.DeliverymanRequest;
import com.zkboys.sdk.model.Result;
import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.service.DeliverymanService;
import com.zkboys.sdk.httpjson.ServiceClient;
import com.zkboys.sdk.httpjson.ServiceTicket;

public class DeliverymanServiceImp extends BaseService implements DeliverymanService {


    public static final String OAUTH_DELIVERY_FIND_DELIVERY = "/dm/get.json";
    public static final String OAUTH_DELIVERY_FIND_DELIVERY_INFO = "/dm/info/get.json";
    public static final String OAUTH_DELIVERY_UPDATEINFO = "/dm/info/update.json";
    public static final String OAUTH_DELIVERY_UPDATE_WORK = "/dm/work/update.json";
    public static final String OAUTH_DELIVERY_UPDATE_POSITION = "/dm/postion/update.json";
    public static final String APP_CLIENT_CHECK_VERSION = "/version.json";


    public DeliverymanServiceImp(ServiceClient serviceClient) {
        super(serviceClient);
    }

    @Override
    public ServiceTicket findDeliveryman(Callback<DeliverymanLittleInfo> callback) {
        return serviceClient.call(true, OAUTH_DELIVERY_FIND_DELIVERY, null,
                null, callback);
    }

    @Override
    public ServiceTicket findDeliverymanInfo(Callback<DeliverymanInfo> callback) {
        return serviceClient.call(true, OAUTH_DELIVERY_FIND_DELIVERY_INFO, null,
                null, callback);
    }

    @Override
    public ServiceTicket updateInfo(DeliverymanRequest deliverymanRequest, Callback<Result<Boolean>> callback) {
        return serviceClient.call(true, OAUTH_DELIVERY_UPDATEINFO,
                deliverymanRequest,
                null, callback);
    }

    @Override
    public ServiceTicket updateDeliverymanWork(Boolean isWork, Callback<Result<Boolean>> callback) {
        return serviceClient.call(true, OAUTH_DELIVERY_UPDATE_WORK,
                MapTool.create().put("isWork", isWork).value(),
                null, callback);
    }

    @Override
    public ServiceTicket updatePostion(String lng, String lat, Callback<Result<Boolean>> callback) {
        return serviceClient.call(true, OAUTH_DELIVERY_UPDATE_POSITION,
                MapTool.create().put("lng", lng).put("lat", lat).value(),
                null, callback);
    }

    @Override
    public ServiceTicket checkVersion(String appType, Integer clientVersion, Callback<ClientVersionInfo> callback) {
        return serviceClient.call(false, APP_CLIENT_CHECK_VERSION,
                MapTool.create().put("appType", appType).put("clientVersion", clientVersion).value(),
                null, callback);
    }
}
