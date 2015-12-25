package com.baidu.dingding.entity.AddressBean;

import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 */
public class Provinces {

    public String provinceName;
    public int shengNumber;
    public List<City> cities;            //市数组
    public String shen_Poat;

    public String getShen_Poat() {
        return shen_Poat;
    }

    public void setShen_Poat(String shen_Poat) {
        this.shen_Poat = shen_Poat;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getShengNumber() {
        return shengNumber;
    }

    public void setShengNumber(int shengNumber) {
        this.shengNumber = shengNumber;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
