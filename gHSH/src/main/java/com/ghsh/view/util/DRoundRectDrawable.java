package com.ghsh.view.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
/**
 * 圆角
 * */
public class DRoundRectDrawable extends ShapeDrawable {
	private final Paint bgPaint, borderPaint;

	public DRoundRectDrawable(int radius) {
		super(new RoundRectShape(new float[] { radius, radius, radius, radius, radius, radius, radius, radius }, null, null));
		bgPaint = new Paint(this.getPaint());
		borderPaint = new Paint(bgPaint);
		borderPaint.setStyle(Paint.Style.STROKE);
	}

	@Override
	protected void onDraw(Shape shape, Canvas canvas, Paint paint) {
		shape.draw(canvas, bgPaint);
		shape.draw(canvas, borderPaint);
	}
	public void setBackgroundColor(int color){
		bgPaint.setColor(color);
	}
	public void setBorderColor(int color,int borderWidth){
		borderPaint.setColor(color);
		borderPaint.setStrokeWidth(borderWidth);
	}
}