package com.gt.mylibrary.customs_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gt.mylibrary.R;

import java.util.ArrayList;
import java.util.List;

public class NavVIew extends LinearLayout {
	
	private TextView tv;
	private ImageView iv;
	
	public NavVIew(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public NavVIew(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}


	public NavVIew(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	public void setData(String text,int res) {
		if (text != null) {
			tv.setText(text);
		}
		if (res != 0) {
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
			iv.setImageBitmap(bitmap);
		}
	}
	
	public void setClick() {
		tv.setTextColor(Color.WHITE);
		iv.setVisibility(VISIBLE);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.login_bg_on);
		iv.setImageBitmap(bitmap);
		String text = (String) tv.getText();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < text.length(); i++) {			
			list.add(text.substring(i,i+1));
		}
		iv.setLayoutParams(new LayoutParams(list.size() * 30, 3));
	}
	
	public void setNotClick() {
		tv.setTextColor(Color.parseColor("#abc1ab"));
		iv.setVisibility(GONE);
	}
	
	private void init(Context context){
		inflate(context, R.layout.nav, this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setTextSize(15);
		tv.setTextColor(Color.parseColor("#abc1ab"));
		iv = (ImageView) findViewById(R.id.iv_line);
	}
	
}
