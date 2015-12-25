package com.ghsh.view.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ghsh.code.bean.TGoodsCategory;

public class ViewUtils {
	/** 
     * * 判断是否有长按动作发生
     * @param lastX 按下时X坐标
     * @param lastY 按下时Y坐标
     * @param thisX  移动时X坐标
     * @param thisY  移动时Y坐标
     * @param lastDownTime  按下时间 
     * @param thisEventTime 移动时间 
     * @param longPressTime 判断长按时间的阀值 
     */  
	public static boolean isLongPressed(float lastX, float lastY, float thisX,float thisY, long lastDownTime, long thisEventTime,long longPressTime) {
		float offsetX = Math.abs(thisX - lastX);
		float offsetY = Math.abs(thisY - lastY);
		long intervalTime = thisEventTime - lastDownTime;
		if (offsetX <= 10 && offsetY <= 10 && intervalTime >= longPressTime) {
			return true;
		}
		return false;
	}

	/**
	 * 设置字体不受系统字体大小影响
	 * */
	public static void updateConfiguration(Activity activity){
		Resources resource = activity.getResources();
		Configuration c =resource.getConfiguration() ;
		c.fontScale=1.0f;
		resource.updateConfiguration(c, resource.getDisplayMetrics());
	}
	public static SpannableStringBuilder getStringBuilder(String[] strs,int [] colos) {
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<strs.length;i++){
			sb.append(strs[i]);
		}
		SpannableStringBuilder style = new SpannableStringBuilder(sb.toString());
		int start=0;
		int end=0;
		for(int i=0;i<strs.length;i++){
			start=end;
			end=end+strs[i].length();
			style.setSpan(new ForegroundColorSpan(colos[i]),start,end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		}
		return style;
	}
	
	public static void setButtonDrawables(Context context,TextView textView,int icon,int type){
		Drawable drawable=context.getResources().getDrawable(icon);
		ViewUtils.setButtonDrawables(context, textView, drawable, type);
	}
	public static void setButtonDrawables(Context context,TextView textView,Drawable drawable,int type){
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		switch(type){
		case 1:
			textView.setCompoundDrawables(drawable, null, null, null); //设置左图标
			break;
		case 2:
			textView.setCompoundDrawables(null, drawable,null , null); //设置上图标
			break;
		case 3:
			textView.setCompoundDrawables(null, null, drawable, null); //设置右图标
			break;
		case 4:
			textView.setCompoundDrawables(null, null,null , drawable); //设置下图标
			break;
		}
	}
	
	
	public static void addNumberListener(ImageButton addButton,ImageButton minusButton,final TextView inputView,final NumberListener listener){
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int number=Integer.parseInt(inputView.getText()+"")+1;
				if(number<=99){
					inputView.setText(number+"");
					if(listener!=null){
						listener.modifyNumber(number);
					}
				}
			}
		});
		minusButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int number=Integer.parseInt(inputView.getText()+"")-1;
				if(number>=1){
					inputView.setText(number+"");
					if(listener!=null){
						listener.modifyNumber(number);
					}
				}
			}
		});
	}
	
	public static interface NumberListener{
		public void modifyNumber(int number);
	} 
}
