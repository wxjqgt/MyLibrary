package com.gt.mylibrary.activitys;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

public class BaseActivity extends Activity {

    private ArrayList<Activity> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.add(this);
    }

    public void exit(){
        for (Activity a:list) {
            a.finish();
        }
    }

}
