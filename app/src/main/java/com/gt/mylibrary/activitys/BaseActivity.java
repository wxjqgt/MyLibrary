package com.gt.mylibrary.activitys;

import android.app.Activity;
import android.os.Bundle;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

public class BaseActivity extends Activity {

    private ArrayList<Activity> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.add(this);
        x.view().inject(this);
    }

    public void exit(){
        for (Activity a:list) {
            a.finish();
        }
    }

}
