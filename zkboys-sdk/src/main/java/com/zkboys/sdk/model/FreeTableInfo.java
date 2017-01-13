package com.zkboys.sdk.model;

/**
 * Created by HuaHua on 2016/3/24 0024.
 */
public class FreeTableInfo {


    /**
     * seatNum : 6 座位人数
     * num : 0 空闲数量
     */

    private int seatNum;
    private int num;

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "FreeTableInfo{" +
                "seatNum=" + seatNum +
                ", num=" + num +
                '}';
    }


}
