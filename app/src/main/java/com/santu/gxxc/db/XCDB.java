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

}
