package com.ghsh.activity.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghsh.R;

public class DSearchLablesView extends LinearLayout{
	private Context context;
	private int screenWidth;
	private float density;
	private LinearLayout.LayoutParams lableParams;
	private Animation expandAnim;
	private Listener listener;
	public DSearchLablesView(Context context) {
		super(context);
		this.context=context;
		DisplayMetrics metric = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metric);
		screenWidth = metric.widthPixels;
		density = metric.density;
//		screenWidth -= 10 * density;
		this.initViews();
	}

	private void initViews(){
		this.setOrientation(LinearLayout.VERTICAL);
		expandAnim = AnimationUtils.loadAnimation(context, R.anim.lable_expand);
		
		lableParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		lableParams.setMargins(0, 20, 20, 0);
	}
	
	public void addTexts(List<String> textList,int padding){
		if(textList==null||textList.size()==0){
			return;
		}
		screenWidth=screenWidth-padding;
		LinearLayout layout = this.createLayout();
		this.addView(layout);
		
		float lineWidth = 0;
		
		for (String text:textList) {
			final TextView textView=new TextView(context);
			textView.setPadding(20, 24, 20, 24);
			textView.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.button_search_while_radius_border_bg));
			textView.setTextColor(this.getResources().getColor(R.color.font_color_333333)); 
			textView.setText(text);
			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(listener!=null){
						listener.onClickLable(textView.getText().toString());
					}
				}
			});
			float width=textView.getPaint().measureText(text)+textView.getPaddingLeft()+textView.getPaddingRight()+lableParams.rightMargin+lableParams.leftMargin;
			lineWidth = lineWidth+width;//+ (20 * density);
			if(lineWidth > screenWidth){
				layout =  this.createLayout();
				this.addView(layout);
				layout.addView(textView,lableParams);
				lineWidth=width;
			}else{
				layout.addView(textView,lableParams);
			}
		}
	}
	private LinearLayout createLayout(){
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		layout.setGravity(Gravity.LEFT);
		layout.setAnimationCacheEnabled(true);
		layout.setLayoutAnimation(new LayoutAnimationController(expandAnim));
		return layout;
	}
	
	public void addListener(Listener listener){
		this.listener=listener;
	}
	public static interface Listener{
		public void onClickLable(String text);
	}
}
