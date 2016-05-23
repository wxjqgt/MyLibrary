package com.gt.mylibrary.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gt.mylibrary.R;
import com.gt.mylibrary.base.BaseActivity;
import com.gt.mylibrary.beans.EventType;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_event_bus1)
public class Activity_EventBus1 extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.editext)
    private EditText editText;

    @ViewInject(R.id.back)
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String text = editText.getText().toString();
        EventBus.getDefault().post(new EventType(text));
        finish();
    }

}
