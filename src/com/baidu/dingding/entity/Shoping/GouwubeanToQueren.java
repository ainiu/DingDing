package com.baidu.dingding.entity.Shoping;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/12/22.
 */
public class GouwubeanToQueren implements Parcelable{

    private String picPath;
    private String goodsId;
    private String sort;
    private String sellerNumber;
    private String price;
    private String goodsSpecificationContactStr ;
    private String shoppingCarId;
    private String shopName;
    private String shopId;
    private String freight;
    private String goodsCount;
    private String goodsName;
    private boolean pid;
    private boolean id;
    private double count;

    public GouwubeanToQueren() {
    }

    public GouwubeanToQueren(String picPath, String goodsId, String sort, String sellerNumber, String price, String goodsSpecificationContactStr, String shoppingCarId, String shopName, String shopId, String freight, String goodsCount, String goodsName, boolean pid, boolean id, double count) {
        this.picPath = picPath;
        this.goodsId = goodsId;
        this.sort = sort;
        this.sellerNumber = sellerNumber;
        this.price = price;
        this.goodsSpecificationContactStr = goodsSpecificationContactStr;
        this.shoppingCarId = shoppingCarId;
        this.shopName = shopName;
        this.shopId = shopId;
        this.freight = freight;
        this.goodsCount = goodsCount;
        this.goodsName = goodsName;
        this.pid = pid;
        this.id = id;
        this.count = count;
    }

    @Override
    public String toString() {
        return "GouwubeanToQueren{" +
                "picPath='" + picPath + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", sort='" + sort + '\'' +
                ", sellerNumber='" + sellerNumber + '\'' +
                ", price='" + price + '\'' +
                ", goodsSpecificationContactStr='" + goodsSpecificationContactStr + '\'' +
                ", shoppingCarId='" + shoppingCarId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopId='" + shopId + '\'' +
                ", freight='" + freight + '\'' +
                ", goodsCount='" + goodsCount + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", pid=" + pid +
                ", id=" + id +
                ", count=" + count +
                '}';
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSellerNumber() {
        return sellerNumber;
    }

    public void setSellerNumber(String sellerNumber) {
        this.sellerNumber = sellerNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoodsSpecificationContactStr() {
        return goodsSpecificationContactStr;
    }

    public void setGoodsSpecificationContactStr(String goodsSpecificationContactStr) {
        this.goodsSpecificationContactStr = goodsSpecificationContactStr;
    }

    public String getShoppingCarId() {
        return shoppingCarId;
    }

    public void setShoppingCarId(String shoppingCarId) {
        this.shoppingCarId = shoppingCarId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(String goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public boolean isPid() {
        return pid;
    }

    public void setPid(boolean pid) {
        this.pid = pid;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public static Creator<GouwubeanToQueren> getCREATOR() {
        return CREATOR;
    }

    protected GouwubeanToQueren(Parcel in) {
        picPath = in.readString();
        goodsId = in.readString();
        sort = in.readString();
        sellerNumber = in.readString();
        price = in.readString();
        goodsSpecificationContactStr = in.readString();
        shoppingCarId = in.readString();
        shopName = in.readString();
        shopId = in.readString();
        freight = in.readString();
        goodsCount = in.readString();
        goodsName = in.readString();
        pid = in.readByte() != 0;
        id = in.readByte() != 0;
        count = in.readDouble();
    }

    public static final Creator<GouwubeanToQueren> CREATOR = new Creator<GouwubeanToQueren>() {
        @Override
        public GouwubeanToQueren createFromParcel(Parcel in) {
            return new GouwubeanToQueren(in);
        }

        @Override
        public GouwubeanToQueren[] newArray(int size) {
            return new GouwubeanToQueren[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(picPath);
        dest.writeString(goodsId);
        dest.writeString(sort);
        dest.writeString(sellerNumber);
        dest.writeString(price);
        dest.writeString(goodsSpecificationContactStr);
        dest.writeString(shoppingCarId);
        dest.writeString(shopName);
        dest.writeString(shopId);
        dest.writeString(freight);
        dest.writeString(goodsCount);
        dest.writeString(goodsName);
        dest.writeByte((byte) (pid ? 1 : 0));
        dest.writeByte((byte) (id ? 1 : 0));
        dest.writeDouble(count);
    }
}
