package com.xiao.nanshi_check.fragment;


import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.adapter.EquipmentRecylerAdapter;
import com.xiao.nanshi_check.db.InspectionDevice;
import com.xiao.nanshi_check.db.dao.InspectionDeviceDao;
import com.xiao.nanshi_check.model.EquipmentBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentFragment extends Fragment {
    public static final int JUDGE_QUERY_DELETE = -1;
    //设备管理
    private View view;
    private RecyclerView recyclerView;
    private List<EquipmentBean> beanList;
    private EquipmentRecylerAdapter adapter;

    private String deleteIp = "";
//    Context context;
//    private InspectionDeviceDao dao;

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

    @Override
    public void onStart() {
        super.onStart();
        beanList.clear();
        dataDispose(JUDGE_QUERY_DELETE);
        adapter.notifyDataSetChanged();//更新?
    }


    private void initData() {
        beanList = new ArrayList<EquipmentBean>();
        dataDispose(JUDGE_QUERY_DELETE);
        adapter = new EquipmentRecylerAdapter(getActivity(), beanList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new EquipmentRecylerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
                Toast.makeText(getContext(), "第" + position + "点击", Toast.LENGTH_SHORT).show();
            }
        });

        adapter.OnItemLongClickListener(new EquipmentRecylerAdapter.OnItemLongClickListener() {//长按事件
            @Override
            public void OnItemLongClick(final int position, Object object) {
//                Toast.makeText(getContext(), "第" + position + "长按", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //设置图标
//                builder.setIcon(android.R.drawable.alert_dark_frame);
                //设置标题
                builder.setTitle("删除设备");
                //设置文本
                builder.setMessage("确定删除该设备");

                //设置确定按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataDispose(position);
                        adapter.notifyDataSetChanged();//更新?
                    }
                });

                //设置取消按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getContext(), "8888", Toast.LENGTH_LONG).show();
                    }
                });
                //使用创建器,生成一个对话框对象
                builder.create().show();
            }
        });
    }

    private void dataDispose(int position) {

        if (position == JUDGE_QUERY_DELETE) {
            deleteIp = "";
        } else {
            deleteIp = beanList.get(position).getEquipmentIp().toString();
        }

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

            if (position == JUDGE_QUERY_DELETE) {
                beanList.add(e);
            } else if (deleteIp.equals(equipmentIp)) {
                InspectionDeviceDao dao = new InspectionDeviceDao(getContext());
                dao.delete(equipmentIp);
//                Log.i("", "删除前:" + beanList.size() + ":" + beanList);
                Iterator<EquipmentBean> sListIterator = beanList.iterator();
                /*arraylist 删除东西**********************************************************************/
                while (sListIterator.hasNext()) {
                    EquipmentBean b = sListIterator.next();
                    String c = b.getEquipmentIp().toString();
                    if (c.equals(deleteIp)) {
                        sListIterator.remove();
                    }
                }
//                Log.i("", "删除后集合的长度为:" + beanList.size() + ":" + beanList);
            }
        }
    }

    public void add1() {
//        InspectionDeviceDao dao = new InspectionDeviceDao(getActivity());
//        for (int i = 0; i < 2; i++) {
//            dao.add("192.168.0." + i, "x62机床" + i);
//        }
//        adapter.notifyDataSetChanged();//更新?
//        Toast.makeText(getActivity(), "设备管理 ", Toast.LENGTH_SHORT).show();
//        Log.i("haha", "haha");
//        InspectionDeviceDao dao =  new InspectionDeviceDao(context);
//        boolean result = dao.add("192.168.0." , "x62机床" );
//        for (int i = 0; i < 10; i++) {
//            int number = i;
//            dao.add("192.168.0." + number, "x62机床" + number);
//        }
//        adapter.notifyDataSetChanged();//更新?
    }
}
