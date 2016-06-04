package com.gt.mylibrary.MyDbSqlite;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/10/27.
 */
public class News implements Parcelable{

    private String title;
    private String source;
    private String name;
    private String create_time;
    private String data_id;
    private int id;
    private String wap_thumb;

    public News() {}

    public News(String data_id) {
        this.data_id = data_id;
    }

    public News(String title, String name, String create_time, String data_id, int id) {
        this.title = title;
        this.name = name;
        this.create_time = create_time;
        this.data_id = data_id;
        this.id = id;
    }

    public News(String title, String source, String name, String create_time, String data_id, int id, String wap_thumb) {
        this.title = title;
        this.source = source;
        this.name = name;
        this.create_time = create_time;
        this.data_id = data_id;
        this.id = id;
        this.wap_thumb = wap_thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWap_thumb() {
        return wap_thumb;
    }

    public void setWap_thumb(String wap_thumb) {
        this.wap_thumb = wap_thumb;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", name='" + name + '\'' +
                ", create_time='" + create_time + '\'' +
                ", data_id='" + data_id + '\'' +
                ", id=" + id +
                ", wap_thumb='" + wap_thumb + '\'' +
                '}';
    }

    protected News(Parcel in) {
        title = in.readString();
        source = in.readString();
        name = in.readString();
        create_time = in.readString();
        data_id = in.readString();
        wap_thumb = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(source);
        parcel.writeString(name);
        parcel.writeString(create_time);
        parcel.writeString(data_id);
        parcel.writeString(wap_thumb);
    }
}
