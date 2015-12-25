package com.ghsh.activity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghsh.R;

public class TabChildView extends LinearLayout {
	private Context context;
	private TextView textView;
	private ImageView imageView;
	private int textColor,selectTextColor,bgColor;
	private int buttonBorderColor,selectButtonBorderColor;//底部边框颜色，底部边框选中颜色
	private int leftBorderColor,rightBorderColor;//左边边框颜色,右边边框颜色
	public TabChildView(Context context) {
		super(context);
		this.context=context;
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.setGravity(Gravity.CENTER);
		this.initView();
	}
	private void initView(){
		textColor=this.getResources().getColor(R.color.tab_child_text_color);
		
		selectTextColor=context.getResources().getColor(R.color.app_global_color);//this.getResources().getColor(R.color.tab_child_text_selected_color);
		bgColor=this.getResources().getColor(R.color.tab_child_bg_color);
		buttonBorderColor=this.getResources().getColor(R.color.tab_child_boder_color);
		selectButtonBorderColor=context.getResources().getColor(R.color.app_global_color);//this.getResources().getColor(R.color.tab_child_select_boder_color);
		leftBorderColor=this.getResources().getColor(R.color.tab_child_left_boder_color);
		rightBorderColor=this.getResources().getColor(R.color.tab_child_right_boder_color);
		
		textView=new TextView(context);
		textView.setSingleLine(true);
		textView.setEllipsize(TextUtils.TruncateAt.END);
		textView.setTextSize(14);
		textView.setGravity(Gravity.CENTER);
		this.addView(textView);
		
		imageView = new ImageView(context);
		imageView.setWillNotCacheDrawing(true);
		imageView.setVisibility(View.GONE);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.setMargins(8, 0, 0, 0);
		this.addView(imageView,params);
		
		this.setBackgroundColor(bgColor);
		textView.setTextColor(textColor);
	}
	
	public void setTextView(int text){
		textView.setText(text);
	}
	public void setImageView(int resId){
		imageView.setImageResource(resId);
	}
	public ImageView getImageView(){
		return imageView;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint=new Paint();
        paint.setAntiAlias(true); 
		paint.setStyle(Paint.Style.FILL);
		
		int width=this.getWidth();
		int height=this.getHeight();
		if(this.isSelected()){
			paint.setColor(selectButtonBorderColor);
			canvas.drawRect(0, height-4, width,height, paint);//画选中底部线条
		}else{
			paint.setColor(buttonBorderColor);
			canvas.drawRect(0, height-4, width,height, paint);//画底部线条
		}
		paint.setColor(leftBorderColor);
		canvas.drawRect(0, 0, 2,height, paint);//左边线条
		paint.setColor(rightBorderColor);
		canvas.drawRect(width-2, 0, width,height, paint);//右边线条
		super.onDraw(canvas);
	}
	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		if(selected){
			textView.setTextColor(selectTextColor);
		}else{
			textView.setTextColor(textColor);
		}
	}
}
