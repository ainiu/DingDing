package com.baidu.dingding.code;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ReturnBean {

    private Object object;
    private JSONObject jsonObject;
    String string ;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    private int type;// 类型

    private int pageCount; //总页数

    private List<HashMap<String, Object>> mapList;

    public List<HashMap<String, Object>> getMapList() {
        return mapList;
    }

    public void setMapList(List<HashMap<String, Object>> mapList) {
        this.mapList = mapList;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

}
