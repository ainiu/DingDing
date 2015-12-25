package com.ghsh.view.util;

import com.ghsh.code.exception.AppViewException;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;

public class ViewLeftRightMove implements OnTouchListener,GestureDetector.OnGestureListener{
	private GestureDetector mGestureDetector;// 手势
	private int MAX_WIDTH = 0;//每次自动展开/收缩的范围 
	private final static int sleep_time = 5;//滑动停顿时间 
	private int window_width;// 屏幕的宽度
	private final static int SPEED = 30;//每次自动展开/收缩的速度
	
	private View leftView,rightView;
	private Activity activity;
	public ViewLeftRightMove(Activity activity,View leftView,View rightView){
		this.activity=activity;
		this.leftView=leftView;
		this.rightView=rightView;
		this.init();
	}
	private void init(){
		mGestureDetector = new GestureDetector(this);
		// 禁用长按监听
		mGestureDetector.setIsLongpressEnabled(false);
		leftView.setOnTouchListener(this);
		rightView.setOnTouchListener(this);
		
		leftView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				window_width = activity.getWindowManager().getDefaultDisplay().getWidth();
				MAX_WIDTH = rightView.getWidth();
				RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) leftView.getLayoutParams();
				RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) rightView.getLayoutParams();
				// 注意： 设置layout_left的宽度。防止被在移动的时候控件被挤压
				layoutParams1.width = window_width;
				leftView.setLayoutParams(layoutParams1);
				// 设置layout_right的初始位置.
				layoutParams2.leftMargin = window_width;
				rightView.setLayoutParams(layoutParams2);
				leftView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
	}
	/**移动*/
	public void moveView(boolean isMove){
		if(isMove){
			new AsynMove().execute(-SPEED);
		}else{
			new AsynMove().execute(SPEED);
		}
	}
	
	/**还原*/
	protected void touchCallback(boolean isMove){
		
	}
	
	class AsynMove extends AsyncTask<Integer, Integer, Void> {
		@Override
		protected Void doInBackground(Integer... params) {
			int times = 0;
			if (MAX_WIDTH % Math.abs(params[0]) == 0){// 整除
				times = MAX_WIDTH / Math.abs(params[0]);
			}else{
				times = MAX_WIDTH / Math.abs(params[0]) + 1;// 有余数
			}
			for (int i = 0; i < times; i++) {
				this.publishProgress(params[0]);
				try {
					Thread.sleep(sleep_time);
				} catch (InterruptedException e) {
					AppViewException.onViewException(e);
				}
			}
			return null;
		}

		/**
		 * update UI
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) leftView.getLayoutParams();
			RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) rightView.getLayoutParams();
			// 右移动
			if (values[0] > 0) {
				layoutParams1.leftMargin = Math.min(layoutParams1.leftMargin+ values[0], 0);
				layoutParams2.leftMargin = Math.min(layoutParams2.leftMargin+ values[0], window_width);
			} else {
				// 左移动
				layoutParams1.leftMargin = Math.max(layoutParams1.leftMargin+ values[0], -MAX_WIDTH);
				layoutParams2.leftMargin = Math.max(layoutParams2.leftMargin+ values[0], window_width - MAX_WIDTH);
			}
			leftView.setLayoutParams(layoutParams1);
			rightView.setLayoutParams(layoutParams2);
		}
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
		
		if(e2.getX() - e1.getX() > 120 && Math.abs(velocityX) > 0){
			//向右滑动
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) leftView.getLayoutParams();
			if (layoutParams.leftMargin < -window_width / 2) {
				this.touchCallback(false);
			}
		}
		return false;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		
	}
	@Override
	public void onShowPress(MotionEvent e) {
		
	}
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
}
