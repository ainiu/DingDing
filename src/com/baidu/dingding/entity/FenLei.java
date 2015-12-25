package com.baidu.dingding.entity;

import java.io.Serializable;

public class FenLei implements Serializable {
    /**
     * "parentId": "0",    类别
     * "sort": "13",  排序
     * "ctgCode": "0-593-",
     * "logPath": "http://183.234.117.210:9090/hello/upFile/ctgPic/593.jpg",
     * "name": "休闲户外",
     * "categoryId": "593",
     * "parentName": "
     */
    String parentId;
    String sort;
    String ctgCode;
    String logPath;
    String name;
    String categoryId;
    String parentName;

    public FenLei() {

    }

    public FenLei(String parentId, String sort, String ctgCode, String logPath, String name, String categoryId, String parentName) {
        this.parentId = parentId;
        this.sort = sort;
        this.ctgCode = ctgCode;
        this.logPath = logPath;
        this.name = name;
        this.categoryId = categoryId;
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return "FenLei{" +
                "parentId='" + parentId + '\'' +
                ", sort='" + sort + '\'' +
                ", ctgCode='" + ctgCode + '\'' +
                ", logPath='" + logPath + '\'' +
                ", name='" + name + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", parentName='" + parentName + '\'' +
                '}';
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCtgCode() {
        return ctgCode;
    }

    public void setCtgCode(String ctgCode) {
        this.ctgCode = ctgCode;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
