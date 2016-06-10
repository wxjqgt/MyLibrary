package com.gt.mylibrary;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.gt.aiqiyi.R;
import com.gt.aiqiyi.beans.Data;
import com.gt.aiqiyi.beans.TemplateData;
import com.gt.aiqiyi.constants.AppConstants;
import com.gt.aiqiyi.utils.App_mine;
import com.gt.aiqiyi.utils.VolleyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        setContentView(R.layout.activity_home);
        initView();
        LoadData();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        linearLayout = (LinearLayout) findViewById(R.id.linear_icon);
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
        VolleyUtil.GETRequestString(AppConstants.URL_FULLSPEED, new Quanyuanjiasu());
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
                        lastImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_evenmore_point));
                    }
                    imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_today_point));
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

    public class Quanyuanjiasu implements VolleyUtil.OnStringRequest {

        @Override
        public void OnSuccess(String successResult) {
            try {
                JSONObject object = new JSONObject(successResult);
                String d = object.getString("data");
                List<Data> result = JSON.parseArray(d.toString(), Data.class);

                Data data_banner = result.get(0);
                List<TemplateData> temp = new ArrayList<>();
                List<TemplateData> templateDatas = Arrays.asList(data_banner.getTemplateData());
                temp.add(templateDatas.get(templateDatas.size() - 1));
                temp.addAll(templateDatas);
                temp.add(templateDatas.get(0));
                viewPager.setAdapter(new ViewPagerAdapter(HomeActivity.this, temp));
                for (int i = 0; i < temp.size() - 2; i++) {
                    ImageView imageView = new ImageView(HomeActivity.this);
                    imageView.setImageBitmap(
                            BitmapFactory.decodeResource(
                                    getResources(),
                                    R.mipmap.ic_evenmore_point));
                    linearLayout.addView(imageView);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void OnError(VolleyError errorResult) {

        }

    }

    private static class ViewPagerAdapter extends PagerAdapter {

        private List<TemplateData> templateDatas;
        private Context context;

        public ViewPagerAdapter(Context context, List<TemplateData> templateDatas) {
            this.templateDatas = templateDatas;
            this.context = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            App_mine.universal_imageloader.displayImage(templateDatas.get(position).getPicUrl(), imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return templateDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
