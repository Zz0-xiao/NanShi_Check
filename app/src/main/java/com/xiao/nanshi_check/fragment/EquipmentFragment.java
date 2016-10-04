package com.xiao.nanshi_check.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.activity.StudentManagementActivity;
import com.xiao.nanshi_check.adapter.EquipmentRecylerAdapter;
import com.xiao.nanshi_check.db.InspectionDevice;
import com.xiao.nanshi_check.db.dao.InspectionDeviceDao;
import com.xiao.nanshi_check.model.EquipmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentFragment extends Fragment {
    //设备管理
    private View view;
    //    private String content;
    private RecyclerView recyclerView;

    private List<EquipmentBean> beanList;
    private EquipmentRecylerAdapter adapter;


    public EquipmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_equipment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        content = getArguments().getString("content");
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        initData();
    }

    private void initData() {

        beanList = new ArrayList<EquipmentBean>();

        //创建一个帮助类对象
        InspectionDevice inspectionDevice = new InspectionDevice(getContext());
        //调用getReadableDatabase方法,来初始化数据库的创建
        SQLiteDatabase db = inspectionDevice.getReadableDatabase();
        //光标什么的
        Cursor cursor = db.query("inspectiondevice", null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String equipmentIp = cursor.getString(1);
            String equipmentName = cursor.getString(2);

            EquipmentBean e = new EquipmentBean(equipmentIp, equipmentName);
            beanList.add(e);
        }

        adapter = new EquipmentRecylerAdapter(getActivity(), beanList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new EquipmentRecylerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
                Toast.makeText(getContext(), "第" + position + "点击", Toast.LENGTH_SHORT).show();
            }
        });


        adapter.OnItemLongClickListener(new EquipmentRecylerAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(int position, Object object) {
//                Toast.makeText(getContext(), "第" + position + "长按", Toast.LENGTH_SHORT).show();
//                InspectionDeviceDao dao = new InspectionDeviceDao(getContext());
//                dao.delete("192.168.0.9");
                /********************************************* *****************/

                String deleteIp = beanList.get(position).getEquipmentIp().toString();

                Toast.makeText(getContext(), "第" + deleteIp + "长按", Toast.LENGTH_SHORT).show();
                //创建一个帮助类对象
                InspectionDevice inspectionDevice = new InspectionDevice(getContext());
                //调用getReadableDatabase方法,来初始化数据库的创建
                SQLiteDatabase db = inspectionDevice.getReadableDatabase();
                //光标什么的
                Cursor cursor = db.query("inspectiondevice", null, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    String equipmentIp = cursor.getString(1);
                    String equipmentName = cursor.getString(2);

                    EquipmentBean e = new EquipmentBean(equipmentIp, equipmentName);
                    beanList.add(e);

                    if (deleteIp.equals(equipmentIp)) {
//                        deleteData(cursor.getInt(0));//删除匹配的数据库里记录，cursor.getInt(0)为得
                        InspectionDeviceDao dao = new InspectionDeviceDao(getContext());
                        dao.delete(equipmentIp);
                    }
                }


                adapter.notifyDataSetChanged();//更新?
            }
        });
       /* adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
//                startActivity(new Intent(getActivity(), TwoActivity.class));
            }
        });*/
    }

}


  /*  String deleteText = mlistItem.get(index).get("mtext").toString();
    String deleteTime = mlistItem.get(index).get("mtime").toString();
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    // 以下是把所有的表都存进来，然后_id按顺序排列，方便读取数据
    Cursor cursor = db
            .query("user", null, null, null, null, null, null);
while (cursor.moveToNext()) {
        String mtext = cursor.getString(cursor.getColumnIndex("mtext"));//得到数据库中的数据
        String mtime = cursor.getString(cursor.getColumnIndex("mtime"));
        currentTime = new Date();

        // 通过主键值来判断点中的listview中的Item所对应的数据库表中的_id
        if (deleteText.equals(mtext) && deleteTime.equals(Cursortime)) {
        deleteData(cursor.getInt(0));//删除匹配的数据库里记录，cursor.getInt(0)为得到该cursor对用的第一例，及_id
        break;
        }
        db.close();


        public void deleteData(int index) {
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    // 删除表的所有数据
    // db.delete("users",null,null);
    // 从表中删除指定的一条数据
    db.execSQL("DELETE FROM " + "user" + " WHERE _id="
            + Integer.toString(index));
    db.close();
}

        */