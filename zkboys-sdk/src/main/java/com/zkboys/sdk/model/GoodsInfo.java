package com.zkboys.sdk.model;

import java.io.Serializable;

/**
 * Created by HuaHua on 2016/9/21 0021.
 */

public class GoodsInfo implements Serializable {
    private String goodsName;
    private Double goodsPrice;
    private Integer number;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }


    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsName='" + goodsName + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", number=" + number +
                '}';
    }

}
