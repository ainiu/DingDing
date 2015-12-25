package com.baidu.dingding.view.Btn;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.baidu.dingding.R;


/**
 * 
 * 自定义控件  
 */
public class JWRadioBtn extends View {

	/**
	 * 画笔,包含了画几何图形、文本等的样式和颜色信息
	 */
	private Paint mPaint;
	/**
	 * 控件文本
	 */
	private String _text = "二零一五年";
	/**
	 * 字体颜色
	 */
	private int textColor = Color.BLACK;
	/**
	 * 字体大小
	 */
	private float textSize = 20;
	/**
	 * 存储当前按下状态
	 */
	private boolean _isPress = false;
	/**
	 * 存储当前选中状态
	 */
	private boolean _isChecked = false;

	/**
	 * 控件的id 该自定义控件的事件处理已经在onTouchEvent里面处理，再调用回调，可以不要id属性
	 */
	private int m_id;

	/**
	 * 选中状态改变后，触发回调
	 */
	private ChangedCheckCallBack mCallback;

	private int _mAscent;

	public JWRadioBtn(Context context) {
		super(context);

		mPaint = new Paint();
		setPadding(20, 5, 20, 5);
		mPaint.setColor(textColor);
		mPaint.setTextSize(textSize);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		// mPaint.setTypeface(Typeface.DEFAULT_BOLD) ;//设置字体
	}

	public JWRadioBtn(Context context, AttributeSet attrs) {
		super(context, attrs);

		mPaint = new Paint();
		mPaint.setAntiAlias(true);// 抗锯齿
		mPaint.setDither(true);// 图像抖动处理
		setPadding(10, 5, 10, 5);

		// TypedArray是一个用来存放由context.obtainStyledAttributes获得的属性的数组
		// 在使用完成后，一定要调用recycle方法
		// 属性的名称是styleable中的名称+“_”+属性名称
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.JWRadioBtn);
		// String strText = array.getText(R.styleable.jwradiobtn_text, "");
		CharSequence text = array.getText(R.styleable.JWRadioBtn_android_text);
		if (text != null)
			_text = text.toString();

		textColor = array.getColor(R.styleable.JWRadioBtn_textColor,
				Color.BLACK); // 提供默认值，放置未指定
		textSize = array.getDimension(R.styleable.JWRadioBtn_textSize, 20);
		mPaint.setColor(textColor);
		mPaint.setTextSize(textSize);

		array.recycle(); // 一定要调用，否则这次的设定会对下次的使用造成影响
	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (_text != null && !_text.equals("")) {
			mPaint.setTextSize(textSize);
			mPaint.setColor(textColor);
			mPaint.setTextAlign(Align.CENTER);// 文字居中显示
			mPaint.setStrokeWidth(0);
			FontMetrics fontMetrics = mPaint.getFontMetrics();
			float fontHeight = fontMetrics.bottom - fontMetrics.top;// 文本高度
			float baseY = this.getHeight() - (this.getHeight() - fontHeight)
					/ 2 - fontMetrics.bottom;
			canvas.drawText(_text, this.getWidth() / 2, baseY, mPaint);// (this.getHeight()
																		// -
																		// fontHeight)/
																		// 2
																		// __额外
			// 设置了mPaint.setTextAlign(Align.CENTER);//文字居中显示
			// 所以canvas.drawText(text,x,y,paint);中，x，为横坐标中点位置
		}

		if (_isPress) {
			// 按下时边框绘制橘黄色
			mPaint.setColor(Color.rgb(255, 165, 0));
			mPaint.setStyle(Style.STROKE); // 设置填充
			mPaint.setStrokeWidth(5);
			canvas.drawRect(1, 1, this.getWidth() - 1, this.getHeight() - 1,
					mPaint); // 绘制矩形
			return;
		}

