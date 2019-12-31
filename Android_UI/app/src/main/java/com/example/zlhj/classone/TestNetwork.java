package com.example.zlhj.classone;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class TestNetwork extends AppCompatActivity {
    private TextView gps,wifi;
    private ConnectivityManager mg;

    private WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_network);

        gps = findViewById(R.id.gps);
        wifi = findViewById(R.id.wifi);

        mg = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 根据给定的网络类型获取该类型下的网络链接信息
        NetworkInfo.State gpsSate = mg.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

       NetworkInfo.State wifiSate =  mg.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

       wifi.setText("当前WiFi的链接状态:"+ wifiSate.toString());

       gps.setText("当前GPS的链接状态:"+ gpsSate.toString());

       if (!gpsSate.equals(NetworkInfo.State.CONNECTED) && ! wifiSate.equals(NetworkInfo.State.CONNECTED)){
//           跳转至设置页面
            Toast.makeText(TestNetwork.this,"当前无网络链接,稍后跳转到网络设置页面",Toast.LENGTH_SHORT).show();

           Timer timer = new Timer();
           timer.schedule(new MyTask(),5000);
        }

    }

    class MyTask extends TimerTask{
        @Override
        public void run() {
            Intent i = new Intent();
            i.setAction(Settings.ACTION_WIFI_SETTINGS);
            startActivity(i);
        }
    }
}
