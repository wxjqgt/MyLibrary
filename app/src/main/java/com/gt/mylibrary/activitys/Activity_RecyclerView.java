package com.gt.mylibrary.activitys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gt.mylibrary.R;
import com.gt.mylibrary.adapters.MyRecyclerViewAdapter;

public class Activity_RecyclerView extends BaseActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        // 创建数据集
        String[] dataset = new String[100];
        for (int i = 0; i < dataset.length; i++) {
            dataset[i] = "item" + i;
        }
        // 创建Adapter，并指定数据集
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(dataset);
        // 设置Adapter
        mRecyclerView.setAdapter(adapter);
    }

}
