package com.baidu.dingding.entity.Shoping;

/**
 * Created by Administrator on 2015/12/16.
 */
public class AllSelect {
    //卖家ID
    private int GoodsShopID;

    //卖家名称
    private String fShopName;

    //整个卖家选中
    private boolean isShopCheck;

    public AllSelect() {
    }

    public AllSelect(boolean isShopCheck, String fShopName, int goodsShopID) {
        this.isShopCheck = isShopCheck;
        this.fShopName = fShopName;
        GoodsShopID = goodsShopID;
    }

    public int getGoodsShopID() {
        return GoodsShopID;
    }

    public void setGoodsShopID(int goodsShopID) {
        GoodsShopID = goodsShopID;
    }

    public String getfShopName() {
        return fShopName;
    }

    public void setfShopName(String fShopName) {
        this.fShopName = fShopName;
    }

    public boolean isShopCheck() {
        return isShopCheck;
    }

    public void setIsShopCheck(boolean isShopCheck) {
        this.isShopCheck = isShopCheck;
    }
}
