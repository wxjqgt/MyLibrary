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
public abstract class CommonPagerAdapter<T> extends android.support.v4.view.PagerAdapter {

    private List<T> datas;
    private int layout;
    private Context context;
    private View itemView;
    private ViewHolder viewHolder;

    public CommonPagerAdapter(List<T> datas, Context context, int layout) {
        this.layout = layout;
        this.context = context;
        itemView = LayoutInflater.from(context).inflate(layout,null,false);
        this.datas = new ArrayList<>();
        addDatas(datas);
        viewHolder = new ViewHolder(itemView,context);
    }

    public void addDatas(List<T> datas){
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        convert(viewHolder,datas.get(position));
        container.addView(itemView);
        return itemView;
    }

    public abstract void convert(ViewHolder viewHolder,T t);

    public static class ViewHolder {

        private View itemView;
        private SparseArray<View> views;
        private Context context;

        public ViewHolder(View itemView,Context context){
            this.itemView = itemView;
            this.views = new SparseArray<>();
            this.context = context;
        }

        public <T extends View> T getView(int viewId) {
            View view = views.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (T) view;
        }

        public void setImage(String url,int viewId){
            ImageView imageView = getView(viewId);
            ImageLoader.load(context,url, R.mipmap.ic_launcher,imageView);
        }

        public void setText(String text,int viewId){
            TextView textView = getView(viewId);
            textView.setText(text);
        }

    }

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
