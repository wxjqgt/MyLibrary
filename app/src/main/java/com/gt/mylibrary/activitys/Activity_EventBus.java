package com.gt.mylibrary.activitys;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.gt.mylibrary.R;
import com.gt.mylibrary.base.BaseActivity;
import com.gt.mylibrary.beans.Country;
import com.gt.mylibrary.beans.EventType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import io.realm.Realm;
import io.realm.RealmResults;

@ContentView(R.layout.activity_event_bus)
public class Activity_EventBus extends BaseActivity implements View.OnClickListener {

    private EventBus eventBus;

    @ViewInject(R.id.go)
    private Button go;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus = EventBus.getDefault();
        eventBus.register(this);
        go.setOnClickListener(this);

        /*realm = Realm.getInstance(this);
        realm.beginTransaction();
        // Create an object
        Country country1 = realm.createObject(Country.class);
        // Set its fields
        country1.setName("Norway");
        country1.setPopulation(5165800);
        country1.setCode("NO");

        realm.commitTransaction();*/
    }

    @Subscribe
    public void OnEventMainThread(EventType eventType){
        Toast.makeText(this,eventType.getMsg(),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @Override
    public void onClick(View v) {
        PopupWindow pw = new PopupWindow(View.inflate(this,R.layout.activity_event_bus1,null), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pw.setAnimationStyle(R.style.AppTheme);
        pw.setBackgroundDrawable(getResources().getDrawable(R.mipmap.login_bg_on));
        pw.setFocusable(true);
        pw.setInputMethodMode(PopupWindow.INPUT_METHOD_FROM_FOCUSABLE);
        pw.setTouchable(true);
        pw.setOutsideTouchable(true);
        pw.showAtLocation(go, Gravity.BOTTOM,0,0);

//        RealmResults<Country> countries = realm.where(Country.class).findAll();
//        System.out.println("all = " + countries);
    }
}
