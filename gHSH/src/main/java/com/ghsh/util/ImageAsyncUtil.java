/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ghsh.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;

import com.ghsh.Constants;
import com.ghsh.code.util.EncryptionUtil;

/**
 * 图片类
 */
public class ImageAsyncUtil {
	
	public static String CACHE_DIR = null;// 资源路径
	// 内存中的软应用缓存
	private static Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
	private static ExecutorService executorService;
	static{
		executorService=Executors.newFixedThreadPool(1);
	}
	public static void initSkinDir(Context context) {
		CACHE_DIR = Constants.getCachePath(context);
	}
	/**下载图片*/
	public static void downloadImageUrl(final String imageUrl,final ImageListener imageListener){
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				String imageName=EncryptionUtil.MD5_32(imageUrl);
				String imagelocalPath=CACHE_DIR+imageName;;//图片本地路径
				Bitmap bitmap=null;
				try {
					bitmap=ImageUtil.getBitmapFromURL(imageUrl,imagelocalPath);
					imageListener.onCallback(bitmap, imagelocalPath,null);
				} catch (Exception e) {
					imageListener.onCallback(bitmap, imagelocalPath,e);
				}
			}
		});
	}
	
	public static Bitmap getBitmap(String imageUrl) throws Exception{
		String imageName=EncryptionUtil.MD5_32(imageUrl);
		//中内存中读取
		Bitmap bitmap =ImageAsyncUtil.getBitmapFromMemory(imageName);
		if(bitmap!=null){
			return bitmap;
		}
		String imagelocalPath=CACHE_DIR+imageName;;//图片本地路径
		// 从内存卡中读取
		bitmap=ImageUtil.getBitmapFromFile(imagelocalPath);
		if(bitmap!=null){
			imageCache.put(imageName, new SoftReference<Bitmap>(bitmap));
			return bitmap;
		}
		//从网上获取
		bitmap=ImageUtil.getBitmapFromURL(imageUrl,imagelocalPath);
		if(bitmap!=null){
			imageCache.put(imageName, new SoftReference<Bitmap>(bitmap));
			return bitmap;
		}
		return null;
	}
	
	/**从内存缓存中获取bitmap*/
	private static Bitmap getBitmapFromMemory(String imageName) {
		Bitmap bitmap = null;
		if (imageCache.containsKey(imageName)) {
			synchronized (imageCache) {
				SoftReference<Bitmap> bitmapRef = imageCache.get(imageName);
				if (bitmapRef != null) {
					bitmap = bitmapRef.get();
					return bitmap;
				}
			}
		}
		return bitmap;
	}
	

	
	public interface ImageListener{
		public void onCallback(Bitmap bitmap,String imageLocalPath,Throwable error);
	}
}
