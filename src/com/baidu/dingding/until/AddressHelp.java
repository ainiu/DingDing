package com.baidu.dingding.until;

import com.baidu.dingding.CityJsonParse;
import com.baidu.dingding.TApplication;
import com.baidu.dingding.entity.AddressBean.City;
import com.baidu.dingding.entity.AddressBean.Country;
import com.baidu.dingding.entity.AddressBean.Provinces;
import com.baidu.dingding.entity.CityJsonEntity;

/**
 * Created by Administrator on 2015/12/23. 地址解析帮助类
 */
public class AddressHelp {

    public static  String  getAddress(String quId){
        CityJsonEntity cityjsonEntity = CityJsonParse.initConfige(TApplication.getContext());
        for(Provinces provinces:cityjsonEntity.getProvinces()){
            if(provinces.getShen_Poat().equals(quId)){
                return provinces.getProvinceName();
            }else{
                for(City citys :provinces.getCities()){
                    if(citys.getCity_post().equals(quId)){
                        return  provinces.getProvinceName()+citys.getCityName();
                    }else{
                        for(Country countrys:citys.getCountries()){
                            if(countrys.country_post.equals(quId)){
                                return provinces.getProvinceName()+"_"+citys.getCityName()+countrys.getCountryNmae();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
