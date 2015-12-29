package com.gt.mylibrary.customs_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by wxjqgt on 2015/12/25.
 */
public class NavigationBar_RadioButton extends RadioButton {

    private int textSize;
    private int textColor;
    private String text;
    private int imageSize;
    private Paint paint;

    public NavigationBar_RadioButton(Context context) {
        super(context);
        init();
    }

    public NavigationBar_RadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationBar_RadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void init(){
        paint = new Paint();
        paint.setColor(textColor);
        paint.setAntiAlias(true);
    }

}
