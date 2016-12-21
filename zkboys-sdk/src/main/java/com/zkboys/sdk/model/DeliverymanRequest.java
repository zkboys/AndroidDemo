package com.zkboys.sdk.model;

import java.io.Serializable;

/**
 * Created by HuaHua on 2016/9/11 0011.
 */

public class DeliverymanRequest implements Serializable {
    private String name;
    //汽车类型
    private String carType;
    //身份证号
    private String idCard;
    //身份证正面
    private Long idCardPositive;
    //身份证背面
    private Long idCardBack;
    //车牌号
    private String licensePlate;
    //驾照号
    private String licenseDrive;
    //保险编号
    private String insuranceNo;

    private String carCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Long getIdCardPositive() {
        return idCardPositive;
    }

    public void setIdCardPositive(Long idCardPositive) {
        this.idCardPositive = idCardPositive;
    }

    public Long getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(Long idCardBack) {
        this.idCardBack = idCardBack;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicenseDrive() {
        return licenseDrive;
    }

    public void setLicenseDrive(String licenseDrive) {
        this.licenseDrive = licenseDrive;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public String getCarCode() {
        return carCode;
    }

    @Override
    public String toString() {
        return "DeliverymanRequest{" +
                "name='" + name + '\'' +
                ", carType='" + carType + '\'' +
                ", idCard='" + idCard + '\'' +
                ", idCardPositive=" + idCardPositive +
                ", idCardBack=" + idCardBack +
                ", licensePlate='" + licensePlate + '\'' +
                ", licenseDrive='" + licenseDrive + '\'' +
                ", insuranceNo='" + insuranceNo + '\'' +
                ", carCode='" + carCode + '\'' +
                '}';
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }
}
