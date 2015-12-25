package com.baidu.dingding.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/2. 商品收藏
 */
public class CollectionEntity implements Serializable {

    String goodsCollectionId;
    String goodsId;
    String goodsName;
    String picPath;
    String price;
    String freight;
    String shopName;
    String shopId;
    String sellerNumber;

    @Override
    public String toString() {
        return "CollectionEntity{" +
                "goodsCollectionId='" + goodsCollectionId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", picPath='" + picPath + '\'' +
                ", price='" + price + '\'' +
                ", freight='" + freight + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopId='" + shopId + '\'' +
                ", sellerNumber='" + sellerNumber + '\'' +
                '}';
    }

    public CollectionEntity() {
    }

    public CollectionEntity(String goodsCollectionId, String goodsId, String goodsName, String picPath, String price, String freight, String shopName, String shopId, String sellerNumber) {
        this.goodsCollectionId = goodsCollectionId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.picPath = picPath;
        this.price = price;
        this.freight = freight;
        this.shopName = shopName;
        this.shopId = shopId;
        this.sellerNumber = sellerNumber;
    }

    public String getGoodsCollectionId() {
        return goodsCollectionId;
    }

    public void setGoodsCollectionId(String goodsCollectionId) {
        this.goodsCollectionId = goodsCollectionId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
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

    public String getSellerNumber() {
        return sellerNumber;
    }

    public void setSellerNumber(String sellerNumber) {
        this.sellerNumber = sellerNumber;
    }
}
