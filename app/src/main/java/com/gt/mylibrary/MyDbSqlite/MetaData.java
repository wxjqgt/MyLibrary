package com.gt.mylibrary.MyDbSqlite;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2015/10/28.
 */
public final class MetaData {
    public MetaData(){}
    public static abstract class opinion implements BaseColumns{
        public static final String TABLE_NAME = "opinion";
        public static final String NIKNAME = "name";
        public static final String CREATE_TIME = "create_time";
        public static final String TITLE = "title";
        public static final String DATA_ID = "data_id";
    }
}
