package com.example.zlhj.classone;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestThree extends AppCompatActivity {
    private TextView tv;
    private Button btn;
    private String info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_three);

        btn = findViewById(R.id.btn);
        tv = findViewById(R.id.tv);
        // 获取传递过来的Intent对象
        if (getIntent() != null){
            Intent i = getIntent();
            // 从Intent中获取传递的数据
            // info = i.getStringExtra("name");

            Bundle bundle = i.getExtras();
            // 对版本有要求
             info = bundle.getString("name","未接收到传递的数据");

            tv.setText(info);
        }

        // 实现跳转到拨打电话页面的功能
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeCall(info);
            }
        });
    }

    private void takeCall(String info) {
        Intent intent = new Intent();
        // 隐式挑战
        // Uri.parse() 将一个字符串转化为Uri类型的方法
        // Uri 是一个唯一性标识,同意资源标识符
//         intent.setData(Uri.parse("tel://"+info));
//        intent.setAction(Intent.ACTION_DIAL); // 跳转到拨打电话页面

        // intent.setData(Uri.parse("tel://"+info));
        // intent.setAction(Intent.ACTION_CALL); // 直接拨打电话
        // 如果想直接拨打电话,需要给定权限.android.permission.call_phone

//        intent.setData(Uri.parse("smsto:"+info));
//        intent.setAction(Intent.ACTION_SENDTO);  // 发送短信

        intent.setData(Uri.parse("http://"+info));
        intent.setAction(Intent.ACTION_WEB_SEARCH);

        startActivity(intent);
    }
}
