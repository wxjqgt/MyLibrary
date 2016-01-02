package com.gt.mylibrary.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScanReceiver extends BroadcastReceiver {

    public ScanReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        // 当系统开始扫描sd卡时，为了用户体验，可以加上一个等待框
        if (Intent.ACTION_MEDIA_SCANNER_STARTED.equals(action)) {
        }
        // 当系统扫描完毕时，停止显示等待框，并重新查询ContentProvider
        if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action)) {
        }
    }

}
