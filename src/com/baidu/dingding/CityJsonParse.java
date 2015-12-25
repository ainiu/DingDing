package com.baidu.dingding;

import android.content.Context;
import android.content.res.AssetManager;

import com.baidu.dingding.entity.AddressBean.City;
import com.baidu.dingding.entity.AddressBean.Country;
import com.baidu.dingding.entity.AddressBean.Provinces;
import com.baidu.dingding.entity.CityJsonEntity;
import com.baidu.dingding.until.LogUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/12/4. 解析城市数据
 */
public class CityJsonParse {
    /**
     * 初始化
     */
    public static CityJsonEntity initConfige(Context context) {

        CityJsonEntity bean = new CityJsonEntity();
        //AddressDao addressDao=new AddressDao(context);
        bean.provinces = new ArrayList<Provinces>();
        AssetManager assetManager = context.getResources().getAssets();
        try {
            InputStream is = assetManager.open("sanjiliandong.json");
            int len = 0;
            byte[] bytes = new byte[1024];
            StringBuffer sb = new StringBuffer();
            while ((len = is.read(bytes, 0, bytes.length)) != -1) {
                sb.append(new String(bytes, 0, len));
            }
            is.close();
            if (sb.length() == 0) {
                return null;
            }
            // json解析者
            JsonParser jsonParser = new JsonParser();
            // 解析成Json对象
            JsonObject asJsonObject = jsonParser.parse(sb.toString()).getAsJsonObject();
            // 遍历里头的数据  Entry是map的内部类,遍历的时候使用
            Set<Map.Entry<String, JsonElement>> entrySet = asJsonObject.entrySet();

            for (Map.Entry<String, JsonElement> entry : entrySet) {
                Provinces province = new Provinces();
                province.cities = new ArrayList<City>();
                province.setShen_Poat(entry.getKey());
                //0:[ "北京", 0, {}]
                JsonArray asJsonArray = entry.getValue().getAsJsonArray();
                String asString = asJsonArray.get(0).getAsString();  // 省name
                province.setProvinceName(asString);
                int asInt = asJsonArray.get(1).getAsInt();           //id
                province.setShengNumber(asInt);
               // addressDao.add(entry.getKey(),asString,-1+"",1+"");  //把数据添加到数据库
                JsonObject asJsonObject2 = asJsonArray.get(2).getAsJsonObject();
                LogUtil.i("省份->>", asString);
                // 遍历第2级数据 40000 : [ "北京市", 1, {40004 : [ "延庆县" ], 42255 : [ "经济技术开发区" ] } ] } ]
                Set<Map.Entry<String, JsonElement>> entrySet2 = asJsonObject2.entrySet();
                for (Map.Entry<String, JsonElement> entry2 : entrySet2) {
                    JsonArray asJsonArray2 = entry2.getValue().getAsJsonArray();
                    // 市bean类
                    City city = new City();
                    city.countries = new ArrayList<Country>();
                    city.setCity_post(entry2.getKey());
                    String asString2;
                    int asInt2;
                    if (asJsonArray2.size() == 1) {                       // 判断有没有下一级
                        asString2 = asJsonArray2.get(0).getAsString();
                        city.setCityName(asString2);
                        city.setChengshiNumber(-1);// 表示没有
                        //city.countries = null;// 表示没有，使用的时候要记得判断
                        LogUtil.i("没有下级市 ->>", asString2 +"key="+entry2.getKey() );
                    } else {
                        asString2 = asJsonArray2.get(0).getAsString();// 市级
                        city.setCityName(asString2) ;
                        asInt2 = asJsonArray2.get(1).getAsInt();   // 数字
                        city.setChengshiNumber(asInt2);
                        // 获取第三级数据（县级/区）
                        JsonObject asJsonObject3 = asJsonArray2.get(2).getAsJsonObject();
                        LogUtil.i("有下级市 ->>", asString2 +"key="+entry2.getKey());
                        // 遍历第三级数据
                        Set<Map.Entry<String, JsonElement>> entrySet3 = asJsonObject3.entrySet();
                        for (Map.Entry<String, JsonElement> entry3 : entrySet3) {
                            JsonArray asJsonArray3 = entry3.getValue().getAsJsonArray();
                            String asString3 = asJsonArray3.get(0).getAsString();// 县级数据
                            Country country = new Country();

                            country.setCountry_post(entry3.getKey());
                            country.countryNmae = asString3;
                            city.countries.add(country);// 添加县/区
                            LogUtil.i("县/区->>", asString3 + "key=" + entry3.getKey());
                        }
                    }
                    province.cities.add(city);
                }
                bean.provinces.add(province);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.i("解析城市的数据 ->>", bean.toString());
        return bean;
    }
}

