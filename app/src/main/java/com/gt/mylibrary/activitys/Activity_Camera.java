package com.gt.mylibrary.activitys;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.gt.mylibrary.R;
import com.gt.mylibrary.base.BaseActivity;
import com.gt.mylibrary.beans.Country;
import com.gt.mylibrary.utils.CameraUtil;
import com.gt.mylibrary.utils.DLog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Activity_Camera extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback {

    private Button button_image;
    private Button button_video;
    private Button button_start;
    private Button button_stop;
    private Button button_pause;
    private ImageView imageView_camera;
    private SurfaceView surfaceView_video;
    private SurfaceHolder holder;
    private MediaPlayer mp;
    private Uri uri;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int MEDIA_TYPE_VIDEO = 2;
    private List<File> files = new ArrayList<>();
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
        initListener();
    }

    private Uri getOutputMediaFileUri(int type) {
        File file = getOutputMediaFile(type);
        files.add(file);
        return Uri.fromFile(file);
    }

    private File getOutputMediaFile(int type) {
        File mediaFile = null;
        String path = null;
        if (type == MEDIA_TYPE_IMAGE) {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
            mediaFile = new File(path + File.separator + System.currentTimeMillis() + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getPath();
            mediaFile = new File(path + File.separator + System.currentTimeMillis() + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_image:
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputMediaFileUri(MEDIA_TYPE_IMAGE));
                startActivityForResult(intent, MEDIA_TYPE_IMAGE);
                break;
            case R.id.button_video:
                Intent intent1 = new Intent();
                intent1.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, getOutputMediaFileUri(MEDIA_TYPE_VIDEO));
                intent1.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
                startActivityForResult(intent1, MEDIA_TYPE_VIDEO);
                break;
            case R.id.button_pause:
                mp.pause();
                break;
            case R.id.button_start:
                mp.start();
                break;
            case R.id.button_stop:
                mp.stop();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (requestCode == MEDIA_TYPE_IMAGE) {
                File file = files.get(0);
                imageView_camera.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView_camera.setImageURI(Uri.fromFile(file));
            } else if (requestCode == MEDIA_TYPE_VIDEO){
                uri = data.getData();
                holder = surfaceView_video.getHolder();
                holder.addCallback(this);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setDisplay(holder);
        try {
            mp.setDataSource(this,uri);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mp != null){
            if (mp.isPlaying()){
                mp.stop();
                mp.release();
            }
        }
    }
    private void initView(){
        button_image = (Button) findViewById(R.id.button_image);
        imageView_camera = (ImageView) findViewById(R.id.imageView_camera);
        button_video = (Button) findViewById(R.id.button_video);
        surfaceView_video = (SurfaceView) findViewById(R.id.surfaceview_video);
        button_start = (Button) findViewById(R.id.button_start);
        button_stop = (Button) findViewById(R.id.button_stop);
        button_pause = (Button) findViewById(R.id.button_pause);
    }
    private void initListener(){
        button_video.setOnClickListener(this);
        button_pause.setOnClickListener(this);
        button_start.setOnClickListener(this);
        button_stop.setOnClickListener(this);
        button_image.setOnClickListener(this);
    }

}
