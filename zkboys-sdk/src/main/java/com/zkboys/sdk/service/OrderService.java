package com.zkboys.sdk.service;

import com.zkboys.sdk.httpjson.Callback;
import com.zkboys.sdk.httpjson.ServiceTicket;
import com.zkboys.sdk.model.OrderDetailInfo;
import com.zkboys.sdk.model.OrderDmCountInfo;
import com.zkboys.sdk.model.OrderDmRankInfo;
import com.zkboys.sdk.model.OrderLittleInfo;
import com.zkboys.sdk.model.Result;
import com.zkboys.sdk.model.Results;

public interface OrderService {

    /**
     * 获取快递的订单列表
     *
     * @param state    订单状态  (state: allotted:待配送, carrying:正在配送)
     * @param callback
     * @return
     */
    ServiceTicket findOrders(String state, Callback<Results<OrderLittleInfo>> callback);

    /**
     * 获取快递配送完成的订单
     *
     * @param start    : 开始时间 (yyyy-MM-dd)
     * @param end      : 结束时间 (yyyy-MM-dd)
     * @param offset:  当前list数量
     * @param size:    一次读取数量
     * @param callback
     * @return
     */
    ServiceTicket findOrderCount(String start, String end, int offset, int size, Callback<OrderDmCountInfo> callback);

    /**
     * 获取订单详情
     *
     * @param orderId  订单ID
     * @param callback
     * @return
     */
    ServiceTicket findOrderInfo(Long orderId, Callback<OrderDetailInfo> callback);

    /**
     * 获取订单排行榜
     *
     * @param mode     day(当天排行) month(当月排行)
     * @param callback
     * @return
     */
    ServiceTicket findRank(String mode, Callback<Results<OrderDmRankInfo>> callback);

    /**
     * 接单 开始配送
     *
     * @param orderId
     * @param lng
     * @param lat
     * @param callback
     * @return
     */
    ServiceTicket startCarry(Long orderId, String lng, String lat, Callback<Result<Boolean>> callback);

    /**
     * 接单 完成配送
     *
     * @param id
     * @param lng
     * @param lat
     * @param callback
     * @return
     */
    ServiceTicket finishCarry(Long id, String lng, String lat, Callback<Result<Boolean>> callback);


}
