package com.ghsh.activity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class DCheckBox extends CheckBox{
	private Context context;
	public DCheckBox(Context context) {
		super(context);
		this.context=context;
		this.initView();
	}
	public DCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		this.initView();
    }

    public DCheckBox(Context context, AttributeSet attrs, int defStyle) {
    	super(context, attrs, defStyle);
    	this.context=context;
    	this.initView();
    }
    
    private void initView(){
    	
    }
    @Override
    protected void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    	Paint paint=new Paint();
    	
    	int width= this.getWidth();
    	int height= this.getHeight();
    	int circleX=width/2;//圆中心点
    	int circleY=height/2;//圆中心点
    	int r=width/2;//圆半径
    	if(this.isChecked()){
    		paint.setStyle(Paint.Style.FILL);
    		paint.setColor(Color.RED);
    		canvas.drawCircle(circleX, circleY,r, paint);
    		paint.setColor(Color.WHITE);
    		canvas.drawText("✓", circleX, circleY, paint);
    	}else{
    		paint.setStyle(Paint.Style.STROKE);
    		paint.setColor(Color.BLACK);
    		paint.setStrokeWidth(2);
    		paint.setAntiAlias(true);
    		canvas.drawCircle(circleX, circleY,r, paint);
    	}
    }
}
