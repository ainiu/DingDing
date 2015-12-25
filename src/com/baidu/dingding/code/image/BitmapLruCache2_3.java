package com.baidu.dingding.code.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.baidu.dingding.code.Constants;
import com.baidu.dingding.code.util.EncryptionUtil;
import com.baidu.dingding.code.util.ImageUtil;

/**
 * LruCache缓存管理类，该类实现了ImageCache接口，并实现了LruCache
 * 一旦bitmap对象从LruCache中被挤出，将会被放置在BitmapSoftRefCache中，再配合该框架本身支持的硬盘缓存，可以完成图片三级缓存
 *
 * BitmapLruCache.java
 * @author jason
 *
 */
@SuppressLint("NewApi")
public class BitmapLruCache2_3  implements ImageCache {
    private BitmapSoftRefCache softRefCache;
    private String CACHE_DIR;
    public BitmapLruCache2_3(Context context) {
        softRefCache = new BitmapSoftRefCache();
        CACHE_DIR= Constants.getSDCachePath(context);
    }

    /**
     * 得到缓存对象
     */
    @Override
    public Bitmap getBitmap(String imageUrl) {
        Bitmap bitmap =softRefCache.getBitmap(EncryptionUtil.MD5_32(imageUrl));
        if(bitmap==null){
            //重文件中查找
            bitmap= ImageUtil.getBitmapFromFile(CACHE_DIR + EncryptionUtil.MD5_32(imageUrl));
        }
        return bitmap;
    }

    /**
     * 添加缓存对象
     * 如果getBitmap方法返回不为空，则不会调用此方法
     */
    @Override
    public void putBitmap(String imageUrl, Bitmap bitmap) {
        ImageUtil.saveBitmapToLocalFile(CACHE_DIR+EncryptionUtil.MD5_32(imageUrl), bitmap);
        softRefCache.putBitmap(EncryptionUtil.MD5_32(imageUrl), bitmap);
    }
}
