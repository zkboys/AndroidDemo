package com.zkboys.sdk.model;

import java.io.Serializable;

/**
 * Created by HuaHua on 2016/3/26 0026.
 */
public class OrderItemInfo implements Serializable {

    private Long id;
    private Long storeOrderId;  //开桌订单ID
    private Long dishNormId;  //商品标识
    private String dishName;  //菜品名称
    private String unit;   //单位

    private Integer price;  //商品单价
    private Integer amount; //商品总价
    private Integer charge; //实收金额
    private String isFree;  //是否赠送（Y/N）
    private Integer freeCount = 0;  //赠菜数量
    private Integer returnCount = 0; //退菜数量
    private String status = "";   //ordered已点餐  入厨cooking  完成cooked  退菜return  正在划菜 drawing
    private String remark = "";  //备注


    private Integer unitCount;              // 有值，表示双单位 （默认1份）
    private String chargeUnitName;          // 计位 单位
    private Double totalCount; //商品数量   //  计价单位 不能为NULL   （如 7.2斤）

    private String groupName; //菜品分组名称

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreOrderId() {
        return storeOrderId;
    }

    public void setStoreOrderId(Long storeOrderId) {
        this.storeOrderId = storeOrderId;
    }

    public Long getDishNormId() {
        return dishNormId;
    }

    public void setDishNormId(Long dishNormId) {
        this.dishNormId = dishNormId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getTotalCount() {
        if (totalCount == null) {
            return 0.00;
        } else {
            return totalCount;
        }

    }

    public void setTotalCount(Double totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public boolean getIsFree() {
        if (isFree.equals("Y"))
            return true;
        else return false;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public Integer getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(Integer freeCount) {
        this.freeCount = freeCount;
    }


    public Integer getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(Integer unitCount) {
        this.unitCount = unitCount;
    }

    public String getChargeUnitName() {
        return chargeUnitName;
    }

    public void setChargeUnitName(String chargeUnitName) {
        this.chargeUnitName = chargeUnitName;
    }

    @Override
    public String toString() {
        return "OrderItemInfo{" +
                "id=" + id +
                ", storeOrderId=" + storeOrderId +
                ", dishNormId=" + dishNormId +
                ", dishName='" + dishName + '\'' +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", charge=" + charge +
                ", isFree='" + isFree + '\'' +
                ", freeCount=" + freeCount +
                ", returnCount=" + returnCount +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", unitCount=" + unitCount +
                ", chargeUnitName='" + chargeUnitName + '\'' +
                ", totalCount=" + totalCount +
                ", groupName='" + groupName + '\'' +
                '}';
    }

    public Integer getReturnCount() {
        if (null != returnCount) {
            return returnCount;
        } else {
            return 0;
        }

    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String toJson() {
        return "{\"dishNormId\":"
                + dishNormId
                + ",\"totalCount\":"
                + totalCount + '}';
    }

    public boolean equal(OrderItemInfo info) {
        if (info.getDishNormId() == dishNormId) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isOrder() {
        if (status.equals("ordered") && null != status) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCooking() {
        if (status.equals("cooking") && null != status) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCookied() {
        if (status.equals("cooked") && null != status) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isReturn() {
        if (returnCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFree() {
        if (freeCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isDrawing() {
        if (status.equals("drawing") && null != status) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (null != obj) {
            if (obj instanceof DishInfo) {

                if (this.dishNormId == null || ((DishInfo) obj).getId() == null) {
                    return false;
                }

                if (this.dishNormId.equals(((DishInfo) obj).getId())) {
                    return true;
                } else {
                    return false;
                }

            } else if (obj instanceof OrderItemInfo) {
                if (this.getStatus().equals(((OrderItemInfo) obj).getStatus())) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } else {
            return false;
        }

    }
}
