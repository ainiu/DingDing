package com.ghsh.view.util;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/**
 * 动画
 * */
public class AnimationUtils {

	public static Animation addAlphaAnimation(long duration,AnimationSet animationSet){
		Animation animation = new AlphaAnimation(0,1);   //AlphaAnimation 控制渐变透明的动画效果  
		animation.setDuration(500);     //动画时间毫秒数  
		if(animationSet!=null){
			animationSet.addAnimation(animation);    //加入动画集合  
		}
		return animation;
	}
	/**创建 AlphaAnimation 控制渐变透明的动画效果  */
	private static Animation createAlphaAnimation(long duration){
		Animation animation = new AlphaAnimation(0,1);   //AlphaAnimation 控制渐变透明的动画效果  
		// 设置动画持续时间
		animation.setDuration(duration);
		// 设置执行动画前，延迟时间
		animation.setStartOffset(0);
		//animation.setRepeatCount(int repeatCount);//设置重复次数
		//animation.setFillAfter(boolean);//动画执行完后是否停留在执行完的状态 
		return animation;
	}
	/**创建 TranslateAnimation 控制画面平移的动画效果    */
	private static Animation createTranslateAnimation(long duration){
		Animation animation = new TranslateAnimation(0, 150,0, 0);
		animation.setDuration(duration);//设置动画持续时间
//		animation.setRepeatCount(2);//设置重复次数
//		animation.setRepeatMode(Animation.REVERSE);//设置反方向执行 
		return animation;
	}
}
