package com.zkboys.sdk.model;

import java.io.Serializable;

/**
 * Created by HuaHua on 2016/9/12 0012.
 */

public class OrderDmRankInfo implements Serializable {

    private Integer rank;

    private String name;

    private int orderCount;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    @Override
    public String toString() {
        return "OrderDmRankDTO{" +
                "rank=" + rank +
                ", name='" + name + '\'' +
                ", orderCount=" + orderCount +
                '}';
    }
}
