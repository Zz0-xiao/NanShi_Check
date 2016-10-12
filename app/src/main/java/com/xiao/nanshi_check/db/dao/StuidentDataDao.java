package com.xiao.nanshi_check.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.xiao.nanshi_check.db.StudentData;

/**
 * Created by zzzzz on 16/10/12/0012.
 */

public class StuidentDataDao {

    private StudentData helper;

    public StuidentDataDao(Context context) {
        helper = new StudentData(context);
    }

    public boolean add(String  id, String grade,String name) {
        // 获取到可写的数据库
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put("name", grade);
        values.put("grade",name);
        long rowid = db.insert("studentdata", null, values);
        if (rowid == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean delete(String _id) {
        // 获取到可写的数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        int rownumber = db.delete("studentdata", "_id=?",
                new String[]{_id});
        if (rownumber == 0) {
            return false;
        } else {
            return true;
        }
    }


    public boolean deleteAll() {

        SQLiteDatabase db = helper.getWritableDatabase();
        int rownumber = db.delete("studentdata", null, null);
        if (rownumber == 0) {
            return false;
        } else {
            return true;
        }
    }
}
