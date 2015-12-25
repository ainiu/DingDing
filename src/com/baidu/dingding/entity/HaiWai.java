package com.baidu.dingding.entity;

public class HaiWai {
    String ycdId;
    String name;

    public HaiWai() {

    }

    public HaiWai(String ycdId, String name) {
        this.ycdId = ycdId;
        this.name = name;
    }

    public String getYcdId() {
        return ycdId;
    }

    public void setYcdId(String ycdId) {
        this.ycdId = ycdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
