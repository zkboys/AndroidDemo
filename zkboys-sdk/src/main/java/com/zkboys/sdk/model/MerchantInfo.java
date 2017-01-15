package com.zkboys.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by wangshubin on 17/1/15.
 */

public class MerchantInfo implements Parcelable {
    private String id;
    private String name;
    private String logo;
    private List<StoreInfo> stores;

    public MerchantInfo() {
    }

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<StoreInfo> getStores() {
        return stores;
    }

    public void setStores(List<StoreInfo> stores) {
        this.stores = stores;
    }

    @Override
    public String toString() {
        return "MerchantInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", stores=" + stores +
                '}';
    }

    protected MerchantInfo(Parcel in) {
        id = in.readString();
        name = in.readString();
        logo = in.readString();
        stores = in.createTypedArrayList(StoreInfo.CREATOR);
    }

    public static final Creator<MerchantInfo> CREATOR = new Creator<MerchantInfo>() {
        @Override
        public MerchantInfo createFromParcel(Parcel in) {
            return new MerchantInfo(in);
        }

        @Override
        public MerchantInfo[] newArray(int size) {
            return new MerchantInfo[size];
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
        dest.writeString(logo);
        dest.writeTypedList(stores);
    }
}
