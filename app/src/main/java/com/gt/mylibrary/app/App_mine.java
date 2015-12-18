package com.gt.mylibrary.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.gt.mylibrary.R;
import com.gt.mylibrary.utils.VolleyUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.xutils.x;

/**
 * Created by wxjqgt on 2015/12/17.
 */

public class App_mine extends Application {

    public static RequestQueue request;
    public static Context context_app;
    public static ImageLoader universalimageloader;
    public static SharedPreferences sp;
    public static VolleyUtil volleyUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志
        universalimageloader = initImageLoader(getApplicationContext());
        request = Volley.newRequestQueue(getApplicationContext());
        context_app = getApplicationContext();
        sp = getSharedPreferences("kugoumusic",MODE_PRIVATE);
        volleyUtil = new VolleyUtil();
    }

    private ImageLoader initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        config.memoryCacheSizePercentage(20);
        config.diskCacheFileCount(100);
        config.defaultDisplayImageOptions(new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .cacheOnDisk(true)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .imageScaleType(ImageScaleType.NONE)
                //.delayBeforeLoading(3000)
                //*.displayer(new BitmapDisplayer() {
        /*@Override
        public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
            Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bitmap1);
            BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Paint paint = new Paint();
            paint.setShader(shader);
            c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getHeight()/1.5f,paint);
            imageAware.setImageBitmap(bitmap1);
        }
    })*/
                .build());

        // Initialize ImageLoader with configuration.
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config.build());
        return imageLoader;
    }

}
