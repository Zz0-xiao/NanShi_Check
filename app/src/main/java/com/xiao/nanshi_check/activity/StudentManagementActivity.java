package com.xiao.nanshi_check.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.xiao.nanshi_check.R;

import com.xiao.nanshi_check.adapter.StudentManagementRecylerAdapter;
import com.xiao.nanshi_check.behavior.ScaleDownShowBehavior;
import com.xiao.nanshi_check.model.StudentsBean;

import java.util.ArrayList;
import java.util.List;

public class StudentManagementActivity extends BaseActivity {
    //    private View view;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private List<StudentsBean> studentsList;
    private StudentManagementRecylerAdapter adapter;

    private FloatingActionButton fab;

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

        /*ScaleDownShowBehavior scaleDownShowFab = ScaleDownShowBehavior.from(fab);
        scaleDownShowFab.setOnStateChangedListener(onStateChangedListener);
*/
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(StudentManagementActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initData() {
        studentsList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            StudentsBean bean = new StudentsBean();
            bean.setId("" + i);
            bean.setGrade("机电" + i);
            bean.setName("张三" + i);
            studentsList.add(bean);
        }
        adapter = new StudentManagementRecylerAdapter(StudentManagementActivity.this, studentsList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new StudentManagementRecylerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
//                startActivity(new Intent(getActivity(), TwoActivity.class));
                Toast.makeText(StudentManagementActivity.this, "第" + position + "条被按下了", Toast.LENGTH_SHORT).show();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentManagementActivity.this, "添加学生和导入", Toast.LENGTH_SHORT).show();
            }
        });
    }

/*    private ScaleDownShowBehavior.OnStateChangedListener onStateChangedListener = new ScaleDownShowBehavior.OnStateChangedListener() {
        @Override
        public void onChanged(boolean isShow) {

        }
    };*/

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
}
