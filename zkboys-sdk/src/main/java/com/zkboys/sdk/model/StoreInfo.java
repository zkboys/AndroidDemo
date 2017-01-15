package com.zkboys.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangshubin on 17/1/15.
 */

public class StoreInfo implements Parcelable {
    private String id;
    private String merchantId;
    private String name;
    private String address;

    public StoreInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "StoreInfo{" +
                "id='" + id + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    protected StoreInfo(Parcel in) {
        id = in.readString();
        merchantId = in.readString();
        name = in.readString();
        address = in.readString();
    }

    public static final Creator<StoreInfo> CREATOR = new Creator<StoreInfo>() {
        @Override
        public StoreInfo createFromParcel(Parcel in) {
            return new StoreInfo(in);
        }

        @Override
        public StoreInfo[] newArray(int size) {
            return new StoreInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(merchantId);
        dest.writeString(name);
        dest.writeString(address);
    }
}
