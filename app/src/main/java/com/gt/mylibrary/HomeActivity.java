package com.gt.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gt.mylibrary.activitys.MainActivity;
import com.gt.mylibrary.base.BaseActivity;

import java.lang.reflect.Field;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends BaseActivity {

    private ViewPager viewPager;
    private ScheduledExecutorService se;
    private LinearLayout linearLayout;
    private ImageView lastImageView;
    private EdgeEffectCompat leftEdge;
    private EdgeEffectCompat rightEdge;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(msg.what);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        initView();
        LoadData();
    }

    private void initView() {
        //viewPager = (ViewPager) findViewById(R.id.viewpager);
        //linearLayout = (LinearLayout) findViewById(R.id.linear_icon);
    }

    private void LoadData() {
        se = Executors.newSingleThreadScheduledExecutor();
        try {
            Field leftEdgeField = viewPager.getClass().getDeclaredField("mLeftEdge");
            Field rightEdgeField = viewPager.getClass().getDeclaredField("mRightEdge");
            if (leftEdgeField != null && rightEdgeField != null) {
                leftEdgeField.setAccessible(true);
                rightEdgeField.setAccessible(true);
                leftEdge = (EdgeEffectCompat) leftEdgeField.get(viewPager);
                rightEdge = (EdgeEffectCompat) rightEdgeField.get(viewPager);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //VolleyUtil.GETRequestString(AppConstants.URL_FULLSPEED, new Quanyuanjiasu());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                if (position == 4) {
                    viewPager.setCurrentItem(1, false);
                } else if (position == 0) {
                    viewPager.setCurrentItem(3, false);
                } else {
                    ImageView imageView = (ImageView) linearLayout.getChildAt(position - 1);
                    if (lastImageView != null) {
                        //lastImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_evenmore_point));
                    }
                    //imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_today_point));
                    lastImageView = imageView;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(rightEdge!=null&&!rightEdge.isFinished()){//到了最后一张并且还继续拖动，出现蓝色限制边条了
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                    HomeActivity.this.finish();
                }
            }
        });
        viewPager.setCurrentItem(1);
       se.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(viewPager.getCurrentItem() + 1);
            }
        }, 1, 4, TimeUnit.SECONDS);
    }



}
