package com.gt.mylibrary.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gt.mylibrary.R;
import com.gt.mylibrary.base.BaseActivity;
import com.gt.mylibrary.constants.Constant_mine;
import com.gt.mylibrary.utils.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

public class Activity_Volley extends BaseActivity implements View.OnClickListener {

    private Button b_StringRequestGet;
    private TextView t_StringRequest;
    private Button b_StringRequestPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        initView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button_StringRequestGet:
                VolleyUtil.GETRequestString(Constant_mine.URL,new Listener());
                break;
            case R.id.button_StringRequestPost:
                VolleyUtil.POSTRequestString(Constant_mine.FIRST_PAGE_WEBVIEW,getMap(10,0,0),new Listener());
                break;
            default:
                break;
        }
    }

    private void initView(){
        b_StringRequestGet = (Button) findViewById(R.id.button_StringRequestGet);
        b_StringRequestPost = (Button) findViewById(R.id.button_StringRequestPost);
        t_StringRequest = (TextView) findViewById(R.id.text_StringRequest);
        b_StringRequestGet.setOnClickListener(this);
        b_StringRequestPost.setOnClickListener(this);
    }

    private Map<String,String> getMap(int reqnum,int pageflag,int buttonmore){
        Map<String,String> params = new HashMap<>();
        params.put("reqnum",reqnum + "");
        params.put("pageflag",pageflag + "");
        params.put("buttonmore",buttonmore + "");
        return params;
    }

    private class Listener implements VolleyUtil.OnStringRequest{

        @Override
        public void OnSuccess(String successResult) {
            t_StringRequest.setText(successResult);
        }

        @Override
        public void OnError(VolleyError errorResult) {

        }
    }

}
