package com.gt.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.gt.mylibrary.base.App_mine;

/**
 * Created by Administrator on 2016/6/4.
 */
public class SPUtil {

    private static SharedPreferences getShardPreferences() {
        return App_mine.sp;
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getShardPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = getShardPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key) {
        String value = getShardPreferences().getString(key, "default");
        return value;
    }
}
