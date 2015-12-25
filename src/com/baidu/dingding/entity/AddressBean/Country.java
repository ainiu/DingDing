package com.baidu.dingding.entity.AddressBean;

/**
 * Created by Administrator on 2015/12/8.
 */
public class Country {
    public String countryNmae;       //县名
    public String country_post;      //编号

    public String getCountry_post() {
        return country_post;
    }

    public void setCountry_post(String country_post) {
        this.country_post = country_post;
    }

    public String getCountryNmae() {
        return countryNmae;
    }

    public void setCountryNmae(String countryNmae) {
        this.countryNmae = countryNmae;
    }
}
