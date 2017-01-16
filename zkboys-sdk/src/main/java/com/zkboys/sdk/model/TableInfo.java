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
    private String id = "";
    private int seatNum = 0;
    private int seatedNum = 0;
    private String tabStatus = "";
    private Long openTime = 0L;
    private Long endTime = 0L;
    private boolean isChecked = false;

    public TableInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
            return this.seatNum == ((FreeTableInfo) obj).getSeatNum();
        } else if (obj instanceof TableInfo) {
            return this.id.equals(((TableInfo) obj).getId());
        }
        return false;
    }

    public boolean isFree() {
        return C.TableStatus.FREE.equals(tabStatus);
    }


    public boolean isReserved() {
        return C.TableStatus.RESERVED.equals(tabStatus);
    }

    public boolean isCleaning() {
        return C.TableStatus.NEED_CLEAN.equals(tabStatus);
    }


    public boolean isDining() {
        return C.TableStatus.DINING.equals(tabStatus);
    }


    public boolean isOpened() {
        return C.TableStatus.OPENED.equals(tabStatus);
    }

    public boolean isLocked() {
        return C.TableStatus.LOCKED.equals(tabStatus);
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

    protected TableInfo(Parcel in) {
        id = in.readString();
        name = in.readString();
        seatNum = in.readInt();
        seatedNum = in.readInt();
        tabStatus = in.readString();
        openTime = in.readLong();
        endTime = in.readLong();
        isChecked = in.readByte() != 0;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(seatNum);
        dest.writeInt(seatedNum);
        dest.writeString(tabStatus);
        dest.writeLong(openTime);
        dest.writeLong(endTime);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }
}
