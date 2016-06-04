package com.gt.mylibrary.MyDbSqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2015/10/28.
 */
public class DataBaseAdapter {

    private DataBaseHelper dataBaseHelper;

    public DataBaseAdapter(Context context){
        dataBaseHelper = new DataBaseHelper(context);
    }

    public DataBaseAdapter(Context context,String tableName){
        dataBaseHelper = new DataBaseHelper(context,tableName);
    }

    public long add(News news){
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MetaData.opinion.TITLE,news.getTitle());
        values.put(MetaData.opinion.NIKNAME,news.getName());
        values.put(MetaData.opinion.CREATE_TIME,news.getCreate_time());
        values.put(MetaData.opinion.DATA_ID,news.getData_id());
        long id = sqLiteDatabase.insert(MetaData.opinion.TABLE_NAME,null,values);
        sqLiteDatabase.close();
        return id;
    }

    public int delete(int id){
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        String whereClause = MetaData.opinion._ID + "=?";
        String[] whereArgs = {String.valueOf(id)};
        int num = sqLiteDatabase.delete(MetaData.opinion.TABLE_NAME, whereClause, whereArgs);
        sqLiteDatabase.close();
        return num;
    }

    public News findById(int id){
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        String[] columns = {
                MetaData.opinion._ID,
                MetaData.opinion.TITLE,
                MetaData.opinion.NIKNAME,
                MetaData.opinion.CREATE_TIME,
                MetaData.opinion.DATA_ID
        };
        Cursor cursor = sqLiteDatabase.query(
                true, MetaData.opinion.TABLE_NAME, columns,
                MetaData.opinion._ID, new String[]{String.valueOf(id)},
                null, null, MetaData.opinion._ID, null);
        News news = null;
        String title = cursor.getString(
                cursor.getColumnIndexOrThrow(MetaData.opinion.TITLE));
        String nikname = cursor.getString(
                cursor.getColumnIndexOrThrow(MetaData.opinion.NIKNAME));
        String createtime = cursor.getString(
                cursor.getColumnIndexOrThrow(MetaData.opinion.CREATE_TIME));
        String dataid = cursor.getString(
                cursor.getColumnIndexOrThrow(MetaData.opinion.DATA_ID));
        while (cursor.moveToNext()){
            news = new News();
            news.setTitle(title);
            news.setName(nikname);
            news.setCreate_time(createtime);
            news.setData_id(dataid);
            news.setId(id);
        }
        cursor.close();
        sqLiteDatabase.close();
        return news;
    }

    public ArrayList<News> findall(){
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        String[] columns = {
                MetaData.opinion._ID,
                MetaData.opinion.TITLE,
                MetaData.opinion.NIKNAME,
                MetaData.opinion.CREATE_TIME,
                MetaData.opinion.DATA_ID
        };
        Cursor cursor = sqLiteDatabase.query(
                true, MetaData.opinion.TABLE_NAME, columns,
                null, null, null, null, null, null);
        ArrayList<News> list = new ArrayList<>();
        News news = null;
        String title = cursor.getString(
                cursor.getColumnIndexOrThrow(MetaData.opinion.TITLE));
        String nikname = cursor.getString(
                cursor.getColumnIndexOrThrow(MetaData.opinion.NIKNAME));
        String createtime = cursor.getString(
                cursor.getColumnIndexOrThrow(MetaData.opinion.CREATE_TIME));
        String dataid = cursor.getString(
                cursor.getColumnIndexOrThrow(MetaData.opinion.DATA_ID));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(MetaData.opinion._ID));
        while (cursor.moveToNext()){
            news = new News();
            news.setTitle(title);
            news.setName(nikname);
            news.setCreate_time(createtime);
            news.setData_id(dataid);
            news.setId(id);
            list.add(news);
        }
        cursor.close();
        sqLiteDatabase.close();

        return list;
    }

}
