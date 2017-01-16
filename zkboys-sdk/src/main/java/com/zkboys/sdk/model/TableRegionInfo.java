package com.zkboys.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TableRegionInfo implements Parcelable {


    /**
     * id : 101
     * name : 一楼
     * "tableList": [
     * {
     * "name": "2号桌",
     * "id": 6,
     * "seatNum": 6, // 桌位数量
     * "seatedNum": 0, // 用户数量
     * "tabStatus": "free",
     * "beginTime": null, // 开始用餐时间
     * "endTime": null // 结束用餐时间
     * }
     * ]
     */

    private String id;
    private String name;
    private List<TableInfo> tableList;

    public TableRegionInfo() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<TableInfo> getTableList() {
        return tableList;
    }

    public void setTableList(List<TableInfo> tableList) {
        this.tableList = tableList;
    }

    @Override
    public String toString() {
        return "TablesInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tableList=" + tableList +
                '}';
    }

    protected TableRegionInfo(Parcel in) {
        id = in.readString();
        name = in.readString();
        tableList = in.createTypedArrayList(TableInfo.CREATOR);
    }

    public static final Creator<TableRegionInfo> CREATOR = new Creator<TableRegionInfo>() {
        @Override
        public TableRegionInfo createFromParcel(Parcel in) {
            return new TableRegionInfo(in);
        }

        @Override
        public TableRegionInfo[] newArray(int size) {
            return new TableRegionInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeTypedList(tableList);
    }
}
