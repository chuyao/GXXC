package com.santu.gxxc.db;

/**
 * Created by ShiChuyao on 2017/9/26.
 */

public class XCDB {

    public static final String NAME = "xcdb";
    public static final int VERSION = 1;

    public static class TableWeather {
        public static final String NAME = "t_weather";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_WEEK_DAY = "week_day";
        public static final String COLUMN_STATUS = "status";
        public static final String CREATE_TABLE = "CREATE TABLE " + NAME
                + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DATE + ""
                + COLUMN_WEEK_DAY + ""
                + COLUMN_STATUS + " )";
    }

    public static class TableNews {
        public static final String NAME = "t_news";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_SUBTITLE = "subtitle";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_ORIGIN = "origin";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_IMAGES = "images";
        public static final String COLUMN_VIDEOS = "videos";
        public static final String COLUMN_TIME = "time";
        public static final String CREATE_TABLE = "CREATE TABLE " + NAME
                + " ("
                + COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_AUTHOR + " "
                + COLUMN_CONTENT + ""
                + COLUMN_IMAGES + ""
                + COLUMN_ORIGIN + ""
                + COLUMN_SUBTITLE + ""
                + COLUMN_TITLE + ""
                + COLUMN_TYPE + ""
                + COLUMN_URL + ""
                + COLUMN_VIDEOS + ""
                + COLUMN_TIME + " )";
    }

}
