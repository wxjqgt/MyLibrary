package com.gt.mylibrary.customs_views;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gt.mylibrary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/6/8.
 */
public class ADViewpager extends FrameLayout implements ViewPager.OnPageChangeListener{

    private static final int OK = 0x1;

    private ScheduledExecutorService se;

    private Handler handler;
    private OnPagerChangeListener onPagerChangeListener;

    private Context context;
    private boolean recycle = false;

    private int frontRes, reversRes, seconds = 5, count;
    private LinearLayout linearLayout;
    private ViewPager viewPager;
    private ImageView lastImageView;

    public void setAdapter(PagerAdapter pagerAdapter) {
        viewPager.setAdapter(pagerAdapter);
        count = pagerAdapter.getCount();
        build();
    }

    public void setIconGravity(int gravity) {
        linearLayout.setGravity(gravity);
    }

    public void setCycleTime(int seconds) {
        if (seconds > 0) {
            this.seconds = seconds;
        }
    }

    public void startCycle() {
        recycle = true;
    }

    public void setIcon(int frontRes, int reversRes) {
        this.frontRes = frontRes;
        this.reversRes = reversRes;
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.viewpager, this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        linearLayout = (LinearLayout) findViewById(R.id.linear_icon);

        initThreadPool();
        initHandler();
        se.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (recycle) {
                    Message msg = Message.obtain();
                    msg.what = OK;
                    msg.arg1 = viewPager.getCurrentItem() + 1;
                    handler.sendMessage(msg);
                    //msg.recycle();
                }
            }
        }, 1, seconds, TimeUnit.SECONDS);
    }

    private void build() {
        viewPager.setOnPageChangeListener(this);
        int icons = count - 2;
        for (int i = 0; i < icons; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(reversRes);
            linearLayout.addView(imageView);
        }

        viewPager.setCurrentItem(1);
    }

    public void recycle() {
        if (se != null && !se.isShutdown()) {
            se.shutdown();
            se = null;
        }
    }

    public void stopCycle() {
        recycle = false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (onPagerChangeListener != null) {
            onPagerChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        int p = position;
        if (position == count - 1) {
            p = 1;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(1, false);
                }
            }, 1000);
        } else if (position == 0) {
            p = count - 2;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(count - 2, false);
                }
            }, 1000);
        }
        if (lastImageView != null) {
            lastImageView.setImageResource(reversRes);
        }
        ImageView imageView = (ImageView) linearLayout.getChildAt(p - 1);
        imageView.setImageResource(frontRes);
        lastImageView = imageView;

        if (onPagerChangeListener != null) {
            onPagerChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (onPagerChangeListener != null) {
            onPagerChangeListener.onPageScrollStateChanged(state);
        }
    }

    private void initThreadPool() {
        if (se != null && !se.isShutdown()) {
            return;
        }
        se = Executors.newSingleThreadScheduledExecutor();
    }

    private void initHandler() {
        if (handler != null) {
            return;
        }
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == OK) {
                    viewPager.setCurrentItem(msg.arg1);
                }
            }
        };
    }

    public void setOnPagerChangeListener(OnPagerChangeListener onPagerChangeListener) {
        this.onPagerChangeListener = onPagerChangeListener;
    }

    public interface OnPagerChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }

    public ADViewpager(Context context) {
        super(context);
        init(context);
    }

    public ADViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ADViewpager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public abstract static class CommonViewPagerAdapter<T> extends PagerAdapter {

        private List<T> datas;

        public CommonViewPagerAdapter(List<T> datas) {
            this.datas = new ArrayList<>();
            int size = datas.size();
            this.datas.add(datas.get(size - 1));
            this.datas.addAll(datas);
            this.datas.add(datas.get(0));
        }

        public void addDatas(List<T> datas) {
            this.datas.addAll(datas);
            this.notifyDataSetChanged();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = convert(datas.get(position), position);
            container.addView(view);
            return view;
        }

        public abstract View convert(T t, int position);

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
