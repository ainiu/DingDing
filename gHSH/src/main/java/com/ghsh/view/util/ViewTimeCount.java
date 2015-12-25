package com.ghsh.view.util;

import com.ghsh.util.DateFormatUtil;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class ViewTimeCount extends Handler{
	private TextView timeView;
	private long totalTime;
	private boolean isRun=true;
	public ViewTimeCount(TextView timeView, long totalTime) {
		this.timeView = timeView;
		this.totalTime=totalTime;
	}
	@Override
	public void handleMessage(Message msg) {
		totalTime=totalTime-1;
		if(totalTime<=0){
			this.timeView.setText(DateFormatUtil.getTimeFromIntB(0));
		}else{
			this.timeView.setText(DateFormatUtil.getTimeFromIntB(totalTime));
			if(isRun){
				this.sendEmptyMessageDelayed(0, 1000);
			}
		}
	}
	public void start(){
		this.sendEmptyMessageDelayed(0, 1000);
	}
	
	public void cancel(){
		this.isRun=false;
	}
}
