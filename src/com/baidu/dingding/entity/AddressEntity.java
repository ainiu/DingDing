package com.baidu.dingding.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/12/3.收货地址列表
 */
public class AddressEntity implements Parcelable {
    String receiveEmail;
    String receiveId;
    String isDefault;
    String receiveTel;
    String receiveMobile;
    String province_city_area;
    String userId;
    String receiveName;
    String receiveAddress;
    String receivePost;
    String regionId;

    public AddressEntity() {
    }

    public AddressEntity(String receiveEmail, String receiveId, String isDefault, String receiveTel, String receiveMobile, String province_city_area, String userId, String receiveName, String receiveAddress, String receivePost, String regionId) {
        this.receiveEmail = receiveEmail;
        this.receiveId = receiveId;
        this.isDefault = isDefault;
        this.receiveTel = receiveTel;
        this.receiveMobile = receiveMobile;
        this.province_city_area = province_city_area;
        this.userId = userId;
        this.receiveName = receiveName;
        this.receiveAddress = receiveAddress;
        this.receivePost = receivePost;
        this.regionId = regionId;
    }

    protected AddressEntity(Parcel in) {
        receiveEmail = in.readString();
        receiveId = in.readString();
        isDefault = in.readString();
        receiveTel = in.readString();
        receiveMobile = in.readString();
        province_city_area = in.readString();
        userId = in.readString();
        receiveName = in.readString();
        receiveAddress = in.readString();
        receivePost = in.readString();
        regionId = in.readString();
    }

    public static final Creator<AddressEntity> CREATOR = new Creator<AddressEntity>() {
        @Override
        public AddressEntity createFromParcel(Parcel in) {
            return new AddressEntity(in);
        }

        @Override
        public AddressEntity[] newArray(int size) {
            return new AddressEntity[size];
        }
    };

    @Override
    public String toString() {
        return "AddressEntity{" +
                "receiveEmail='" + receiveEmail + '\'' +
                ", receiveId='" + receiveId + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", receiveTel='" + receiveTel + '\'' +
                ", receiveMobile='" + receiveMobile + '\'' +
                ", province_city_area='" + province_city_area + '\'' +
                ", userId='" + userId + '\'' +
                ", receiveName='" + receiveName + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", receivePost='" + receivePost + '\'' +
                ", regionId='" + regionId + '\'' +
                '}';
    }

    public String getReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getReceiveTel() {
        return receiveTel;
    }

    public void setReceiveTel(String receiveTel) {
        this.receiveTel = receiveTel;
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public String getProvince_city_area() {
        return province_city_area;
    }

    public void setProvince_city_area(String province_city_area) {
        this.province_city_area = province_city_area;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceivePost() {
        return receivePost;
    }

    public void setReceivePost(String receivePost) {
        this.receivePost = receivePost;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(receiveEmail);
        dest.writeString(receiveId);
        dest.writeString(isDefault);
        dest.writeString(receiveTel);
        dest.writeString(receiveMobile);
        dest.writeString(province_city_area);
        dest.writeString(userId);
        dest.writeString(receiveName);
        dest.writeString(receiveAddress);
        dest.writeString(receivePost);
        dest.writeString(regionId);
    }
}
