package com.zkboys.sdk.model;

import java.io.Serializable;

/**
 * Created by HuaHua on 2016/9/9 0009.
 */

public class OrderLittleInfo implements Serializable {

    private Long id;
    //发货人地址
    private String supplierAddress;
    //发货人经度
    private Double supplierLng;
    //发货人纬度
    private Double supplierLat;
    //收货人姓名
    private String receiveName;
    //收货人电话
    private String receiveMobile;
    //收货人地址
    private String receiveAddress;
    //收货人经度
    private Double receiveLng;
    //收货人纬度
    private Double receiveLat;
    //期望取货时间
    private String expectedFetchTime;
    //期望送达时间
    private String expectedFinishTime;
    //订单状态
    private String state;
    //取货验证码
    private String extractCode;

    public String getExtractCode() {
        return extractCode;
    }

    public void setExtractCode(String extractCode) {
        this.extractCode = extractCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public Double getSupplierLng() {
        return supplierLng;
    }

    public void setSupplierLng(Double supplierLng) {
        this.supplierLng = supplierLng;
    }

    public Double getSupplierLat() {
        return supplierLat;
    }

    public void setSupplierLat(Double supplierLat) {
        this.supplierLat = supplierLat;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public Double getReceiveLng() {
        return receiveLng;
    }

    public void setReceiveLng(Double receiveLng) {
        this.receiveLng = receiveLng;
    }

    public Double getReceiveLat() {
        return receiveLat;
    }

    public void setReceiveLat(Double receiveLat) {
        this.receiveLat = receiveLat;
    }

    public String getExpectedFetchTime() {
        return expectedFetchTime;
    }

    public void setExpectedFetchTime(String expectedFetchTime) {
        this.expectedFetchTime = expectedFetchTime;
    }

    public String getExpectedFinishTime() {
        return expectedFinishTime;
    }

    public void setExpectedFinishTime(String expectedFinishTime) {
        this.expectedFinishTime = expectedFinishTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "OrderLittleInfo{" +
                "id=" + id +
                ", supplierAddress='" + supplierAddress + '\'' +
                ", supplierLng=" + supplierLng +
                ", supplierLat=" + supplierLat +
                ", receiveName='" + receiveName + '\'' +
                ", receiveMobile='" + receiveMobile + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", receiveLng=" + receiveLng +
                ", receiveLat=" + receiveLat +
                ", expectedFetchTime='" + expectedFetchTime + '\'' +
                ", expectedFinishTime='" + expectedFinishTime + '\'' +
                ", state='" + state + '\'' +
                ", extractCode='" + extractCode + '\'' +
                '}';
    }
}
