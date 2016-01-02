package com.gt.mylibrary.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.gt.mylibrary.R;
import com.gt.mylibrary.beans.Mp3Info;
import com.gt.mylibrary.configs.Config_mine;
import com.gt.mylibrary.utils.MediaUtils;
import com.gt.mylibrary.utils.Storage_Utils;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Activity_Custom extends BaseActivity {

    private ExecutorService e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        e = Executors.newSingleThreadExecutor();
        e.execute(r);
        /*IntentFilter intentFilter = new IntentFilter(
                Intent.ACTION_MEDIA_SCANNER_STARTED);
        intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
        intentFilter.addDataScheme("file");
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                Uri.parse("file://" + Environment.getExternalStorageDirectory()
                        .getAbsolutePath())));*/
    }

    /**
     * 通知媒体库更新文件
     * @param context
     * @param filePath 文件全路径
     *
     * */
    public void scanFile(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }

    /**
     * 通知媒体库更新文件夹
     * @param context
     * @param filePath 文件夹
     *
     * */
    public void scanFileDir(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            Storage_Utils.UpdateList(Config_mine.MUSICS);
            List<Mp3Info> list = MediaUtils.getMp3InfoList(getApplicationContext());
            for (Mp3Info m:list) {
                System.out.println("m = " + m.getUrl());
            }
        }
    };

}
