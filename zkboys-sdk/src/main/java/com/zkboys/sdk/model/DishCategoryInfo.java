package com.zkboys.sdk.model;

import java.util.List;

public class DishCategoryInfo {

    private String id;  //菜品分组ID
    private String name; //菜品分组名称
    private boolean isShow = false;
    private List<DishInfo> dishes;

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

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public List<DishInfo> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishInfo> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "DishCategoryInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isShow=" + isShow +
                ", dishes=" + dishes +
                '}';
    }
}
