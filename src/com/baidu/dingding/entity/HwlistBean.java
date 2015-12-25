package com.baidu.dingding.entity;

/**
 * Created by Administrator on 2015/11/5."goodsId":"4731","storeNumber":"0","logPath":"http://183.234.117.210:9090/hello/upFile/spPic/4731.png","primalPrice":"2578.0","goodsName":"CASIO EXILIM 日版EX-ZR1100YW 美颜自拍神器"
 */
public class HwlistBean {
    String goodsId;
    String storeNumber;
    String logPath;
    String primalPrice;                     //原价
    String goodsName;                        //名字

    public HwlistBean() {
    }

    public HwlistBean(String goodsId, String storeNumber, String logPath, String primalPrice, String goodsName) {
        this.goodsId = goodsId;
        this.storeNumber = storeNumber;
        this.logPath = logPath;
        this.primalPrice = primalPrice;
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getPrimalPrice() {
        return primalPrice;
    }

    public void setPrimalPrice(String primalPrice) {
        this.primalPrice = primalPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
