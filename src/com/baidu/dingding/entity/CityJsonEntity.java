package com.baidu.dingding.entity;


import com.baidu.dingding.entity.AddressBean.Provinces;

import java.util.List;

/**
 * Created by Administrator on 2015/12/4.  地址数据
 */
public class CityJsonEntity {
    //省数组
    public List<Provinces> provinces;

    public List<Provinces> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Provinces> provinces) {
        this.provinces = provinces;
    }

    public CityJsonEntity() {
    }

    @Override
    public String toString() {
        return "CityJsonEntity{" +
                "Provinces=" + provinces +
                '}';
    }



}
