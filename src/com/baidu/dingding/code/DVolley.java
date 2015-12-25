package com.baidu.dingding.code;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.dingding.code.image.BitmapLruCache;
import com.baidu.dingding.code.image.BitmapLruCache2_3;
import com.baidu.dingding.code.image.ImageListenerFactory;
import com.baidu.dingding.code.util.VolleyUtil;
import com.baidu.dingding.until.LogUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@SuppressLint("NewApi")
public class DVolley {
    private static final String DVolley_TAG = "DVolley";

    private static DVolley instance;
    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;
    private final static int RATE = 8; // 默认分配最大空间的几分之一

    private DVolley(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB){
            // 确定在LruCache中，分配缓存空间大小,默认程序分配最大空间的 1/8
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            int maxSize = manager.getMemoryClass() / RATE; // 比如 64M/8,单位为M
            // BitmapLruCache自定义缓存class，该框架本身支持二级缓存，在BitmapLruCache封装一个软引用缓存
            mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(context,1024 * 1024 * maxSize));
        }else{
            mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache2_3(context));
        }
    }

    /** 初始化Volley相关对象，在使用Volley前应该完成初始化 */
    public static void init(Context context) {
        if (instance == null) {
            instance = new DVolley(context);
        }
    }


    public static void getImage(String requestUrl, ImageView imageView, int defaultImageResId) {
        if(requestUrl==null||requestUrl.equals("")){
            return;
        }
        imageView.setTag(requestUrl);
        getImageLoader().get(requestUrl, ImageListenerFactory.getImageListener(imageView, defaultImageResId), 0, 0);
    }


    public static void post(String url, final Map<String, String> params,DResponseService response) {
        if(Constants.DEBUG){
            LogUtil.i(DVolley_TAG + "POST", VolleyUtil.getURL(url, params));
        }
        StringRequest jr = new StringRequest(Request.Method.POST, url,response, response) {
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            /**重写parseNetworkResponse(NetworkResponse response)解决乱码*/
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, "GBK");
                    return Response.success(jsonString,
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (Exception je) {
                    return Response.error(new ParseError(je));
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map=new HashMap<String,String>();
                map.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");
                map.put("Connection", "keep-alive");
                map.put("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
                map.put("Accept-Encoding", "gzip, deflate");
                map.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                return map;
            }
        };
        jr.setTag(DVolley_TAG);
        DVolley.getRequestQueue().add(jr);
    }

    public static void get(String url, Map<String, String> params,DResponseService response){
        if(Constants.DEBUG){
            Log.i(DVolley_TAG+"_GET", VolleyUtil.getURL(url, params));

        }
        StringRequest jr = new StringRequest(Request.Method.GET, VolleyUtil.getURL(url, params), response,response);
        jr.setTag(DVolley_TAG);
        DVolley.getRequestQueue().add(jr);
    }

    public static void cancelAll(){
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(DVolley_TAG);
        }
    }
    /**得到请求队列对象*/
    private static RequestQueue getRequestQueue() {
        throwIfNotInit();
        return mRequestQueue;
    }

    /** 得到ImageLoader对象*/
    private static ImageLoader getImageLoader() {
        throwIfNotInit();
        return mImageLoader;
    }
    //检查是否完成初始化
    private static void throwIfNotInit() {
        if (instance == null) {// 尚未初始化
            throw new IllegalStateException("DYVolley尚未初始化，在使用前应该执行init()");
        }
    }
}
