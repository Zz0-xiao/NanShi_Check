package com.xiao.nanshi_check.activity;

import android.view.View;
import android.widget.EditText;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.db.dao.StuidentDataDao;

public class AddStudentActivity extends BaseActivity {


    private EditText addId;
    private EditText addgraDe;
    private EditText addName;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_student;
    }

    @Override
    protected void initView() {
        addId = (EditText) findViewById(R.id.ed_add_id);
        addgraDe = (EditText) findViewById(R.id.ed_add_grade);
        addName = (EditText) findViewById(R.id.ed_add_name);
    }

    public void onClickAdd(View v) {

        StuidentDataDao dao = new StuidentDataDao(AddStudentActivity.this);
        dao.add(addId.getText() + "", addgraDe.getText() + "", addName.getText() + "");
    }

    public void onClickBatchImport(View v) {
        StuidentDataDao dao = new StuidentDataDao(AddStudentActivity.this);
        for (int i = 0; i < 20; i++) {
            dao.add("0091" + i, "张三" + i, "机电" + i);
        }
    }
}
