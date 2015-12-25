package com.fanc.mycar.code.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class VolleyUtil {

    public static String getURL(String url, Map<String, String> map) {
        StringBuffer params = null;
        if (map != null && map.size() != 0) {
            params = new StringBuffer();
            for (String key : map.keySet()) {
                params.append(key).append("=").append(map.get(key)).append("&");
            }
            params.deleteCharAt(params.length()-1);
        }
        if (url.lastIndexOf('?') >= 0) {
            url = url + "&" + params;
        } else {
            url = url + "?" + params;
        }
        return url;
    }

    public static String encode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return text;
        }
    }

}
