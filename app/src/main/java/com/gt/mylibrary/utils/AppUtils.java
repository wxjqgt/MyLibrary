package com.gt.mylibrary.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gt.mylibrary.base.App_mine;


/**
 * Created by Administrator on 2015/12/2.
 */
public class AppUtils {
    //隐藏键盘方法
    public static void hideInputMethod(View view){
        InputMethodManager methodManager = (InputMethodManager) App_mine.context_app.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (methodManager.isActive()){
            methodManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
