package com.baidu.dingding.fragment.ShopingCar;

/**
 * Created by Administrator on 2015/12/15.
 */
public class ChildNode {
    String  id;
    String pid;
    String price;
    String logpath;
    String goodsName;
    Object goodsSpecificationContactStr;
    String goodsCount;

    public ChildNode() {
    }

    public ChildNode(String id, String pid, String price, String logpath, String goodsName, Object goodsSpecificationContactStr, String goodsCount) {
        this.id = id;
        this.pid = pid;
        this.price = price;
        this.logpath = logpath;
        this.goodsName = goodsName;
        this.goodsSpecificationContactStr = goodsSpecificationContactStr;
        this.goodsCount = goodsCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLogpath() {
        return logpath;
    }

    public void setLogpath(String logpath) {
        this.logpath = logpath;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Object getGoodsSpecificationContactStr() {
        return goodsSpecificationContactStr;
    }

    public void setGoodsSpecificationContactStr(Object goodsSpecificationContactStr) {
        this.goodsSpecificationContactStr = goodsSpecificationContactStr;
    }

    public String getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(String goodsCount) {
        this.goodsCount = goodsCount;
    }
}
