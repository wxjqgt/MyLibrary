package com.gt.mylibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.gt.mylibrary.base.App_mine;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/4/11.
 */
public class StringRequstUtil {

    public static ExecutorService service = Executors.newSingleThreadExecutor();

    public static void GetStringRequest(final String url, final OnStringRequstListener onStringRequstListener) {
        if (!isNetworkAvailable()) {
            return;
        }
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL u = new URL(url);
                    HttpURLConnection uRLConnection = (HttpURLConnection) u.openConnection();
                    InputStream is = uRLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    StringBuffer response = null;
                    String readLine = null;
                    while ((readLine = br.readLine()) != null) {
                        response.append(readLine);
                    }
                    onStringRequstListener.OnStringRequstSuccess(response.toString());
                    is.close();
                    br.close();
                    uRLConnection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //向服务器发送post请求
    public static void PostStringRequest(final String url, final String content, final OnStringRequstListener onStringRequstListener) {
        if (!isNetworkAvailable()) {
            return;
        }
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL u = new URL(url);

                    HttpURLConnection uRLConnection = (HttpURLConnection) u.openConnection();
                    uRLConnection.setDoInput(true);
                    uRLConnection.setDoOutput(true);
                    uRLConnection.setRequestMethod("POST");
                    uRLConnection.setUseCaches(false);
                    uRLConnection.setInstanceFollowRedirects(false);
                    uRLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    uRLConnection.connect();

                    DataOutputStream out = new DataOutputStream(uRLConnection.getOutputStream());
                    out.writeBytes(content);
                    out.flush();
                    out.close();

                    InputStream is = uRLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    StringBuffer response = null;
                    String readLine = null;
                    while ((readLine = br.readLine()) != null) {
                        response.append(readLine);
                    }
                    onStringRequstListener.OnStringRequstSuccess(response.toString());
                    is.close();
                    br.close();
                    uRLConnection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static boolean isNetworkAvailable() {
        Context context = App_mine.context_app;
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    //System.out.println(i + "===状态===" + networkInfo[i].getState());
                    //System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public interface OnStringRequstListener {
        void OnStringRequstSuccess(String value);
    }

}
