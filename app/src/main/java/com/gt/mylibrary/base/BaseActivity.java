package com.gt.mylibrary.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    private ArrayList<Activity> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent,name,context,attrs);
                if (view != null&&view instanceof TextView){
                    ((TextView) view).setTypeface(
                            Typeface.createFromAsset(getAssets(), "hwxk.ttf"));
                }
                return view;
            }
        });*/
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
