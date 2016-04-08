package com.gt.mylibrary.base;

/**
 * Created by Administrator on 2016/4/8.
 */
public interface MultiItemTypeSupport<T>
{
    int getLayoutId(int position, T t);

    int getViewTypeCount();

    int getItemViewType(int postion, T t);
}
