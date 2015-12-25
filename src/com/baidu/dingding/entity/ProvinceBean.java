package com.baidu.dingding.entity;

/**
 * Created by Administrator on 2015/12/4.
 */
public class ProvinceBean {
    private String id;                              //id
    private String name;                            //省名
    private String description;                     //描述
    private String others;                          //其他

    public ProvinceBean(String id,String name,String description,String others){
        this.id = id;
        this.name = name;
        this.description = description;
        this.others = others;
    }

    public ProvinceBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
    public String getPickerViewText() {
        //这里还可以判断文字超长截断再提供显示
        return name;
    }
}
