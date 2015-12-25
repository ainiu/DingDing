/*
package com.baidu.dingding.view.topbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.dingding.R;

public class TopBar extends RelativeLayout {

	private Button leftBtn, rightBtn;
	private TextView title;
	private String titleString, leftTextString, rightTextString;
	private float titleTextSize;
	private int titleColor, rightColor, leftColor;
	private Drawable leftBackgroud, rightBackgroud;
	private LayoutParams lBtnLp, rBtnLp, titleLp;

	// 锟斤拷锟斤拷id

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public TopBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		// 锟斤拷锟斤拷rtts.xml
		TypedArray ta = context
				.obtainStyledAttributes(attrs, R.styleable.ToBar);
		// 锟斤拷锟斤拷锟斤拷锟斤拷
		this.titleString = ta.getString(R.styleable.ToBar_title);
		this.leftTextString = ta.getString(R.styleable.ToBar_leftText);
		this.rightTextString = ta.getString(R.styleable.ToBar_rightText);
		// 锟斤拷锟斤拷锟斤拷色
		this.titleColor = ta.getColor(R.styleable.ToBar_titleColor, 0);
		this.leftColor = ta.getColor(R.styleable.ToBar_leftColor, 0);
		this.rightColor = ta.getColor(R.styleable.ToBar_rightColor, 0);
		// 锟斤拷锟斤拷锟斤拷锟斤拷锟叫�
		this.titleTextSize = ta.getDimension(R.styleable.ToBar_titleTextSize,
				14);
		// 锟斤拷锟矫憋拷锟斤拷
		this.leftBackgroud = ta.getDrawable(R.styleable.ToBar_leftBackground);
		this.rightBackgroud = ta.getDrawable(R.styleable.ToBar_rightBackground);
		// 锟斤拷锟斤拷
		ta.recycle();
		// 锟斤拷锟斤拷锟斤拷锟斤拷
		leftBtn = new Button(context);
		rightBtn = new Button(context);
		title = new TextView(context);

		// 锟斤拷锟矫诧拷锟街诧拷锟斤拷
		lBtnLp = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		lBtnLp = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		rBtnLp = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		titleLp = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// 锟斤拷锟斤拷锟斤拷锟�
		lBtnLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		lBtnLp.setMargins(15, 0, 0, 0);
		lBtnLp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		rBtnLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		rBtnLp.setMargins(0, 0, 15, 0);
		rBtnLp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		titleLp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		titleLp.setMargins(0,12,0,12);
		// 锟津布撅拷锟侥硷拷锟斤拷涌丶锟�
		addView(leftBtn, lBtnLp);
		addView(rightBtn, rBtnLp);
		addView(title, titleLp);
		// 锟窖控硷拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
		title.setText(titleString);
		title.setTextSize(titleTextSize);
		title.setTextColor(titleColor);

		leftBtn.setText(leftTextString);
		leftBtn.setTextColor(leftColor);
		leftBtn.setBackground(leftBackgroud);

		rightBtn.setText(rightTextString);
		rightBtn.setTextColor(rightColor);
		rightBtn.setBackground(rightBackgroud);

		leftBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tobarOnClickListener.leftBtnClick();

			}
		});
		rightBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tobarOnClickListener.rightBtnClick();

			}
		});

	}
	//接口回调
	public void setOnToBarClickListener(ToBarOnClickListener tbarOnClickListener) {
			this.tobarOnClickListener=tbarOnClickListener;
	}

	private ToBarOnClickListener tobarOnClickListener;

	public interface ToBarOnClickListener {
		public void leftBtnClick();
		public void rightBtnClick();
	}

}
*/
