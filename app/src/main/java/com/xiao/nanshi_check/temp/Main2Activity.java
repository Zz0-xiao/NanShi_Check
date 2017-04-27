package com.xiao.nanshi_check.temp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiao.nanshi_check.R;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class Main2Activity extends AppCompatActivity {


    private MyHandler myHandler;
    private EditText edtReceive;
    private PrintWriter printWriter;
    EditText edtSend;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        myHandler = new MyHandler();
        edtReceive = (EditText) findViewById(R.id.id_edt_jieshou);
        edtSend = (EditText) this.findViewById(R.id.id_edt_sendArea);
        btnSend = (Button) this.findViewById(R.id.id_btn_sen);
//        btnSend.setOnClickListener(this);
//        btnSend.setOnClickListener(new Button.OnClickListener() {//创建监听
//            public void onClick(View v) {
//                Log.i("", "dianji") ;
//            }
//        });
//        btnSend.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("", "dianji") ;
//                sendData();
//            }
//        });

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(5009);
                    Socket socket = serverSocket.accept();

                    String ip = socket.getInetAddress().getHostAddress();
                    Log.i("" + ip, "链接成功");

                    //获取Socket的输出流，用来向客户端发送数据
//                    PrintStream out = new PrintStream(socket.getOutputStream());
                    OutputStream outputStream = socket.getOutputStream();
                    printWriter = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(outputStream,
                                    Charset.forName("gb2312"))));

                    boolean flag = true;
                    while (flag) {
                        //获取Socket的输入流，用来接收从客户端发送过来的数据
//                    BufferedReader buf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                        InputStream in = socket.getInputStream();
//                        byte[] buf = new byte[1024];
//                        int len = in.read(buf);
//                        String str = new String(buf, 0, len);
                        String str = new Utility().readFromInputStream(socket.getInputStream());
                        //接收从客户端发送过来的数据
                        if (str == null || "".equals(str)) {
                            flag = false;
                        } else {
                            if ("bye".equals(str)) {
                                flag = false;
                            } else {
                                //将接收到的字符串前面加上echo，发送到对应的客户端
                                System.out.println("echo:" + str);

                                Message msg = new Message();
                                msg.what = 1;
                                Bundle data = new Bundle();
                                data.putString("msg", str);
                                msg.setData(data);
                                myHandler.sendMessage(msg);
                            }
                        }
                    }
//                    out.close();/////////
                    socket.close();
//                    serverSocket.close();//关闭服务器，理论上是不用关的

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void send(View view) {
        Log.i("" ,"链接成功");
        sendData();
    }

    /**
     * 发送数据线程.
     */
    private void sendData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String context = edtSend.getText().toString();

                    if (printWriter == null || context == null) {
                        Toast.makeText(Main2Activity.this, "连接失败!", Toast.LENGTH_SHORT).show();
//                        if (printWriter == null) {
//                            Toast.makeText(MainActivity.this, "连接失败!", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        if (context == null) {
//                            Toast.makeText(MainActivity.this, "连接失败!", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
                    }
                    printWriter.print(context);
                    printWriter.flush();
//            printWriter.close();
                    Log.i("", "--->> client send data!");
                } catch (Exception e) {
                    Log.e("", "--->> send failure!" + e.toString());
                }
            }
        }).start();
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

//            receiverData(msg.what);
            if (msg.what == 1) {
                String result = msg.getData().get("msg").toString();
                edtReceive.append(result);
            }
        }
    }
}
