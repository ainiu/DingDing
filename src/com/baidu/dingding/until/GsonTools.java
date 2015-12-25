package com.baidu.dingding.until;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/11/12.  Gson 解析工具类
 */
public class GsonTools {

    public GsonTools() {

    }

    @Override
    public String toString() {
        return super.toString();
    }


    //使用Gson进行解析Person对象
    public static <T> T getPerson(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
            LogUtil.i("Gson解析错误", "");
            e.printStackTrace();
            ExceptionUtil.handleException(e);
        }
        return t;
    }

    // 使用Gson进行解析 List<Person>数组
    public static <T> List<T> getPersons(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();

        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (Exception e) {
            LogUtil.i("Gson解析错误", "");
            ExceptionUtil.handleException(e);
        }
        return list;
    }
    /**json 转map*/
    public static Map<String, Object> getMapForJson(String jsonStr){
        JSONObject jsonObject ;
        try {
            jsonObject = new JSONObject(jsonStr);

            Iterator<String> keyIter= jsonObject.keys();
            String key;
            Object value ;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    /**未知键转list<Map<String,object>>*/

    public static List<Map<String, Object>> getlistforJsonObject(String jsonStr){
        JSONObject jsonObject ;
        List<Map<String, Object>> list = null;
        try {
            jsonObject = new JSONObject(jsonStr);
            list = new ArrayList<Map<String,Object>>();
            Iterator<String> keyIter= jsonObject.keys();
            String key;
            Object value ;
            Map<String, Object> map = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                map.put(key, value);
            }
            list.add(map);
            return list;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Json 转成 List<Map<String,object>>
     * @param jsonStr
     * @return
     */
    public static List<Map<String, Object>> getlistForJson(String jsonStr){
        List<Map<String, Object>> list = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            JSONObject jsonObj ;
            list = new ArrayList<Map<String,Object>>();
            for(int i = 0 ; i < jsonArray.length() ; i ++){
                jsonObj = (JSONObject)jsonArray.get(i);
                list.add(getMapForJson(jsonObj.toString()));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return list;
    }
    /**遍历Map*/
    public static Map.Entry[] getSortedHashtable(Hashtable h) {
        Set set = h.entrySet();

        Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);

        Arrays.sort(entries, new Comparator() {
            public int compare(Object arg0, Object arg1) {
                Object key1 = ((Map.Entry) arg0).getKey();
                Object key2 = ((Map.Entry) arg1).getKey();
                return ((Comparable) key1).compareTo(key2);
            }

        });
        return entries;
    }

    public static ArrayList<String>  getMapList(Map<String,String> map) {

        ArrayList<String> stringList = new ArrayList<String>();
        try {
            for (String key : map.keySet()) {
                LogUtil.i("解析mapok", "key=" + key);
                String value = map.get(key);
                LogUtil.i("解析mapok", "value=" + value);
                String string = key + ":" + value;
                stringList.add(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return stringList;

    }
    public static String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }


}
