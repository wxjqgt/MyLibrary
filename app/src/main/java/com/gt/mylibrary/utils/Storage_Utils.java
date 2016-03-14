package com.gt.mylibrary.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.SparseArray;

import com.gt.mylibrary.app.App_mine;
import com.gt.mylibrary.beans.FileProperty;
import com.gt.mylibrary.constants.Constant_mine;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wxjqgt on 2015/12/31.
 */
public class Storage_Utils {

    public static void UpdateData(String type) {
        if (type.equals(Constant_mine.MOVIES)
                || type.equals(Constant_mine.DOCUMENT)
                || type.equals(Constant_mine.IMAGES)
                || type.equals(Constant_mine.MUSICS)
                ) {
            scanFile(type);
        }
    }

    private static void scanFile(String type) {
        List<File> list = null;
        SparseArray<Object> data = getDatas();
        Map<String, Object> mapInternal = (Map<String, Object>) data.get(Constant_mine.INTERNALSTORAGE);
        Map<String, Object> mapSD = (Map<String, Object>) data.get(Constant_mine.SDSTORAGE);
        if (mapInternal != null && mapInternal.size() != 0) {
            list = (List<File>) mapInternal.get(type);
            if (mapSD != null && mapSD.size() != 0) {
                list.addAll((List<File>) mapSD.get(type));
            }
        }
        if (list != null && list.size() != 0) {
            for (File f : list) {
                MediaScannerConnection.scanFile(App_mine.context_app, new String[]{f.getAbsolutePath()}, null, null);
            }
        }
    }

    public static SparseArray<Object> getDatas() {
        SparseArray<Object> storages = Loadedata();
        List<File> list = (List<File>) storages.get(Constant_mine.SDSTORAGE);
        Map<String, Object> map = null;
        if (list != null) {
            map = getMap(list);
        }
        List<File> list1 = (List<File>) storages.get(Constant_mine.INTERNALSTORAGE);
        Map<String, Object> map1 = getMap(list1);
        SparseArray<Object> data = new SparseArray<>();
        data.put(Constant_mine.SDSTORAGE, map);
        data.put(Constant_mine.INTERNALSTORAGE, map1);
        return data;
    }

