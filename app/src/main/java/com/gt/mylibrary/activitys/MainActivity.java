package com.gt.mylibrary.activitys;


import android.os.Bundle;
import android.view.Menu;

import com.gt.mylibrary.R;
import com.gt.mylibrary.base.BaseActivity;
import com.gt.mylibrary.customs_views.Radarview;

public class MainActivity extends BaseActivity {

    private Radarview radarview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radarview = (Radarview) findViewById(R.id.radar_view);

        radarview.setPreParam((byte)0x01, (byte)0x09, (byte)0x10, (byte)0x1f);
        radarview.setLastParam((byte)0x01, (byte)0x09, (byte)0x10, (byte)0x1f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}

