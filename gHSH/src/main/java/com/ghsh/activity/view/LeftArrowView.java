package com.ghsh.activity.view;

import com.ghsh.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
/**
 * 左边箭头
 * */
public class LeftArrowView extends View {

	private int backgroundColor=Color.WHITE;
	private Context context;
	private int arrow=20;
	public LeftArrowView(Context context) {
		super(context);
		this.context=context;
		this.initView();
	}

	public LeftArrowView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		this.context=context;
		this.initView();
	}

	public LeftArrowView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context=context;
		this.initView();
	}
	
	private void initView(){
		arrow=(int)context.getResources().getDimension(R.dimen.height_goods_category_listview_1_arrow);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint=new Paint();
        /*去锯齿*/  
        paint.setAntiAlias(true); 
		/* 设置paint　的style为　FILL：实心 */
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(backgroundColor);
		
		Path path = new Path();
		int y=this.getHeight()/2;
		int wight=this.getWidth();
		
		path.moveTo(wight-(wight-arrow), y);
		path.lineTo(wight, y+arrow);
		path.lineTo(wight, y-arrow);
		path.close();
		canvas.drawPath(path, paint);
		super.onDraw(canvas);
	}

	@Override
	public void setBackgroundColor(int color) {
//		super.setBackgroundColor(color);
		this.backgroundColor=color;
	}
}
