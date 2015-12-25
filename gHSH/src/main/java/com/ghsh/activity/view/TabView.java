package com.ghsh.activity.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghsh.view.util.SelectorUtil;
import com.ghsh.R;

public class TabView extends LinearLayout {
	private Context context;
	private int bgColor,selectBgColor;
	private int textColor,selectTextColor;
	private TextView textView;
	private ImageView imageView;
	public TabView(Context context) {
		super(context);
		this.context=context;
		this.setOrientation(LinearLayout.VERTICAL);
		this.setGravity(Gravity.CENTER);
		this.initView();
	}
	private void initView(){
		View view=((Activity)context).getLayoutInflater().inflate(R.layout.view_main_tab_item, null);
		imageView=(ImageView)view.findViewById(R.id.tab_item_icon_view);
		textView=(TextView)view.findViewById(R.id.tab_item_text_view);
		
//		imageView = new ImageView(context);
//		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		params.setMargins(0, 15, 0, 0);
//		this.addView(imageView,params);
//		
//		textView=new TextView(context);
//		textView.setTextSize(12);
//		textView.setGravity(Gravity.CENTER);
//		this.addView(textView);
		this.addView(view);
	}
	
	public void setImageView(Drawable icon, Drawable selectIcon){
		StateListDrawable listDrawable =SelectorUtil.createSelectorDrawable(context, icon, selectIcon);
//		imageView.setBackgroundDrawable(listDrawable);
		imageView.setImageDrawable(listDrawable);
	}
	
	public void setTextView(String text,int textColor,int selectTextColor){
		this.textColor=textColor;
		this.selectTextColor=selectTextColor;
		textView.setTextColor(textColor);
		textView.setText(text);
	}
	public void setBgView(int bgColor,int selectBgColor){
		this.bgColor=bgColor;
		this.selectBgColor=selectBgColor;
	}
	
	/**画三角形*/
	private void drawTriangle(Canvas canvas,Paint paint){
		Path path = new Path();
		int x=this.getWidth()/2;
		path.moveTo(x, 0);
		path.lineTo(x-10, 10);
		path.lineTo(x+10, 10);
		path.close();
		canvas.drawPath(path, paint);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint=new Paint();
        /*去锯齿*/  
        paint.setAntiAlias(true); 
		/* 设置paint　的style为　FILL：实心 */
		paint.setStyle(Paint.Style.FILL);
		if(isSelected()){
			/* 设置paint的颜色 */
			paint.setColor(selectBgColor);
			this.drawTriangle(canvas, paint);
		}else{
			paint.setColor(bgColor);
		}
		canvas.drawRect(0, 10, this.getWidth(), this.getHeight(), paint);
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
