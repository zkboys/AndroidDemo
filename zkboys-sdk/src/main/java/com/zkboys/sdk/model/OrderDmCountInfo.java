package com.zkboys.sdk.model;

import java.io.Serializable;

/**
 * Created by HuaHua on 2016/9/11 0011.
 */

public class OrderDmCountInfo implements Serializable {
    private int toMonthOrderCount;

    private int finishOrderCount;

    private OrderDmCountResult orders;

    private double score;//搜索时间段的平均值


    public int getToMonthOrderCount() {
        return toMonthOrderCount;
    }

    public void setToMonthOrderCount(int toMonthOrderCount) {
        this.toMonthOrderCount = toMonthOrderCount;
    }

    public int getFinishOrderCount() {
        return finishOrderCount;
    }

    public void setFinishOrderCount(int finishOrderCount) {
        this.finishOrderCount = finishOrderCount;
    }


    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public OrderDmCountResult getOrders() {
        return orders;
    }

    public void setOrders(OrderDmCountResult orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrderDmCountInfo{" +
                "toMonthOrderCount=" + toMonthOrderCount +
                ", finishOrderCount=" + finishOrderCount +
                ", orders=" + orders +
                ", score=" + score +
                '}';
    }
}
