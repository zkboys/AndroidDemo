package com.zkboys.sdk.model;

public class DishInfo {

    private String id;   //菜品ID
    private String name = ""; //菜品名称
    private String saleName = ""; //菜品销售名称
    private Double price;  //菜品单价
    private String unit = "";     //单位名称
    private Long chargeUnit; //计价单位ID
    private String chargeUnitName; //计价单位名称
    private String groupName; //菜品分组名称
    private String unitName; // TODO 这个是什么鬼？
    private int total = 0;      // 点菜数量

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getChargeUnit() {
        return chargeUnit;
    }

    public void setChargeUnit(Long chargeUnit) {
        this.chargeUnit = chargeUnit;
    }

    public String getChargeUnitName() {
        return chargeUnitName;
    }

    public void setChargeUnitName(String chargeUnitName) {
        this.chargeUnitName = chargeUnitName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "DishInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", saleName='" + saleName + '\'' +
                ", price=" + price +
                ", unit='" + unit + '\'' +
                ", chargeUnit=" + chargeUnit +
                ", chargeUnitName='" + chargeUnitName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", unitName='" + unitName + '\'' +
                ", total=" + total +
                '}';
    }
}
