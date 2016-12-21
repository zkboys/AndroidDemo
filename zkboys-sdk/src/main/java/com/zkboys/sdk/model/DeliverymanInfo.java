package com.zkboys.sdk.model;

import java.io.Serializable;

public class DeliverymanInfo implements Serializable {

    private Long id;
    private String name;//姓名
    private String mobile; //电话
    private String carType;//汽车类型
    private String expressState; //配送状态
    private String status; //审核状态
    private String approveMessage;//审核意见（审核拒绝的之后才会有）

    private String idCard;
    private String idCardPositiveUrl;  //身份证正面URL
    private String idCardBackUrl;       //身份证背面URL
    private String licensePlate;
    private String licenseDrive;
    private String insuranceNo;
    private String carCode;

    //身份证正面
    private Long idCardPositive;
    //身份证背面
    private Long idCardBack;

    @Override
    public String toString() {
        return "DeliverymanInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", carType='" + carType + '\'' +
                ", expressState='" + expressState + '\'' +
                ", status='" + status + '\'' +
                ", approveMessage='" + approveMessage + '\'' +
                ", idCard='" + idCard + '\'' +
                ", idCardPositiveUrl='" + idCardPositiveUrl + '\'' +
                ", idCardBackUrl='" + idCardBackUrl + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", licenseDrive='" + licenseDrive + '\'' +
                ", insuranceNo='" + insuranceNo + '\'' +
                ", carCode='" + carCode + '\'' +
                ", idCardPositive=" + idCardPositive +
                ", idCardBack=" + idCardBack +
                '}';
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getApproveMessage() {
        return approveMessage;
    }

    public void setApproveMessage(String approveMessage) {
        this.approveMessage = approveMessage;
    }

    public String getIdCardPositiveUrl() {
        return idCardPositiveUrl;
    }

    public void setIdCardPositiveUrl(String idCardPositiveUrl) {
        this.idCardPositiveUrl = idCardPositiveUrl;
    }

    public String getIdCardBackUrl() {
        return idCardBackUrl;
    }

    public void setIdCardBackUrl(String idCardBackUrl) {
        this.idCardBackUrl = idCardBackUrl;
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

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpressState() {
        return expressState;
    }

    public void setExpressState(String expressState) {
        this.expressState = expressState;
    }
}
