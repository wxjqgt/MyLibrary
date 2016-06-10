package com.gt.mylibrary.base;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gt.mylibrary.R;
import com.gt.mylibrary.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/8.
 */
public abstract class CommonPagerAdapter<T> extends PagerAdapter {

    private List<T> datas;

    public CommonPagerAdapter(List<T> datas) {
        this.datas = new ArrayList<>();
        addDatas(datas);
    }

    public void addDatas(List<T> datas){
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        T t = null;
        if (datas != null && datas.size() != 0) {
            t = datas.get(position);
        }
        View view = convert(t,position);
        container.addView(view);
        return view;
    }

    public abstract View convert(T t,int position);

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
