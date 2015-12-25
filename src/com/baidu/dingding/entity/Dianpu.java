package com.baidu.dingding.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/2.
 *  "usrNumber": "15919848718",
 "logPath": "http://183.234.117.210:9090/hello/upFile/shop_logo/3776.jpg",
 "shopName": "小小店铺",
 "shopId": "242",
 "authentication": "已认证",
 "country": "中国香港"
 */
public class Dianpu implements Serializable{

    String usrNumber;  //卖家叮叮号
    String logPath;
    String shopName;
    String shopId;
    String authentication;
    String country;

    public Dianpu() {

    }

    public Dianpu(String authentication, String country, String shopId, String logPath, String shopName, String usrNumber) {
        super();
        this.authentication = authentication;
        this.country = country;
        this.shopId = shopId;
        this.logPath = logPath;
        this.shopName = shopName;
        this.usrNumber = usrNumber;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getUsrNumber() {
        return usrNumber;
    }

    public void setUsrNumber(String usrNumber) {
        this.usrNumber = usrNumber;
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

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
