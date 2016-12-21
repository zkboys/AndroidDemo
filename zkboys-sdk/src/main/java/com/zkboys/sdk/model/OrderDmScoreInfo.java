package com.zkboys.sdk.model;

import java.io.Serializable;

/**
 * Created by HuaHua on 2016/9/11 0011.
 */

public class OrderDmScoreInfo implements Serializable {
    private String orderCode;

    private String finishTime;

    private double score;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "OrderDmScoreDTO{" +
                "orderCode='" + orderCode + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", score=" + score +
                '}';
    }
}