		if (_isChecked) {
			// 选中时边框绘制红色
			// Canvas中含有很多画图的接口，利用这些接口，我们可以画出我们想要的图形
			// mPaint = new Paint();
			mPaint.setColor(Color.RED);
			mPaint.setStyle(Style.STROKE); // 设置填充
			mPaint.setStrokeWidth(5);
			canvas.drawRect(1, 1, this.getWidth() - 1, this.getHeight() - 1,
					mPaint); // 绘制矩形

			// 绘制右下角的三角形
			mPaint.setStyle(Style.FILL);
			Path path = new Path();
			path.moveTo(this.getWidth(), this.getHeight());
			path.lineTo(this.getWidth(), this.getHeight() - 15);
			path.lineTo(this.getWidth() - 15, this.getHeight());
			canvas.drawPath(path, mPaint);
		} else {
			// 选中时边框绘制黑色
			mPaint.setColor(Color.BLACK);
			mPaint.setStyle(Style.STROKE); // 设置填充
			mPaint.setStrokeWidth(1);
			canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), mPaint); // 绘制矩形
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		// if (event.getAction() == MotionEvent.ACTION_DOWN)
		// return true;

		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.e("JWRadioBtn", "JWRadioBtn_onTouchEvent:" + event.getAction());
		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			_isPress = true;
			this.invalidate();// 重绘，执行onDraw
			return true;// 要返回true，后面的action_up和move才能执行

		case MotionEvent.ACTION_UP:

			if (mCallback != null) {

				mCallback.ChangedCheck();
			}
			_isPress = false;
			setChecked(!_isChecked);
			break;

		case MotionEvent.ACTION_MOVE:
			break;

		default:
			_isPress = false;
			this.invalidate();// 重绘，执行onDraw
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * @see View#measure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
	}

	/**
	 * Determines the width of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The width of the view, honoring constraints from measureSpec
	 */
	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text
			result = (int) mPaint.measureText(_text) + getPaddingLeft()
					+ getPaddingRight();
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}

		return result;
	}

	/**
	 * Determines the height of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The height of the view, honoring constraints from measureSpec
	 */
	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		_mAscent = (int) mPaint.ascent();
		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text (beware: ascent is a negative number)
			result = (int) (-_mAscent + mPaint.descent()) + getPaddingTop()
					+ getPaddingBottom();
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}

		return result;
	}

	public void setChecked(boolean value) {
		_isChecked = value;
		this.invalidate();

		// if (mCallback != null)
		// mCallback.ChangedCheck();
	}

	/**
	 * 
	 * @return 控件状态 true:选中 false :未被选中
	 */
	public boolean getChecked() {
		return _isChecked;
	}

	public void setText(String value) {
		_text = value;
		this.invalidate();
	}

	public String getText() {
		return _text;
	}

	public void setTextColor(int value) {
		textColor = value;
		this.invalidate();
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextSize(float value) {
		textSize = value;
		this.invalidate();
	}

	public float getTextSize() {
		return textSize;
	}

	public void setId(int id) {

		this.m_id = id;
	}

	public int getId() {

		return this.m_id;
	}

	/**
	 * 获取控件宽度
	 * 
	 * @return 文本总宽度 + padding消耗值
	 */
	public int getRealWidth() {
		if (this.getWidth() == 0) {
			Rect rect = new Rect();
			mPaint.getTextBounds(_text, 0, _text.length(), rect);

			return rect.width() + this.getPaddingLeft()
					+ this.getPaddingRight();
		} else {
			return this.getWidth();
		}
	}

	/**
	 * 获取控件高度
	 * 
	 * @return 文本高度+ pading消耗值
	 */
	public int getRealHeight() {
		if (this.getHeight() == 0) {
			Rect rect = new Rect();
			mPaint.getTextBounds(_text, 0, _text.length(), rect);

			return rect.height() + this.getPaddingTop()
					+ this.getPaddingBottom();
		} else {
			return this.getHeight();
		}
	}

	/**
	 * 接口用于回调
	 */
	public interface ChangedCheckCallBack {
		void ChangedCheck();
	}

	public void setCallback(ChangedCheckCallBack callBack) {
		this.mCallback = callBack;
	}
}
