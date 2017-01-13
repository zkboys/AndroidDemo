package com.zkboys.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zkboys.sdk.common.C;

/**
 * Created by HuaHua on 2016/3/23 0023.
 */
public class TableInfo implements Parcelable {


    /**
     * name : 2号桌
     * id : 6
     * seatNum : 6
     * seatedNum : 0
     * tabStatus : free
     * beginTime : null
     * endTime : null
     */

    private String name = "";
    private Long id = 0L;
    private int seatNum = 0;
    private int seatedNum = 0;
    private String tabStatus = "";
    private Long openTime;
    private Long endTime;
    private boolean isChecked = false;

    protected TableInfo(Parcel in) {
        name = in.readString();
        seatNum = in.readInt();
        seatedNum = in.readInt();
        tabStatus = in.readString();
        isChecked = in.readByte() != 0;
    }

    public TableInfo() {
    }

    public static final Creator<TableInfo> CREATOR = new Creator<TableInfo>() {
        @Override
        public TableInfo createFromParcel(Parcel in) {
            return new TableInfo(in);
        }

        @Override
        public TableInfo[] newArray(int size) {
            return new TableInfo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public int getSeatedNum() {
        return seatedNum;
    }

    public void setSeatedNum(int seatedNum) {
        this.seatedNum = seatedNum;
    }

    public String getTabStatus() {
        return tabStatus;
    }

    public void setTabStatus(String tabStatus) {
        this.tabStatus = tabStatus;
    }


    public Long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Long openTime) {
        this.openTime = openTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FreeTableInfo) {
            if (this.seatNum == ((FreeTableInfo) obj).getSeatNum()) {
                return true;
            } else {
                return false;
            }
        } else if (obj instanceof TableInfo) {
            if (this.id == ((TableInfo) obj).getId()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean isFree() {
        if ((C.TableStatus.STATU_FREE.equals(tabStatus)) && null != tabStatus) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isReserved() {
        if (C.TableStatus.STATU_RESERVED.equals(tabStatus) && null != tabStatus) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCleaning() {
        if (C.TableStatus.STATU_CLEANING.equals(tabStatus) && null != tabStatus) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isDining() {
        if (C.TableStatus.STATU_DINING.equals(tabStatus) && null != tabStatus) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isOpened() {
        if (C.TableStatus.STATU_OPENED.equals(tabStatus) && null != tabStatus) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", seatNum=" + seatNum +
                ", seatedNum=" + seatedNum +
                ", tabStatus='" + tabStatus + '\'' +
                ", openTime=" + openTime +
                ", endTime=" + endTime +
                ", isChecked=" + isChecked +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(seatNum);
        dest.writeInt(seatedNum);
        dest.writeString(tabStatus);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }
}
