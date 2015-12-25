package com.ghsh.view;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ghsh.R;
import com.ghsh.view.util.SelectorRadiusUtil;

public class DToastView {

	public static Toast makeText(Activity activity,int text,int duration){
		View view=activity.getLayoutInflater().inflate(R.layout.view_toast, null);
		int color=activity.getResources().getColor(R.color.app_global_color);
		int alphaColor=Color.argb(200, Color.red(color), Color.green(color), Color.blue(color));
		view.setBackgroundDrawable(SelectorRadiusUtil.createRadiusSelector(activity,alphaColor,null));
		TextView textView=(TextView)view.findViewById(R.id.toast_text);
		textView.setText(text);
		Toast toast = new Toast(activity);
        toast.setDuration(duration);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        return Toast.makeText(activity, text, duration);
	}
	public static Toast makeText(Activity activity,String text,int duration){
		View view=activity.getLayoutInflater().inflate(R.layout.view_toast, null);
		int color=activity.getResources().getColor(R.color.app_global_color);
		int alphaColor=Color.argb(200, Color.red(color), Color.green(color), Color.blue(color));
		view.setBackgroundDrawable(SelectorRadiusUtil.createRadiusSelector(activity,alphaColor,null));
		TextView textView=(TextView)view.findViewById(R.id.toast_text);
		textView.setText(text);
		Toast toast = new Toast(activity);
        toast.setDuration(duration);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        return Toast.makeText(activity, text, duration);
	}
}
