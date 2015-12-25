package com.baidu.dingding.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/12/14.
 */
public class GouwucheBean implements Parcelable{

    /**
     * picPath : http://183.234.117.210:9090/hello/upFile/spPic/7208.jpg
     * goodsId : 7208
     * sort : 类别_颜色_内存_
     * sellerNumber : honggg
     * price : 80.0
     * goodsSpecificationContactStr : {"类别":"移动","颜色":"黑","内存":"1G"}
     * shoppingCarId : 125
     * shopName : hong12323
     * shopId : 5
     * freight : 0.0
     * goodsCount : 3
     * goodsName : 12312
     */
    private String picPath;
    private String goodsId;
    private String sort;
    private String sellerNumber;
    private String price;
    private Object goodsSpecificationContactStr ;
    private String shoppingCarId;
    private String shopName;
    private String shopId;
    private String freight;
    private String goodsCount;
    private String goodsName;
    private boolean pid;
    private boolean id;
    private double count;

    public GouwucheBean() {
    }

    public GouwucheBean(String picPath, String goodsId, String sort, String sellerNumber, String price, Object goodsSpecificationContactStr, String shoppingCarId, String shopName, String shopId, String freight, String goodsCount, String goodsName, boolean pid, boolean id, double count) {
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

    public static final Creator<GouwucheBean> CREATOR = new Creator<GouwucheBean>() {
        @Override
        public GouwucheBean createFromParcel(Parcel in) {
            return new GouwucheBean(in);
        }

        @Override
        public GouwucheBean[] newArray(int size) {
            return new GouwucheBean[size];
        }
    };

    @Override
    public String toString() {
        return "GouwucheBean{" +
                "picPath='" + picPath + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", sort='" + sort + '\'' +
                ", sellerNumber='" + sellerNumber + '\'' +
                ", price='" + price + '\'' +
                ", goodsSpecificationContactStr=" + goodsSpecificationContactStr +
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

    protected GouwucheBean(Parcel in) {
        picPath = in.readString();
        goodsId = in.readString();
        sort = in.readString();
        sellerNumber = in.readString();
        price = in.readString();
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




    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public boolean getPid() {
        return pid;
    }

    public void setPid(boolean pid) {
        this.pid = pid;
    }

    public boolean getId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public Object getGoodsSpecificationContactStr() {
        return goodsSpecificationContactStr;
    }

    public void setGoodsSpecificationContactStr(Object goodsSpecificationContactStr) {
        this.goodsSpecificationContactStr = goodsSpecificationContactStr;
    }

    public Object getObject() {
        return goodsSpecificationContactStr;
    }

    public void setObject(Object object) {
        this.goodsSpecificationContactStr = object;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setSellerNumber(String sellerNumber) {
        this.sellerNumber = sellerNumber;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public void setShoppingCarId(String shoppingCarId) {
        this.shoppingCarId = shoppingCarId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public void setGoodsCount(String goodsCount) {
        this.goodsCount = goodsCount;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPicPath() {
        return picPath;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getSort() {
        return sort;
    }

    public String getSellerNumber() {
        return sellerNumber;
    }

    public String getPrice() {
        return price;
    }



    public String getShoppingCarId() {
        return shoppingCarId;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public String getFreight() {
        return freight;
    }

    public String getGoodsCount() {
        return goodsCount;
    }

    public String getGoodsName() {
        return goodsName;
    }


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
