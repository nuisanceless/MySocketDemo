package com.example.wuguohao.mysocketdemo;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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

public class ServerActivity extends AppCompatActivity
{
    TextView mServerIpTv;
    TextView mClientIpTv;
    EditText mPortEt;
    Button mConfirmBtn;
    TextView mContentPanelTv;
    EditText mInputBoxEt;
    Button mSendMsgBtn;

    View mSetLayout;
    View mMsgLayout;

    private int mPort;
    private MySocketServer mSocketServer = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        initView();
    }

    void initView ()
    {
        mServerIpTv = (TextView) findViewById(R.id.server_ip);
        mClientIpTv = (TextView) findViewById(R.id.client_ip);
        mPortEt = (EditText) findViewById(R.id.port);
        mConfirmBtn = (Button) findViewById(R.id.confirm);
        mContentPanelTv = (TextView) findViewById(R.id.content_panel_tv);
        mInputBoxEt = (EditText) findViewById(R.id.input_box_et);
        mSendMsgBtn = (Button) findViewById(R.id.send_message_btn);
        mSetLayout = findViewById(R.id.set_layout);
        mMsgLayout = findViewById(R.id.msg_layout);

        mServerIpTv.setText(Utils.getWifiIp(this));


        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String portStr = mPortEt.getText().toString();
                if (portStr == null || portStr.equals(""))
                {
                    Toast.makeText(ServerActivity.this, "请输入端口号", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPort = Integer.parseInt(portStr);
                mSocketServer = new MySocketServer(mPort);
                mSocketServer.startSocketServer();

                mServerIpTv.setText(Utils.getWifiIp(ServerActivity.this) + ":" + mPort);
                mSetLayout.setVisibility(View.INVISIBLE);
            }
        });


        mSendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgStr = mInputBoxEt.getText().toString();
                if (msgStr == null || msgStr.equals(""))
                {
                    Toast.makeText(ServerActivity.this, "请输入你想发送的内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                mSocketServer.sendMessage(msgStr);
            }
        });


        MySocketServer.mServerHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 11:
                        mMsgLayout.setVisibility(View.VISIBLE);
                        mClientIpTv.setText(msg.obj.toString());
                        break;
                    case 12:
                        mContentPanelTv.setText(msg.obj.toString());
                        break;
                    default:

                        break;
                }
            }
        };
    }





}
