package com.zkboys.sdk.model;

import java.io.Serializable;

/**
 * Created by HuaHua on 2016/9/12 0012.
 */

public class DeliverymanLittleInfo implements Serializable {
    private Long id;
    private String name;//姓名
    private String mobile; //电话
    private String carType;
    private Double score;//评分
    private String expressState; //配送状态
    private Boolean isWorked;//是否工作中
    private String status; //审核状态
    private String approveMessage;//审核意见（审核拒绝的之后才会有）


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        if (name==null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        if (mobile == null) {
            return "";
        }
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

    public Double getScore() {
        if (score == null) {
            return 0.0;
        }
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getExpressState() {
        return expressState;
    }

    public void setExpressState(String expressState) {
        this.expressState = expressState;
    }

    public Boolean getIsWorked() {
        return this.isWorked;
    }

    public void setIsWorked(Boolean isWorked) {
        this.isWorked = isWorked;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApproveMessage() {
        return approveMessage;
    }

    public void setApproveMessage(String approveMessage) {
        this.approveMessage = approveMessage;
    }

    @Override
    public String toString() {
        return "DeliverymanLittleInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", carType='" + carType + '\'' +
                ", score=" + score +
                ", expressState='" + expressState + '\'' +
                ", isWorked=" + isWorked +
                ", status='" + status + '\'' +
                ", approveMessage='" + approveMessage + '\'' +
                '}';
    }
}
