package com.ghsh.code.volley.image;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.ghsh.Constants;
import com.ghsh.code.volley.util.VolleyUtil;

/**
 * 
 * 关于使用该框架造成图片错位问题，App使用了ImageLoader下载图片，当ListView滚动很快时，还是会发生错位！记得听别人提起过，
 * 使用该框架可以避免图片错位，但是结果还是会发生错位，查看源码并没有找到相关避免错位的措施，不知道是不是自己的使用方法不对，如您知晓，麻烦告知博主一下~
 * 
 * 这里通过覆写ImageListener方法，通过ImageView为其指定Tag标签，防止图片错位，
 * 即比对下载完图片的ImageUrl和当前该ImageView的Tag是否相等~
 * */
public class ImageListenerFactory {

	public static ImageListener getImageListener(ImageView view,int defaultImageResId) {
		return getImageListener(view,defaultImageResId, defaultImageResId,false);
	}
	
	public static ImageListener getImageListener(ImageView view,int defaultImageResId,boolean isRoad) {
		return getImageListener(view,defaultImageResId, defaultImageResId,isRoad);
	}
	/**
	 * @param view 显示组件
	 * @param defaultImageResId 默认图片
	 * @param errorImageResId 错误图片
	 * @param isRoad 是否将图片圆形
	 * @param isBg 是否将图片设置背景
	 * */
	public static ImageListener getImageListener(final ImageView view,final int defaultImageResId, final int errorImageResId,final boolean isRoad) {
		return new ImageListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (errorImageResId != 0) {
					view.setImageResource(errorImageResId);
				}
			}
			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				if (response.getBitmap() != null) {
					if (view.getTag().toString() == response.getRequestUrl()) {
						if(isRoad){
							view.setImageBitmap(VolleyUtil.toRoundBitmap(response.getBitmap()));
						}else{
							view.setImageBitmap(response.getBitmap());
						}
					} else {
						if(Constants.DEBUG){
							Log.i("ImageListenerFactory", "图片错位");
						}
					}
				} else if (defaultImageResId != 0) {
					view.setImageResource(defaultImageResId);
				}
			}
		};
	}
}