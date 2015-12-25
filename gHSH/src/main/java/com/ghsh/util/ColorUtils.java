package com.ghsh.util;

import android.graphics.Color;

public class ColorUtils {
	
	/**
	 * 颜色变浅
	 * @param color 颜色
	 * @param level 变浅（0-1）
	 * */
	public static int getLightColor(int color,float level){
		if(level==0){
			return color;
		}
		if(Color.red(color)==255&&Color.green(color)==255&&Color.blue(color)==255){
			return color;
		}
		if(Color.red(color)==0&&Color.green(color)==0&&Color.blue(color)==0){
			return color;
		}
		float[] hsv=new float[3];
		Color.colorToHSV(color, hsv);
		for (int i = 0; i < 3; i++) {
			hsv[i] = (float)Math.floor((255 - hsv[i]) * level + hsv[i]);
		}
		return Color.HSVToColor(hsv);
	}
	/**
	 * 颜色加深
	 * @param color 颜色
	 * @param level 加深（0-1）
	 * */
	public static int getDarkColor(int color,float level){
		if(level==0){
			return color;
		}
		if(Color.red(color)==255&&Color.green(color)==255&&Color.blue(color)==255){
			return color;
		}
		if(Color.red(color)==0&&Color.green(color)==0&&Color.blue(color)==0){
			return color;
		}
		float[] hsv=new float[3];
		Color.colorToHSV(color, hsv);
		for (int i = 0; i < 3; i++) {
			hsv[i] = (float)Math.floor(hsv[i] * (1 - level));
		}
		return Color.HSVToColor(hsv);
	}
}
