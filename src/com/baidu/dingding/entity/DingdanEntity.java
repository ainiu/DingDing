package com.baidu.dingding.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2015/11/26.
 * "content": [
 * {
 * "goodsVOs": [
 * {
 * "picPath": "http://183.234.117.210:9090/hello/upFile/spPic/7260.png",
 * "goodsId": "7260",
 * "price": "600000.0",
 * "goodsSpecificationContactStr": "",
 * "name": "德国爱他美Milupa Aptamil婴幼儿奶粉 2段 800g",
 * "goodsCount": "1",
 * "totalPrice": "600000.0"
 * }
 * ],
 * "sellerNumber": "SZhaitao",
 * "orderNo": "M151116094049432365",
 * "remark": "",
 * "shopName": "铜锣湾",
 * "shopId": "3",
 * "freight": "0.0",
 * "expressType": "叮叮物流",
 * "orderStatus": "0",
 * "expressNum": "",
 * "orderId": "5992",
 * "totalPrice": "600000.0"
 * }
 * ],
 * "result": "0"
 * }
 */
public class DingdanEntity implements Serializable {
    public List<GoodsVOs> goodsVOs;
    public String orderNo;
    public String sellerNumber;
    public String remark;
    public String shopId;
    public String shopName;
    public String freight;
    public String expressType;
    public String expressNum;
    public String orderStatus;
    public String orderId;
    public String totalPrice;

    public DingdanEntity() {
    }

    public DingdanEntity(List<GoodsVOs> goodsVOs, String orderNo, String remark, String shopName, String shopId, String freight, String expressType, String orderStatus, String expressNum, String orderId, String totalPrice, String sellerNumber) {
        this.goodsVOs = goodsVOs;
        this.orderNo = orderNo;
        this.remark = remark;
        this.shopName = shopName;
        this.shopId = shopId;
        this.freight = freight;
        this.expressType = expressType;
        this.orderStatus = orderStatus;
        this.expressNum = expressNum;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.sellerNumber = sellerNumber;
    }

    public String getSellerNumber() {
        return sellerNumber;
    }

    public void setSellerNumber(String sellerNumber) {
        this.sellerNumber = sellerNumber;
    }

    public List<GoodsVOs> getGoodsVOs() {
        return goodsVOs;
    }

    public void setGoodsVOs(List<GoodsVOs> goodsVOs) {
        this.goodsVOs = goodsVOs;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getExpressType() {
        return expressType;
    }

    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Content{" +
                "goodsVOs=" + goodsVOs +
                ", orderNo='" + orderNo + '\'' +
                ", remark='" + remark + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopId='" + shopId + '\'' +
                ", freight='" + freight + '\'' +
                ", expressType='" + expressType + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", expressNum='" + expressNum + '\'' +
                ", orderId='" + orderId + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                '}';
    }


    public static class GoodsVOs {
        public String picPath;
        public String goodsId;
        public String goodsCount;
        public Object goodsSpecificationContactStr;
        public String price;
        public String totalPrice;
        public String name;

        public GoodsVOs() {
        }

        public GoodsVOs(String picPath, String goodsId, String price, Object goodsSpecificationContactStr, String name, String goodsCount, String totalPrice) {
            this.picPath = picPath;
            this.goodsId = goodsId;
            this.price = price;
            this.goodsSpecificationContactStr = goodsSpecificationContactStr;
            this.name = name;
            this.goodsCount = goodsCount;
            this.totalPrice = totalPrice;
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

        public String getPrice() {
            return price;
        }

        public Object getGoodsSpecificationContactStr() {
            return goodsSpecificationContactStr;
        }

        public void setGoodsSpecificationContactStr(Object goodsSpecificationContactStr) {
            this.goodsSpecificationContactStr = goodsSpecificationContactStr;
        }

        public void setPrice(String price) {
            this.price = price;
        }



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(String goodsCount) {
            this.goodsCount = goodsCount;
        }

        @Override
        public String toString() {
            return "GoodsVOs{" +
                    "picPath='" + picPath + '\'' +
                    ", goodsId='" + goodsId + '\'' +
                    ", price='" + price + '\'' +
                    ", goodsSpecificationContactStr='" + goodsSpecificationContactStr + '\'' +
                    ", name='" + name + '\'' +
                    ", goodsCount='" + goodsCount + '\'' +
                    ", totalPrice='" + totalPrice + '\'' +
                    '}';
        }
    }
}