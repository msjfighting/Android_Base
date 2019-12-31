package com.example.zlhj.classone;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import resultCode.Constants;


public class TestOne extends AppCompatActivity {
    private Button btn;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 写在 setContentView 之前 否则会报错 操作都是window窗体对象
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 去掉状态栏 操作的是WindowManager
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_test_one);
        btn = findViewById(R.id.button2);
        text = findViewById(R.id.text);

       /*
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              // Intent intent = new Intent(当前页面.this,目标页面.class);
                Intent intent = new Intent(TestOne.this,TestTwo.class);
                // 实现页面跳转
                // startActivity(intent);

                // 带返回值的跳转
                // 参数一: 请求的Intent对象  参数二: 请求码
                startActivityForResult(intent, Constants.REQUEST_CODE);
            }
        });
        */
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             // Intent i = new Intent(TestOne.this,TestTwo.class);

               // 设置组件名 ComponentName
               /*
               ComponentName cn = new ComponentName(TestOne.this,TestTwo.class);
               Intent i = new Intent();
               i.setComponent(cn);
               */
                Intent i = new Intent();
                i.setAction("com.example.zlhj.classone.TestOne.action");
               startActivity(i);
           }
       });
    }

    // 如果需要处理返回值,需要重写一个方法
    // requestCode  resultCode 用来区分那一个页面使用带有返回值进行跳转而来的 并且返回值有事给定给那一个页面的
    // data : 返回值时携带的Intent对象
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK){
            text.setText(data.getStringExtra("name"));
        }
    }
}
