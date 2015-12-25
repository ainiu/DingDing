package com.baidu.dingding.entity;

/**
 * Created by Administrator on 2015/11/10.海外店铺推荐
 */
public class HaiwaituijianBean extends NewGoods {
    String categoryName;
    String originPlace;

    public HaiwaituijianBean() {

    }

    public HaiwaituijianBean(String goodsId, String storeNumber, String logPath, String name, String freight, String primalPrice, String categoryName, String originPlace) {
        super(goodsId, storeNumber, logPath, name, freight, primalPrice);
        this.categoryName = categoryName;
        this.originPlace = originPlace;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }
}
