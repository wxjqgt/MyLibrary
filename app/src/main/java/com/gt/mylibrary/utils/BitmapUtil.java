package com.gt.mylibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4.
 */
public class BitmapUtil {

    //销毁ImageView的资源，回收内存
    public void destoryBitmaps(List<ImageView> list){
        for (int i = 0;i < list.size();i++){
            ImageView imageView = list.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null){
                //解除drawable对View的引用
                drawable.setCallback(null);
            }
        }
    }

    public Bitmap CompressImage(String imagePath,float imageWidth,float imageHeight){
        Bitmap bitmap = null;
        if (imageWidth == 0||imageHeight == 0) {
            return bitmap;
        }
        BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
        bitmapFactoryOptions.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(imagePath,bitmapFactoryOptions);
        int yRadio = (int)Math.ceil(bitmapFactoryOptions.outHeight/imageHeight);
        int xRadio = (int)Math.ceil(bitmapFactoryOptions.outWidth/imageWidth);
        if (yRadio > 1||xRadio > 1){
            if (yRadio > xRadio){
                bitmapFactoryOptions.inSampleSize = yRadio;
            }else {
                bitmapFactoryOptions.inSampleSize = xRadio;
            }
        }
        bitmapFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(imagePath,bitmapFactoryOptions);
        return bitmap;
    }
}
