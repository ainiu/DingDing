package com.baidu.dingding.entity;

import java.io.Serializable;

public class TPic implements Serializable {
    private static final long serialVersionUID = 1L;

    private String picID;// 编号

    private String thumbnail;// 小图

    private String imageUrl;// 大图

    private String sortOrder;// 排序

    private String isFixed;//是否固定连接

    private String href;//单击跳转连接


    private int imageRes;

    public TPic(){
    }
    public TPic(int imageRes){
        this.imageRes=imageRes;
    }

    public String getPicID() {
        return picID;
    }

    public void setPicID(String picID) {
        this.picID = picID;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(String isFixed) {
        this.isFixed = isFixed;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
