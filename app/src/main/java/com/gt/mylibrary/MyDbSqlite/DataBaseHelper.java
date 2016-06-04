package com.gt.mylibrary.MyDbSqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2015/10/28.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "opinion.db";
    private static final int VERSION = 1;

    private static final String CREAETE_TABLE_OPINION =
            "create table opinion(_id integer primary key " +
            "autoincrement,title text,name text,create_time text,data_id text)";
    private static final String DROP_TABLE_OPINION = "drop table if exists opinion";

    public DataBaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    public DataBaseHelper(Context context,String tableName){
        super(context,tableName,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREAETE_TABLE_OPINION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_OPINION);
        sqLiteDatabase.execSQL(CREAETE_TABLE_OPINION);
    }
}
