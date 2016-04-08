package com.gt.mylibrary.activitys;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.gt.mylibrary.R;
import com.gt.mylibrary.base.BaseActivity;

import java.io.File;
import java.io.IOException;
//录音实例
public class Activity_Recorder extends BaseActivity
        implements View.OnClickListener,MediaRecorder.OnErrorListener {

    private MediaRecorder mr;
    private Button b_start, b_stop;
    private Boolean prepare = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        initView();
        mr = new MediaRecorder();
        initRecorder();
    }

    private void initView() {
        b_start = (Button) findViewById(R.id.button_start);
        b_stop = (Button) findViewById(R.id.button_stop);
        b_start.setOnClickListener(this);
        b_stop.setOnClickListener(this);
    }

    private void initRecorder() {
        mr.reset();
        mr.setAudioSource(MediaRecorder.AudioSource.MIC);
        mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
                + File.separator + System.currentTimeMillis() + ".mp3";
        mr.setOutputFile(path);
        try {
            mr.prepare();
            prepare = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        b_stop.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                if (prepare) {
                    mr.start();
                    b_start.setEnabled(false);
                    b_stop.setEnabled(true);
                    prepare = false;
                }
                break;
            case R.id.button_stop:
                mr.stop();
                b_start.setEnabled(true);
                b_stop.setEnabled(false);
                prepare = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        mr.reset();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mr != null){
            mr.release();
        }
    }
}
