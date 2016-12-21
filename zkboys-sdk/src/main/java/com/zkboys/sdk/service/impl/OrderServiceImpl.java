package com.zkboys.sdk.service.impl;

import com.zkboys.sdk.common.MapTool;
import com.zkboys.sdk.httpjson.BaseService;
import com.zkboys.sdk.model.OrderDetailInfo;
import com.zkboys.sdk.model.OrderDmCountInfo;
import com.zkboys.sdk.model.OrderDmRankInfo;
import com.zkboys.sdk.model.OrderLittleInfo;
import com.zkboys.sdk.model.Result;
import com.zkboys.sdk.model.Results;
import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.service.OrderService;
import com.zkboys.sdk.httpjson.ServiceClient;
import com.zkboys.sdk.httpjson.ServiceTicket;


public class OrderServiceImpl extends BaseService implements OrderService {

    public static final String OAUTH_ORDER_FIND_ORDERS = "/order/list.json";
    public static final String OAUTH_ORDER_FIND_ORDERS_COUNT = "/order/count/list.json";
    public static final String OAUTH_ORDER_FIND_ORDERS_INFO = "/order/get.json";
    public static final String OAUTH_ORDER_FIND_RANK = "/order/rank/list.json";
    public static final String OAUTH_ORDER_START = "/order/carry/start.json";
    public static final String OAUTH_ORDER_FINISH = "/order/carry/finish.json";


    public OrderServiceImpl(ServiceClient serviceClient) {
        super(serviceClient);
    }

    @Override
    public ServiceTicket findOrders(String state, Callback<Results<OrderLittleInfo>> callback) {
        return serviceClient.call(true, OAUTH_ORDER_FIND_ORDERS,
                MapTool.create().put("state", state).value(),
                null, callback);
    }

    @Override
    public ServiceTicket findOrderCount(String start, String end, int offset, int size, Callback<OrderDmCountInfo> callback) {
        return serviceClient.call(true, OAUTH_ORDER_FIND_ORDERS_COUNT,
                MapTool.create().put("start", start).put("end", end).put("offset", offset).put("size", size).value(),
                null, callback);
    }

    @Override
    public ServiceTicket findOrderInfo(Long orderId, Callback<OrderDetailInfo> callback) {
        return serviceClient.call(true, OAUTH_ORDER_FIND_ORDERS_INFO,
                MapTool.create().put("orderId", orderId).value(),
                null, callback);
    }

    @Override
    public ServiceTicket findRank(String mode, Callback<Results<OrderDmRankInfo>> callback) {
        return serviceClient.call(true, OAUTH_ORDER_FIND_RANK,
                MapTool.create().put("mode", mode).value(),
                null, callback);
    }

    @Override
    public ServiceTicket startCarry(Long orderId, String lng, String lat, Callback<Result<Boolean>> callback) {
        return serviceClient.call(true, OAUTH_ORDER_START,
                MapTool.create().put("orderId", orderId).put("lng", lng).put("lat", lat).value(),
                null, callback);
    }

    @Override
    public ServiceTicket finishCarry(Long id, String lng, String lat, Callback<Result<Boolean>> callback) {
        return serviceClient.call(true, OAUTH_ORDER_FINISH,
                MapTool.create().put("id", id).put("lng", lng).put("lat", lat).value(),
                null, callback);
    }
}
