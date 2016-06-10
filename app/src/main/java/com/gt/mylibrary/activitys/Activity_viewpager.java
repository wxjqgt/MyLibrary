package com.gt.mylibrary.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gt.mylibrary.R;
import com.gt.mylibrary.customs_views.ADViewpager;
import com.gt.mylibrary.base.App_mine;
import com.gt.mylibrary.base.BaseActivity;
import com.gt.mylibrary.customs_views.JumpViewPager;
import com.gt.mylibrary.utils.ImageLoader;
import com.gt.mylibrary.utils.MediaUtils;

import java.util.ArrayList;
import java.util.List;

public class Activity_viewpager extends BaseActivity {

    private JumpViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        List<String> list = new ArrayList<>();
        list.addAll(MediaUtils.getImagesList(App_mine.context_app).subList(1, 5));
        viewpager = (JumpViewPager) findViewById(R.id.viewpager_1);
        viewpager.setAdapter(new ADViewpager.CommonViewPagerAdapter<String>(list) {
            @Override
            public View convert(String s, int position) {
                ImageView imageView = new ImageView(Activity_viewpager.this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLoader.load(App_mine.context_app, s, R.mipmap.ic_launcher, imageView);
                return imageView;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
