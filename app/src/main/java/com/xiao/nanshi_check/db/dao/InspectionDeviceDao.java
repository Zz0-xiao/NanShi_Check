package com.xiao.nanshi_check.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.xiao.nanshi_check.db.InspectionDevice;
import com.xiao.nanshi_check.model.EquipmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzzzz on 16/9/25/0025.
 */

public class InspectionDeviceDao {

    private InspectionDevice helper;

    public InspectionDeviceDao(Context context) {
        helper = new InspectionDevice(context);
    }

    public boolean add(String deviceip, String devicename) {
        // 获取到可写的数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("deviceip", deviceip);
        values.put("devicename", devicename);
        long rowid = db.insert("inspectiondevice", null, values);
        if (rowid == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean delete(String deviceip) {
        // 获取到可写的数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        int rownumber = db.delete("inspectiondevice", "deviceip=?",
                new String[]{deviceip});
        if (rownumber == 0) {
            return false;
        } else {
            return true;
        }
    }

//    public boolean delete2(String deviceip) {
//        // 获取到可写的数据库
//        SQLiteDatabase db = helper.getWritableDatabase();
//        int rownumber = db.delete("inspectiondevice", "_id=?",
//                new String[]{deviceip});
//        if (rownumber == 0) {
//            return false;
//        } else {
//            return true;
//        }
//    }

    public boolean deleteAll() {

        SQLiteDatabase db = helper.getWritableDatabase();
        int rownumber = db.delete("inspectiondevice", null, null);
        if (rownumber == 0) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 查询全部的黑名单号码
     */
    public List<EquipmentBean> findAll() {
        // 得到可读的数据库
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("inspectiondevice", new String[]{"deviceip", "devicename"}, null, null, null, null, null);
        List<EquipmentBean> EquipmentBeans = new ArrayList<EquipmentBean>();
        while (cursor.moveToNext()) {
            EquipmentBean eb = new EquipmentBean();
            String deviceip = cursor.getString(0);
            String devicename = cursor.getString(1);
            eb.setEquipmentIp(deviceip);
            eb.setEquipmentName(devicename);
            EquipmentBeans.add(eb);
        }
        cursor.close();
        db.close();
        SystemClock.sleep(3000);
        return EquipmentBeans;
    }

}
