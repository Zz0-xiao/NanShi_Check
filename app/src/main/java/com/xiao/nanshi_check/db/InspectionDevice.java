package com.xiao.nanshi_check.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zzzzz on 16/9/25/0025.
 */

public class InspectionDevice extends SQLiteOpenHelper {

    public InspectionDevice(Context context) {
        super(context, "inspectiondevice.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table inspectiondevice (_id integer primary key autoincrement,deviceip varchar(20),devicename varchar(20)) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
