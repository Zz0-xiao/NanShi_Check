package com.xiao.nanshi_check.fragment;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.activity.DeviceListActivity;
import com.xiao.nanshi_check.service.BluetoothService;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentFragment extends Fragment {
    private View view;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText("设备");
//        return textView;
        view = inflater.inflate(R.layout.activity_bluetooth, container, false);
        return view;
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // 数组初始化适配器对话线程
        mConversationArrayAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.message);
        mConversationView = (ListView) view.findViewById(R.id.in);
        mConversationView.setAdapter(mConversationArrayAdapter);

        // 初始化组合字段返回键的一个侦听器
        mOutEditText = (EditText) view.findViewById(R.id.edit_text_out);
        // 初始化发送按钮单击事件的监听器
        mSendButton = (Button) view.findViewById(R.id.button_send);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 发送消息使用的内容编辑文本小部件
                TextView edit_text_out = (TextView) view.findViewById(R.id.edit_text_out);
                String message = edit_text_out.getText().toString();
                sendMessage(message);
            }
        });

        // 初始化蓝牙聊天服务进行蓝牙连接
        mChatService = new BluetoothService(this.getActivity(), mHandler);

        // 初始化缓冲传出消息
        mOutStringBuffer = new StringBuffer("");
    }


    private void sendMessage(String message) {
        // 检查我们之前任何连接
        if (mChatService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(this.getActivity(), R.string.not_connected, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getActivity(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    Toast.makeText(this.getActivity(), R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
//                    finish();
                }
        }
    }
}
