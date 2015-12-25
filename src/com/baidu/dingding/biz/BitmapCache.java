package com.baidu.dingding.biz;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2015/10/28.
 * 图片处理
 */

public class BitmapCache extends LruCache<String,Bitmap> implements ImageLoader.ImageCache {

    // LruCache 原理：Cache保存一个强引用来限制内容数量，每当Item被访问的时候，此Item就会移动到队列的头部。 当cache已满的时候加入新的item时，在队列尾部的item会被回收。
    // 解释：当超出指定内存值则移除最近最少用的图片内存
    public static int getDefaultLruCacheSize() {
        // 拿到最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 拿到内存的八分之一来做图片内存缓存
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }

    public BitmapCache() {
        this(getDefaultLruCacheSize());
    }

    public BitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
