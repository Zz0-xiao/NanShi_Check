package com.xiao.nanshi_check.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.adapter.StudentManagementRecylerAdapter;
import com.xiao.nanshi_check.db.StudentData;
import com.xiao.nanshi_check.db.dao.StuidentDataDao;
import com.xiao.nanshi_check.model.StudentsBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class StudentManagementActivity extends BaseActivity implements ActionMode.Callback {

    //    private View view;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private List<StudentsBean> studentsList;
    private StudentManagementRecylerAdapter adapter;

    private FloatingActionButton fab;
    private String id = "";

    public static final int JUDGE_QUERY_DELETE = -1;

    private ActionMode actionMode;//多选删除用
    //    public static Set<Integer> positionSet = new HashSet<>();
    public static Set<Integer> positionSet = new HashSet<>();//记录要删除的选项
    ;//保存RecyclerView的所点击的item的位置

    @Override
    protected int getContentView() {
        return R.layout.activity_student_management;
    }


    @Override
    protected void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回按钮
        toolbar.setTitle("学生管理");

        initRecyclerView();
        initData();

    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(StudentManagementActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initData() {
        studentsList = new ArrayList<StudentsBean>();

        //保存RecyclerView的所点击的item的位置
//        positionSet = new HashSet<>();

//        for (int i = 0; i < 50; i++) {
//            StudentsBean bean = new StudentsBean();
//            bean.setId("" + i);
//            bean.setGrade("机电" + i);
//            bean.setName("张三" + i);
//            studentsList.add(bean);
//        }
        dataDispose(JUDGE_QUERY_DELETE);

        adapter = new StudentManagementRecylerAdapter(StudentManagementActivity.this, studentsList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new StudentManagementRecylerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (actionMode != null) {
                    // 如果当前处于多选状态，则进入多选状态的逻辑
                    // 维护当前已选的position
                    addOrRemove(position);
                } else {
                    // 如果不是多选状态，则进入点击事件的业务逻辑
                    // TODO something
                    Toast.makeText(StudentManagementActivity.this, "第" + position + "条", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (actionMode == null) {
                    actionMode = startSupportActionMode(StudentManagementActivity.this);
                }
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(StudentManagementActivity.this, "添加学生和导入", Toast.LENGTH_SHORT).show();
//                StuidentDataDao dao = new StuidentDataDao(StudentManagementActivity.this);
//                for (int i = 0; i < 20; i++) {
//                    dao.add("0091" + i, "张三" + i, "机电" + i);
//                }
                startActivity(new Intent(StudentManagementActivity.this, AddStudentActivity.class));
                studentsList.clear();
                dataDispose(JUDGE_QUERY_DELETE);
                adapter.notifyDataSetChanged();//更新?
            }
        });
    }

    private void addOrRemove(int position) {
        if (positionSet.contains(position)) {
            // 如果包含，则撤销选择
            positionSet.remove(position);
        } else {
            // 如果不包含，则添加
            positionSet.add(position);
        }
        if (positionSet.size() == 0) {
            // 如果没有选中任何的item，则退出多选模式
            actionMode.finish();
        } else {
            // 设置ActionMode标题
            actionMode.setTitle(positionSet.size() + " 已选择");
            // 更新列表界面，否则无法显示已选的item
            adapter.notifyDataSetChanged();
        }
    }


    private void dataDispose(int position) {


        if (position == JUDGE_QUERY_DELETE) {
            id = "";
        } else {
            id = studentsList.get(position).getId().toString();
        }


        //创建一个帮助类对象
        StudentData StudentData = new StudentData(StudentManagementActivity.this);
        //调用getReadableDatabase方法,来初始化数据库的创建
        SQLiteDatabase db = StudentData.getReadableDatabase();
        Cursor cursor = db.query("studentdata", null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String _id = cursor.getString(0);
            String name = cursor.getString(1);
            String grade = cursor.getString(2);
            StudentsBean sd = new StudentsBean();
            sd.setId(_id);
            sd.setName(name);
            sd.setGrade(grade);

            if (position == JUDGE_QUERY_DELETE) {
                studentsList.add(sd);
            } else if (id.equals(_id)) {
                StuidentDataDao dao = new StuidentDataDao(StudentManagementActivity.this);
                dao.delete(id);
//                Log.i("", "删除前:" + beanList.size() + ":" + beanList);
                Iterator<StudentsBean> sListIterator = studentsList.iterator();
                /*arraylist 删除东西**********************************************************************/
                while (sListIterator.hasNext()) {
                    StudentsBean b = sListIterator.next();
                    String c = b.getId().toString();
                    if (c.equals(id)) {
                        sListIterator.remove();
                    }
                }
//                Log.i("", "删除后集合的长度为:" + beanList.size() + ":" + beanList);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_edit) {
//            Toast.makeText(MainActivity.this, "查找", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        if (actionMode == null) {
            actionMode = mode;
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_delete, menu);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                // 删除已选
                ArrayList<StudentsBean> deleteList = new ArrayList<>();
                for (int position : positionSet) {
                    deleteList.add(adapter.getItem(position));
                }
//                for (int i = 0; i < positionSet.size(); i++) {
//                    valueSet.add(adapter.getItem(i));
////                    int position = adapter.getItemCount();
////                    Log.i("hahahah", position + "");
//                }

//                for (StudentsBean val : delete) {
//                    adapter.remove(val);
//                }
                for (int i = 0; i < deleteList.size(); i++) {
                    StuidentDataDao dao = new StuidentDataDao(StudentManagementActivity.this);
                    dao.delete(deleteList.get(i).getId().toString());
                    Log.i("hahahah", deleteList.get(i).getId().toString() + "");
                    adapter.remove(deleteList.get(i));
                }
                mode.finish();
                return true;
            default:
                return false;
        }
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        positionSet.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        initData();
        super.onResume();
    }
}




    /*        adapter.setOnItemClickListener(new StudentManagementRecylerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
//                startActivity(new Intent(getActivity(), TwoActivity.class));
                Toast.makeText(StudentManagementActivity.this, "第" + position + "条被按下了", Toast.LENGTH_SHORT).show();
            }
        });

        adapter.OnItemLongClickListener(new StudentManagementRecylerAdapter.OnItemLongClickListener() {//长按事件
            @Override
            public void OnItemLongClick(final int position, Object object) {
//                Toast.makeText(getContext(), "第" + position + "长按", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(StudentManagementActivity.this);
                //设置图标
//                builder.setIcon(android.R.drawable.alert_dark_frame);
                //设置标题
                builder.setTitle("删除学生");
                //设置文本
                builder.setMessage("确定删除该学生");

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
                AlertDialog ad = builder.create();
                ad.show();
            }
        });*/