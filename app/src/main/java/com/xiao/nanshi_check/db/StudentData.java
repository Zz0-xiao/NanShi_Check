package com.xiao.nanshi_check.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zzzzz on 16/9/25/0025.
 */

public class StudentData extends SQLiteOpenHelper {

    public StudentData(Context context) {
        super(context, "studentdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table studentdata (_id integer primary key ,name varchar(20),grade varchar(20),testScore varchar(20),testTime varchar(20),testDate varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
