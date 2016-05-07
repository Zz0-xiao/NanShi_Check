package com.xiao.nanshi_check.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.xiao.nanshi_check.R;

public class SplashActivity extends AppCompatActivity {

    // 本地蓝牙适配器
    private  BluetoothAdapter mBluetoothAdapter = null;
    // 意图请求代码
    private static final int REQUEST_ENABLE_BT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 获取本地蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 如果适配器是null,那么不支持蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "不支持蓝牙", Toast.LENGTH_LONG).show();
            finish();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);

        } else if (mBluetoothAdapter.isEnabled()) {
            // 否则,设置聊天会话
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 当请求启用蓝牙的回报
            if (resultCode == Activity.RESULT_OK) {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                Intent intent = new Intent(this, MainActivity.class);
//                intent.putExtra("MBLUETOOTHADAPTER",mBluetoothAdapter);
//                startActivity(intent);

//                Bundle bundle = new Bundle();
//                bundle.putSerializable("people",(Object)mBluetoothAdapter);
//
//                intent.putExtras(bundle);
//                startActivity(intent);
                finish();
            } else {
                // 用户不启用蓝牙或发生一个错误
                Toast.makeText(this, "蓝牙不启用。离开蓝牙聊天", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }
}
