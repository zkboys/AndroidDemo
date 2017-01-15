package com.zkboys.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by HuaHua on 2016/3/24 0024.
 */
public class TablesInfo implements Parcelable {


    /**
     * tabRegionId : 101
     * tabRegionName : 一楼
     * "tableList": [
     * {
     * "name": "2号桌",
     * "id": 6,
     * "seatNum": 6,
     * "seatedNum": 0,
     * "tabStatus": "free",
     * "beginTime": null,
     * "endTime": null
     * }
     * ]
     */

    private String tabRegionId;
    private String tabRegionName;
    private List<TableInfo> tableList;

    public TablesInfo() {

    }

    public void setTabRegionId(String tabRegionId) {
        this.tabRegionId = tabRegionId;
    }

    public void setTabRegionName(String tabRegionName) {
        this.tabRegionName = tabRegionName;
    }

    public String getTabRegionId() {
        return tabRegionId;
    }

    public String getTabRegionName() {
        return tabRegionName;
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
                "tabRegionId=" + tabRegionId +
                ", tabRegionName='" + tabRegionName + '\'' +
                ", tableList=" + tableList +
                '}';
    }

    protected TablesInfo(Parcel in) {
        tabRegionId = in.readString();
        tabRegionName = in.readString();
        tableList = in.createTypedArrayList(TableInfo.CREATOR);
    }

    public static final Creator<TablesInfo> CREATOR = new Creator<TablesInfo>() {
        @Override
        public TablesInfo createFromParcel(Parcel in) {
            return new TablesInfo(in);
        }

        @Override
        public TablesInfo[] newArray(int size) {
            return new TablesInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tabRegionId);
        dest.writeString(tabRegionName);
        dest.writeTypedList(tableList);
    }
}
