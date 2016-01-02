package com.gt.mylibrary.customs_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by wxjqgt on 2015/12/29.
 */
public class CircleImageView_01 extends ImageView{

    private Paint paint;

    public CircleImageView_01(Context context) {
        super(context);
        init();
    }

    public CircleImageView_01(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleImageView_01(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        int height = bitmap.getHeight();
        int weight = bitmap.getWidth();
        int radiu = height / 2;
        if (height >= weight){
            radiu = weight / 2;
        }
        canvas.drawCircle(getHeight()/2,getWidth()/2,radiu,paint);
    }

    private void init(){
        paint = new Paint();
        paint.setAntiAlias(true);
    }

}
