package com.baidu.dingding.view.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.baidu.dingding.R;

/**
 * Created by Administrator on 2015/12/3.
 */
public class SlideButton extends View {
    private Bitmap slideBitMap;// 滑动图片
    private Bitmap switchBitMap;// 背景图片

    private int slideBitMapWidth;// 滑动图片宽度
    private int switchBitMapWidth;// 背景图片宽度
    private int switchBitMapHeight;// 背景图片高度

    private boolean currentState;// 开关状态
    private boolean isSliding = false; // 是否正在滑动中

    private int currentX; // 当前开关的位置

    private OnToggleStateChangedListener mChangedListener;// 回调接口

    /**
     * @param context
     *            在java代码中直接调用使用此构造方法
     */
    public SlideButton(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     *            在xml中使用要用到这个方法
     */
    public SlideButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     *            指定一个样式
     */
    public SlideButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBitmap();
    }

    /**
     * @category 加载背景图片以及开关图片 然后获取各自的宽高
     *
     */
    private void initBitmap() {
        // TODO Auto-generated method stub
        slideBitMap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ding_155);
        switchBitMap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ding_26);
        slideBitMapWidth = slideBitMap.getWidth();
        switchBitMapWidth = switchBitMap.getWidth();
        switchBitMapHeight = switchBitMap.getHeight();
        Log.i("switchBitMapWidth", switchBitMapWidth + "");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(switchBitMapWidth, switchBitMapHeight);// 设置控件的宽高
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制button背景图片
        canvas.drawBitmap(switchBitMap, 0, 0, null);
        // 绘制滑动开关
        if (isSliding) {// 如果当前状态是滑动中 则动态绘制开关
            int dis = currentX - slideBitMapWidth / 2;
            if (dis < 0) {
                dis = 0;
            } else if (dis > switchBitMapWidth - slideBitMapWidth) {
                dis = switchBitMapWidth - slideBitMapWidth;
            }
            canvas.drawBitmap(slideBitMap, dis, 0, null);
        } else {
            if (currentState) { // 绘制开关为开的状态
                canvas.drawBitmap(slideBitMap, switchBitMapWidth
                        - slideBitMapWidth, 0, null);
            } else { // 绘制开关为关的状态
                canvas.drawBitmap(slideBitMap, 0, 0, null);
            }
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 手势识别 判断滑动方向
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isSliding = true;
                currentX = (int) event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                currentX = (int) event.getX();
                Log.i("currentX", currentX + "");

                break;
            case MotionEvent.ACTION_UP:
                isSliding = false;
                int bgCenter = switchBitMapWidth / 2;
                boolean state = currentX > bgCenter; // 改变后的状态
                if (state != currentState && mChangedListener != null) {// 添加回调
                    mChangedListener.onToggleStateChanged(state);
                }
                currentState = state;
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    public OnToggleStateChangedListener getmChangedListener() {
        return mChangedListener;
    }

    public void setmChangedListener(
            OnToggleStateChangedListener mChangedListener) {
        this.mChangedListener = mChangedListener;
    }

    public boolean isToggleState() {
        return currentState;
    }

    public void setToggleState(boolean currentState) {
        this.currentState = currentState;
    }
    public interface OnToggleStateChangedListener {
         void onToggleStateChanged(boolean state);

    }

}
