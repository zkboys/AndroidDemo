package com.zkboys.sdk.service;

import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.ClientVersionInfo;
import com.zkboys.sdk.model.DeliverymanInfo;
import com.zkboys.sdk.model.DeliverymanLittleInfo;
import com.zkboys.sdk.model.DeliverymanRequest;
import com.zkboys.sdk.model.Result;

public interface DeliverymanService {


    /**
     * 获取快递员基本信息
     *
     * @param callback
     * @return
     */
    ServiceTicket findDeliveryman(Callback<DeliverymanLittleInfo> callback);

    /**
     * 获取快递员详细信息
     *
     * @param callback
     * @return
     */
    ServiceTicket findDeliverymanInfo(Callback<DeliverymanInfo> callback);

    /**
     * 完善注册信息
     *
     * @param deliverymanRequest
     * @param callback
     * @return
     */
    ServiceTicket updateInfo(DeliverymanRequest deliverymanRequest, Callback<Result<Boolean>> callback);

    /**
     * 修改快递员工作状态
     *
     * @param isWork
     * @param callback
     * @return
     */
    ServiceTicket updateDeliverymanWork(Boolean isWork, Callback<Result<Boolean>> callback);

    /**
     * location
     *
     * @param lng
     * @param lat
     * @param callback
     * @return
     */
    ServiceTicket updatePostion(String lng, String lat, Callback<Result<Boolean>> callback);

    ServiceTicket checkVersion(String appType, Integer clientVersion, Callback<ClientVersionInfo> callback);
}
