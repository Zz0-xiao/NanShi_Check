package com.xiao.nanshi_check.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.db.dao.InspectionDeviceDao;

public class AddEquipmentActivity extends BaseActivity {

    private EditText addIp;
    private EditText addName;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_equipment;
    }

    @Override
    protected void initView() {
        addIp = (EditText) findViewById(R.id.ed_add_ip);
        addName = (EditText) findViewById(R.id.ed_add_name);
//        Button add = (Button) findViewById(R.id.bt_add);
        addIp.setText("192.168.0.5");
        addName.setText("x62机床");
    }

        public void onClickAdd(View v) {

        InspectionDeviceDao dao = new InspectionDeviceDao(AddEquipmentActivity.this);
        dao.add(addIp.getText() + "", addName.getText() + "");
    }
}
