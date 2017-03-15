package com.example.wuguohao.mysocketdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wuguohao on 17/3/14.
 */

public class ClientActivity extends AppCompatActivity
{
    EditText mServerIpEt;
    EditText mServerPortEt;
    Button mConnectBtn;
    TextView mContentPanelTv;
    EditText mInputBoxEt;
    Button mSendMsgBtn;

    View mSetLayout;
    View mMsgLayout;

    MySocketClient mSocketClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        initView();
    }

    void initView()
    {
        mServerIpEt = (EditText) findViewById(R.id.server_ip);
        mServerPortEt = (EditText) findViewById(R.id.server_port);
        mConnectBtn = (Button) findViewById(R.id.connect);
        mContentPanelTv = (TextView) findViewById(R.id.content_panel_tv);
        mInputBoxEt = (EditText) findViewById(R.id.input_box_et);
        mSendMsgBtn = (Button) findViewById(R.id.send_msg_btn);
        mSetLayout = findViewById(R.id.set_layout);
        mMsgLayout = findViewById(R.id.msg_layout);

        mSocketClient = new MySocketClient();

        mConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipString = mServerIpEt.getText().toString();
                String portString = mServerPortEt.getText().toString();
                if (ipString == null || ipString.equals("")) {
                    Toast.makeText(ClientActivity.this, "请输入服务器IP地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (portString == null || portString.equals("")) {
                    Toast.makeText(ClientActivity.this, "请输入服务器端口号", Toast.LENGTH_SHORT).show();
                    return;
                }

                mSocketClient.setClientValue(ClientActivity.this, ipString, Integer.parseInt(portString));
                mSocketClient.connectServer();
            }
        });

        mSendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = mInputBoxEt.getText().toString();
                if (msg == null || msg.equals(""))
                {
                    Toast.makeText(ClientActivity.this, "请输入你要发送的内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                mSocketClient.sendMessage(msg);
            }
        });

        MySocketClient.mHandler=new Handler(  ){
            @Override
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case 13:
                        mContentPanelTv.setText ( msg.obj.toString ());
                        break;
                    case 14:
                        mSetLayout.setVisibility(View.INVISIBLE);
                        mMsgLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(ClientActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                        break;
                    default:

                        break;
                }
            }
        };

    }
}
