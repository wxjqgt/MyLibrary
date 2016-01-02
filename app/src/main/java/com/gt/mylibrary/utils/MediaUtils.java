package com.gt.mylibrary.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import com.gt.mylibrary.R;
import com.gt.mylibrary.beans.Mp3Info;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MediaUtils {
    //获取专辑封面的Uri
    public static final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");
    /*
    * 根据歌曲id查询歌曲信息
    */
    public static Mp3Info getMp3Info(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                MediaStore.Audio.Media._ID + "=" + id,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        );
        Mp3Info mp3Info = null;
        if (cursor.moveToNext()) {
            mp3Info = new Mp3Info();
            int ismusic = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.IS_MUSIC));
            if (ismusic != 0) {
                mp3Info.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
                mp3Info.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
                mp3Info.setAlbumId(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)));
                mp3Info.setArtist(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                mp3Info.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                mp3Info.setSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
                mp3Info.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                mp3Info.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                mp3Info.setDisplay(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
            }
        }
        cursor.close();
        return mp3Info;
    }

    //查询所有歌曲信息
    public static List<Mp3Info> getMp3InfoList(Context context) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                MediaStore.Audio.Media.DURATION + ">=" + "180000",
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        );
        List<Mp3Info> list = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            Mp3Info mp3Info = new Mp3Info();
            int ismusic = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.IS_MUSIC));
            if (ismusic != 0) {
                mp3Info.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
                mp3Info.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
                mp3Info.setAlbumId(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)));
                mp3Info.setArtist(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                mp3Info.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                mp3Info.setSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
                mp3Info.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                mp3Info.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                mp3Info.setDisplay(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
                list.add(mp3Info);
            }
        }
        cursor.close();
        return list;
    }

    //格式化时间
    public static String formattime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + time % (1000 * 60) + "";
        } else if (sec.length() == 3) {
            sec = "00" + time % (1000 * 60) + "";
        } else if (sec.length() == 2) {
            sec = "000" + time % (1000 * 60) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + time % (1000 * 60) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    //获取专辑默认图片
    public static Bitmap getDefautArtWork(Context context, boolean small) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        if (small) {
            return BitmapFactory.decodeStream(
                    context.getResources().openRawResource(R.mipmap.ic_launcher), null, options);
        }
        return BitmapFactory.decodeStream(
                context.getResources().openRawResource(R.mipmap.ic_launcher), null, options);
    }

    private static Bitmap getArtWorkFile(Context context, long song_id, long album_id) {
        Bitmap bitmap = null;
        if (album_id < 0 && song_id < 0) {
            throw new IllegalArgumentException("Must specify an album or a1 song_id");
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            FileDescriptor fd = null;
            if (album_id < 0) {
                Uri uri = Uri.parse("content://media/external/audio/media/" + song_id + "/albumart");
                ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
                if (parcelFileDescriptor != null) {
                    fd = parcelFileDescriptor.getFileDescriptor();
                }
            }else {
                Uri uri = ContentUris.withAppendedId(albumArtUri,album_id);
                ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
                if (parcelFileDescriptor != null) {
                    fd = parcelFileDescriptor.getFileDescriptor();
                }
            }
            options.inSampleSize = 1;
            //只进行大小判断
            options.inJustDecodeBounds = true;
            //得到歌曲option然后得到图片大小
            BitmapFactory.decodeFileDescriptor(fd,null,options);
            options.inSampleSize = 100;//目标是在800pixel上显示，需要调用computeSampleSixe获取图片缩放比例
            options.inJustDecodeBounds = false;//互殴去图片比例后开始正式写入
            options.inDither = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            //根据option的参数减少所需要的内容
            bitmap = BitmapFactory.decodeFileDescriptor(fd,null,options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getArtWork(Context context, Long song_id,
                                    long abum_id, boolean allowdefault, boolean small) {
        if (abum_id < 0) {
            if (song_id < 0) {
                Bitmap bitmap = getArtWorkFile(context,song_id,-1);
                if (bitmap != null){
                    return bitmap;
                }
            }
            if (allowdefault){
                return getDefautArtWork(context,small);
            }
            return null;
        }
        ContentResolver cr = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(albumArtUri,abum_id);
        if (uri != null){
            InputStream in = null;
            try {
                in = cr.openInputStream(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(in,null,options);
                if (small){
                    options.inSampleSize = computeSampleSize(options,40);
                }else {
                    options.inSampleSize = computeSampleSize(options,600);
                }
                options.inJustDecodeBounds = false;
                options.inDither = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                in = cr.openInputStream(uri);
                return BitmapFactory.decodeStream(in,null,options);
            }catch (Exception e){
                Bitmap bitmap = getArtWorkFile(context,song_id,abum_id);
                if (bitmap != null){
                    if (bitmap.getConfig() == null){
                        bitmap = bitmap.copy(Bitmap.Config.RGB_565,false);
                        if (bitmap == null && allowdefault){
                            return getDefautArtWork(context,small);
                    }
                    }
                }else if (allowdefault){
                    bitmap = getDefautArtWork(context,small);
                }
                return bitmap;
            }finally {
                try{
                    if (in != null){
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static int computeSampleSize(BitmapFactory.Options options,int target){
        int w = options.outWidth;
        int h = options.outHeight;
        int candidatew = w / target;
        int candidateh = h / target;
        int candidate = Math.max(candidateh,candidatew);
        if (candidate == 0){
            return 1;
        }
        if (candidate > 1){
            if (w > target && (w / target) < target){
                candidate -= 1;
            }
        }
        if (candidate > 1){
            if (h > target && (h / target) < target){
                candidate -= 1;
            }
        }
        return candidate;
    }

    public static Uri getArtWorkUri(Context context, Long song_id, long abum_id) {
        if (abum_id < 0) {
            if (song_id < 0) {
                Uri uri = getArtWorkFileUri(song_id,abum_id);
                if (uri != null){
                    return uri;
                }
            }
            return null;
        }
        Uri uri = ContentUris.withAppendedId(albumArtUri,abum_id);
        if (uri != null){
            try {
                return uri;
            }catch (Exception e){
                Uri uri1 = getArtWorkFileUri(song_id,abum_id);
                if (uri1 != null){
                    return uri1;
                }
                return uri;
            }
        }
        return null;
    }

    private static Uri getArtWorkFileUri(long song_id, long album_id) {
        Uri uri = null;
        if (album_id < 0 && song_id < 0) {
            throw new IllegalArgumentException("Must specify an album or a song_id");
        }
        try {
            if (album_id < 0) {
                uri = Uri.parse("content://media/external/audio/media/" + song_id + "/albumart");
            }else {
                uri = ContentUris.withAppendedId(albumArtUri,album_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;
    }

}

























