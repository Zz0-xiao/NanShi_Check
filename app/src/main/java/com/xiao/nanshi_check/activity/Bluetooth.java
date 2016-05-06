package com.xiao.nanshi_check.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.service.BluetoothService;

public class Bluetooth extends AppCompatActivity {
    // 调试以排除故障
    private static final String TAG = "Bluetooth";
    private static final boolean D = true;

    // 消息类型从蓝牙发送聊天服务处理程序
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // 键名收到蓝牙聊天服务处理程序
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // 意图请求代码
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout Views
//    private TextView mTitle;
    private ListView mConversationView;
    private EditText mOutEditText;
    private Button mSendButton;

    // 连接设备的名称
    private String mConnectedDeviceName = null;
    // 数组适配器对话线程
    private ArrayAdapter<String> mConversationArrayAdapter;
    // 字符串缓冲区传出消息
    private StringBuffer mOutStringBuffer;
    // 本地蓝牙适配器
    private BluetoothAdapter mBluetoothAdapter = null;
    // 成员对象聊天服务
    private BluetoothService mChatService = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (D) Log.e(TAG, "+++ ON CREATE +++");
        setContentView(R.layout.activity_bluetooth);
        // 获取本地蓝牙适配器

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 如果适配器是null,那么不支持蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "不支持蓝牙", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    public void onclick(View v) {
//        Toast.makeText(this, "链接", Toast.LENGTH_LONG).show();

        // 启动DeviceListActivity看到设备和做扫描
        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (D) Log.e(TAG, "++ ON START ++");

        // 如果不是BT,请求它被启用。
        // setupChat() will then be called during onActivityResult
        // setupChat在onActivityResult()将被调用 上面的翻译
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // 否则,设置聊天会话
        } else {
            if (mChatService == null) setupChat();
        }
    }


    @Override
    protected synchronized void onResume() {
        super.onResume();
        if (D) Log.e(TAG, "+ ON RESUME +");

        // 执行这种检查在onResume()覆盖的情况下BT
        // 未启用期间onStart(),所以我们停下来启用它……
        // onResume ACTION_REQUEST_ENABLE活动时()将返回。
        if (mChatService != null) {
            // 只有状态是STATE_NONE,我们已经知道,我们还没有开始
            if (mChatService.getState() == BluetoothService.STATE_NONE) {
                // 启动蓝牙聊天服务
                mChatService.start();
            }
        }
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // 数组初始化适配器对话线程
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        mConversationView = (ListView) findViewById(R.id.in);
        mConversationView.setAdapter(mConversationArrayAdapter);

        // 初始化组合字段返回键的一个侦听器
        mOutEditText = (EditText) findViewById(R.id.edit_text_out);
        mOutEditText.setOnEditorActionListener(mWriteListener);

        // 初始化发送按钮单击事件的监听器
        mSendButton = (Button) findViewById(R.id.button_send);
        mSendButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // 发送消息使用的内容编辑文本小部件
                TextView view = (TextView) findViewById(R.id.edit_text_out);
                String message = view.getText().toString();
                sendMessage(message);
            }
        });

        // 初始化蓝牙聊天服务进行蓝牙连接
        mChatService = new BluetoothService(this, mHandler);

        // 初始化缓冲传出消息
        mOutStringBuffer = new StringBuffer("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 停止蓝牙聊天服务
        if (mChatService != null) mChatService.stop();
        if (D) Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if (D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }// 确保该设备容易被别人

    private void sendMessage(String message) {
        // 检查我们之前任何连接
        if (mChatService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // 检查是否有东西送
        if (message.length() > 0) {
            // 得到消息的字节,告诉BluetoothChatService写
            byte[] send = message.getBytes();
            mChatService.write(send);

            // 重置了字符串缓冲区为零并清除编辑文本字段
            mOutStringBuffer.setLength(0);
            mOutEditText.setText(mOutStringBuffer);
        }
    }

    //    // EditText部件的动作侦听器,侦听返回键
    private TextView.OnEditorActionListener mWriteListener =
            new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                    // 如果操作是一个激励的事件返回键,发送消息
                    if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                        String message = view.getText().toString();
                        sendMessage(message);
                    }
                    if (D) Log.i(TAG, "END onEditorAction");
                    return true;
                }
            };
    // 处理程序从BluetoothChatService回来的信息
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if (D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
//                    mTitle.setText(R.string.title_connected_to);
//                    mTitle.append(mConnectedDeviceName);
                            mConversationArrayAdapter.clear();
                            break;
                        case BluetoothService.STATE_CONNECTING:
//                    mTitle.setText(R.string.title_connecting);
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
//                    mTitle.setText(R.string.title_not_connected);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // 构造一个字符串缓冲区
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // 构造一个字符串有效字节的缓冲区
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    break;
                case MESSAGE_DEVICE_NAME:
                    // 保存连接设备的名字
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // 当DeviceListActivity返回设备连接
                if (resultCode == Activity.RESULT_OK) {
                    // 设备的MAC地址
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // 得到BLuetoothDevice对象
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    //试图连接到设备
                    mChatService.connect(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                // 当请求启用蓝牙的回报
                if (resultCode == Activity.RESULT_OK) {
                    // 蓝牙现在启用,所以建立一个聊天会话
                    setupChat();
                } else {
                    // 用户不启用蓝牙或发生一个错误
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }
}
