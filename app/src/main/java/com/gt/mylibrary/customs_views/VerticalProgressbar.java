package com.gt.mylibrary.customs_views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2016/3/26.
 */
public class VerticalProgressbar extends ProgressBar {

    public VerticalProgressbar(Context context) {
        super(context);
    }

    public VerticalProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public VerticalProgressbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // 旋转
        canvas.rotate(-90);
        canvas.translate(-this.getHeight(), 0);
        super.onDraw(canvas);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());//互换宽高值
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldw, oldh);//互换宽高值
    }

}