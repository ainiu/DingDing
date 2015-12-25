package com.ghsh.view.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;

import com.ghsh.Constants;
import com.ghsh.util.ColorUtils;
/**
 * 创建圆角StateListDrawable
 * */
public class SelectorRadiusUtil {
	public static StateListDrawable createRadiusSelector(Context context,int normalColor,Rect padding) {
		return SelectorRadiusUtil.createRadiusSelector(context, normalColor, Color.TRANSPARENT,0,padding);
	}
	public static StateListDrawable createRadiusSelector(Context context,int normalColor,int borderColor,Rect padding) {
		return SelectorRadiusUtil.createRadiusSelector(context, normalColor,borderColor,1,padding);
	}
	private static StateListDrawable createRadiusSelector(Context context,int normalColor,int borderColor,int borderWidth,Rect padding) {
		Drawable normalDrawable  = SelectorRadiusUtil.getRadiusShapeDrawable(context,normalColor,borderColor,borderWidth,padding);
		Drawable pressedDrawable = SelectorRadiusUtil.getRadiusShapeDrawable(context,ColorUtils.getLightColor(normalColor, 0.1f),ColorUtils.getLightColor(borderColor, 0.1f),borderWidth,padding);
		return SelectorUtil.createSelectorDrawable(context, normalDrawable, pressedDrawable);
	}
	private static ShapeDrawable getRadiusShapeDrawable(Context context,int bgColor,int borderColor,int borderWidth,Rect padding){
		DRoundRectDrawable roundRectDrawable=new DRoundRectDrawable(12);
		roundRectDrawable.setBackgroundColor(bgColor);
		roundRectDrawable.setBorderColor(borderColor, borderWidth);
		if(padding!=null){
			roundRectDrawable.setPadding(padding);
			if(Constants.DEBUG){
				Log.i("SelectorRadiusUtil padding","left:"+padding.left+" top:"+padding.top+" right:"+padding.right+" bottom:"+padding.bottom);
			}
		}
		return roundRectDrawable;
	}
}
