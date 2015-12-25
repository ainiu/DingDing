package com.baidu.dingding.code.image;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * 软引用缓存管理类
 * BitmapSoftRefCache.java
 * @author jason
 *
 */
public class BitmapSoftRefCache implements ImageCache {
    private static final String TAG = "BitmapSoftRefCache";

    private LinkedHashMap<String, SoftReference<Bitmap>> softReferenceMap;

    public BitmapSoftRefCache() {
        softReferenceMap = new LinkedHashMap<String, SoftReference<Bitmap>>();
    }

    /**
     * 从软引用集合中得到Bitmap对象
     */
    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        SoftReference<Bitmap> softRef = softReferenceMap.get(url);
        if (softRef != null) {
            bitmap = softRef.get();
            if (bitmap == null) {
                softReferenceMap.remove(url); // 从map中移除
                Log.i(TAG, url + "对象已经被GC回收");
            } else {
                Log.i(TAG, "命中" + url);
            }
        }
        return bitmap;
    }

    /**
     * 从软引用集合中添加bitmap对象
     */
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        SoftReference<Bitmap> softRef = new SoftReference<Bitmap>(bitmap);
        softReferenceMap.put(url, softRef);
    }
}