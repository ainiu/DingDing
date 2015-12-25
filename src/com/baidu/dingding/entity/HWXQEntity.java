package com.baidu.dingding.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/12. 海外详情实体
 */
public class HWXQEntity implements Serializable {
    public HWXQEntity() {
    }

    public HWXQEntity(ProductSpecification productSpecification, GoodsInfo goodsInfo, List<GoodsFdjg> goodsFdjg, String sort) {
        this.productSpecification = productSpecification;
        this.goodsInfo = goodsInfo;
        this.goodsFdjg = goodsFdjg;
        this.sort = sort;
    }

    public GoodsInfo goodsInfo;
    public ProductSpecification productSpecification;
    public List<GoodsFdjg>  goodsFdjg=new ArrayList<GoodsFdjg>();
    public String sort;


    public class GoodsInfo{

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getSellerNumber() {
            return sellerNumber;
        }

        public void setSellerNumber(String sellerNumber) {
            this.sellerNumber = sellerNumber;
        }

        public String getXsjg() {
            return xsjg;
        }

        public void setXsjg(String xsjg) {
            this.xsjg = xsjg;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOriginPlace() {
            return originPlace;
        }

        public void setOriginPlace(String originPlace) {
            this.originPlace = originPlace;
        }

        public List getPictures() {
            return pictures;
        }

        public void setPictures(List pictures) {
            this.pictures = pictures;
        }

        public String getStartBuyMin() {
            return startBuyMin;
        }

        public void setStartBuyMin(String startBuyMin) {
            this.startBuyMin = startBuyMin;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDgMax() {
            return dgMax;
        }

        public void setDgMax(String dgMax) {
            this.dgMax = dgMax;
        }

        public String getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(String arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public String getTgjg() {
            return tgjg;
        }

        public void setTgjg(String tgjg) {
            this.tgjg = tgjg;
        }

        public String getStoreCount() {
            return storeCount;
        }

        public void setStoreCount(String storeCount) {
            this.storeCount = storeCount;
        }

        String goodsId;
        String sellerNumber;
        String xsjg;
        String status;
        String originPlace;
        List pictures;
        String startBuyMin;
        String type;
        String country;
        String dgMax;
        String arrivalTime;
        String description;
        String shopId;
        String shopName;
        String name;
        String freight;
        String tgjg;
        String storeCount;
    }

    public class ProductSpecification{
        public ProductSpecification(List<String> sting) {
            this.T = sting;
        }

        List<String> T =new ArrayList<String>();

        public List getT() {
            return T;
        }

        public void setT(List t) {
            T = t;
        }
    }

    public class GoodsFdjg{
       String beginNum;
        String lastNum;
         String fdjg;

        public String getBeginNum() {
            return beginNum;
        }

        public void setBeginNum(String beginNum) {
            this.beginNum = beginNum;
        }

        public String getLastNum() {
            return lastNum;
        }

        public void setLastNum(String lastNum) {
            this.lastNum = lastNum;
        }

        public String getFdjg() {
            return fdjg;
        }

        public void setFdjg(String fdjg) {
            this.fdjg = fdjg;
        }
    }

    @Override
    public String toString() {
        return "HWXQEntity{" +
                "goodsInfo=" + goodsInfo +
                ", productSpecification=" + productSpecification +
                ", goodsFdjg=" + goodsFdjg +
                ", sort='" + sort + '\'' +
                '}';
    }



    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public ProductSpecification getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(ProductSpecification productSpecification) {
        this.productSpecification = productSpecification;
    }

    public List<GoodsFdjg> getGoodsFdjg() {
        return goodsFdjg;
    }

    public void setGoodsFdjg(List<GoodsFdjg> goodsFdjg) {
        this.goodsFdjg = goodsFdjg;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}