    //equalsIgnoreCase不区分大小写
    private static Map<String, Object> getMap(List<File> list) {
        Map<String, Object> map = new HashMap<>();
        List<File> misics = new ArrayList<>();
        List<File> movies = new ArrayList<>();
        List<File> images = new ArrayList<>();
        List<File> document = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            File file = list.get(i);
            String name = file.getName().trim();
            String fileType = getTypeName(name);
            if ("mp3".equalsIgnoreCase(fileType)
                    || "ape".equalsIgnoreCase(fileType)
                    || "wma".equalsIgnoreCase(fileType)
                    || "wav".equalsIgnoreCase(fileType)
                    || "mod".equalsIgnoreCase(fileType)
                    || "ra".equalsIgnoreCase(fileType)
                    || "cd".equalsIgnoreCase(fileType)
                    || "md".equalsIgnoreCase(fileType)
                    || "asf".equalsIgnoreCase(fileType)
                    || "aac".equalsIgnoreCase(fileType)
                    || "md".equalsIgnoreCase(fileType)
                    || "mp3pro".equalsIgnoreCase(fileType)
                    || "vqf".equalsIgnoreCase(fileType)
                    || "flac".equalsIgnoreCase(fileType)
                    || "mid".equalsIgnoreCase(fileType)
                    || "ogg".equalsIgnoreCase(fileType)
                    || "m4a".equalsIgnoreCase(fileType)
                    || "aac+".equalsIgnoreCase(fileType)
                    || "aiff".equalsIgnoreCase(fileType)
                    || "au".equalsIgnoreCase(fileType)
                    || "vqf".equalsIgnoreCase(fileType)
                    ) {
                misics.add(file);
            } else if (fileType.equalsIgnoreCase(".mp4")
                    || fileType.equalsIgnoreCase(".3gp")
                    || fileType.equalsIgnoreCase(".wmv")
                    || fileType.equalsIgnoreCase(".ts")
                    || fileType.equalsIgnoreCase(".rmvb")
                    || fileType.equalsIgnoreCase(".mov")
                    || fileType.equalsIgnoreCase(".m4v")
                    || fileType.equalsIgnoreCase(".avi")
                    || fileType.equalsIgnoreCase(".m3u8")
                    || fileType.equalsIgnoreCase(".3gpp")
                    || fileType.equalsIgnoreCase(".3gpp2")
                    || fileType.equalsIgnoreCase(".mkv")
                    || fileType.equalsIgnoreCase(".flv")
                    || fileType.equalsIgnoreCase(".divx")
                    || fileType.equalsIgnoreCase(".f4v")
                    || fileType.equalsIgnoreCase(".rm")
                    || fileType.equalsIgnoreCase(".asf")
                    || fileType.equalsIgnoreCase(".ram")
                    || fileType.equalsIgnoreCase(".mpg")
                    || fileType.equalsIgnoreCase(".v8")
                    || fileType.equalsIgnoreCase(".swf")
                    || fileType.equalsIgnoreCase(".m2v")
                    || fileType.equalsIgnoreCase(".asx")
                    || fileType.equalsIgnoreCase(".ra")
                    || fileType.equalsIgnoreCase(".ndivx")
                    || fileType.equalsIgnoreCase(".xvid")) {
                movies.add(file);
            } else if ("txt".equals(fileType)) {
                document.add(file);
            } else if ("jpg".equals(fileType)) {
                images.add(file);
            }
        }
        map.put(Constant_mine.MUSICS, misics);
        map.put(Constant_mine.MOVIES, movies);
        map.put(Constant_mine.IMAGES, images);
        map.put(Constant_mine.DOCUMENT, document);
        return map;
    }

    public static SparseArray<Object> Loadedata() {
        List<File> InternalStorageList = new ArrayList<>();
        List<File> SDStorageList = new ArrayList<>();
        SparseArray<Object> l = new SparseArray<>();
        boolean isMoutedAble = isMoutedAble();
        if (isMoutedAble == true) {
            File InternalStorage = Environment.getExternalStorageDirectory();
            getFilesList(InternalStorageList, InternalStorage);
            String SDpath = getExtSDCardPath();
            String path = SDpath.substring(SDpath.lastIndexOf("/") + 1, SDpath.length());
            if (SDpath != null && (path.substring(0, path.length() - 1)).equals("sdcard")) {
                String pt = InternalStorage.toString().substring(0, InternalStorage.toString().lastIndexOf("/") - 1);
                String p = pt.substring(0, pt.lastIndexOf("/") + 1) + path;
                File SDStorage = new File(p);
                getFilesList(SDStorageList, SDStorage);
                l.put(Constant_mine.SDSTORAGE, SDStorageList);
            }
            l.put(Constant_mine.INTERNALSTORAGE, InternalStorageList);
            return l;
        }
        return null;
    }

    //获取外置存储卡的根路径，如果没有外置存储卡，则返回null
    public static String getExtSDCardPath() {
        String sdcard_path = null;
        String sd_default = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        if (sd_default.endsWith("/")) {
            sd_default = sd_default.substring(0, sd_default.length() - 1);
        }
        // 得到路径
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;
                if (line.contains("fat") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                } else if (line.contains("fuse") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sdcard_path;
    }

    private static List<File> getFilesList(List<File> fileList, File f) {
        List<File> list = fileList;
        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                list.add(file);
            } else if (file.isDirectory()) {
                getFilesList(list, file);
            }
        }
        return list;
    }

    public static boolean isMoutedAble() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static String getTypeName(String s) {
        String s1 = s.substring(s.indexOf(".") + 1, s.length());
        if (s1.indexOf(".") >= 0) {
            s = s1;
            s1 = getTypeName(s);
        }
        return s1;
    }

    public static String getFileLength(File file) {
        String length = null;
        File f = file;
        long len = 0;
        if (f.exists() && f.isFile()) {
            len = f.length();
            length = convertFileSize(len);
        }
        return length;
    }

    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    public static FileProperty getFileProperty(File file) {
        File aFile = file;
        FileProperty fp = null;
        if (aFile != null) {
            fp = new FileProperty();
            //文件绝对路径
            String absolutePath = aFile.getAbsolutePath();
            fp.setAbsolutePath(absolutePath);
            //是否可读
            boolean canRead = aFile.canRead();
            fp.setCanRead(canRead);
            //是否可写
            boolean canWrite = aFile.canWrite();
            fp.setCanWrite(canWrite);
            //文件名
            String name = aFile.getName();
            fp.setName(name);
            //文件所在上级目录
            String parent = aFile.getParent();
            fp.setParent(parent);
            //文件所在目录（包括文件名）
            String path = aFile.getPath();
            fp.setPath(path);
            //文件长度
            long length = aFile.length();
            fp.setLength(convertFileSize(length));
            //文件上次修改时间
            //从00:00:00 GMT, January 1, 1970开始的长整型数
            long lastModified = aFile.lastModified();
            fp.setLastModified(refFormatDate(lastModified));
            //文件是否被隐藏
            boolean isHidden = aFile.isHidden();
            fp.setHidden(isHidden);
        }
        return fp;
    }

    public static String refFormatDate(long time) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        Date d = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String retStrFormatNowDate = format.format(d);
        return retStrFormatNowDate;
    }

    /**
     * 获取指定文件列表的全路径，如果对应文件存在则返回文件的路径，如果对应文件不存在，则路径为空
     * eg:
     * List<String> filePathList = FileUtils.getExistFiles(this,new String[]{"1.ptr","2.hst","3.dct"});
     * 如果1.ptr和3.dct存在，2.hst不存在则返回的结果是：
     * filePathList.get(0) = /mnt/sdcard/1.ptr
     * filePathList.get(1) = null
     * filePathList.get(2) = /mnt/sdcard/3.dct
     */
    public static List<String> getExistFiles(final Context context, final String[] fileNames) {
        if (null == context || null == fileNames || fileNames.length == 0) {
            return null;
        }
        String filePath = null;
        String suffix = null;
        String linkType = null;
        List<String> filePathList = new ArrayList<String>();
        for (String fileName : fileNames) {
            suffix = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
            linkType = " LIKE '%" + suffix + "'";
            ContentResolver contentResolver = context.getContentResolver();
            if (null != contentResolver) {
                Uri uri = MediaStore.Files.getContentUri("external");
                // 为了效率起见不应当传入null,否则默认取出所有的字段。这里应当根据自己的需求来定，比如下面只需要路径、大小信息
                Cursor cursor = contentResolver.query(uri,
                        new String[]{MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.SIZE},
                        MediaStore.Files.FileColumns.DATA + linkType, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                            if (filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length()).equals(fileName)) {
                                filePathList.add(filePath);
                                break;
                            }
                        } while (cursor.moveToNext());
                    }
                    if (!cursor.isClosed()) {
                        cursor.close();
                    }
                }
            }
        }
        return filePathList;
    }

    public static Bitmap decodeSampledBitmapFromResource(
            Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //先将inJustDecodeBounds属性设置为true,解码避免内存分配
        options.inJustDecodeBounds = true;
        // 将图片传入选择器中
        BitmapFactory.decodeResource(res, resId, options);
        // 对图片进行指定比例的压缩
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        //待图片处理完成后再进行内存的分配，避免内存泄露的发生
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    // 计算图片的压缩比例
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择长宽高较小的比例，成为压缩比例
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}

