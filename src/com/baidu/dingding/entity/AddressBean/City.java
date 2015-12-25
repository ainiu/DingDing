package com.baidu.dingding.entity.AddressBean;

import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 */
public class City {
    public String cityName;
    public int chengshiNumber;       //0不是直辖市1是直辖市
    public List<Country> countries;  //县数组
    public String city_post;            //市编号

    public String getCity_post() {
        return city_post;
    }

    public void setCity_post(String city_post) {
        this.city_post = city_post;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getChengshiNumber() {
        return chengshiNumber;
    }

    public void setChengshiNumber(int chengshiNumber) {
        this.chengshiNumber = chengshiNumber;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
