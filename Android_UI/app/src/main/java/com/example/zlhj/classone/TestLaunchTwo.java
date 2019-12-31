package com.example.zlhj.classone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestLaunchTwo extends AppCompatActivity implements View.OnClickListener {
    private TextView tv;
    private Button jump;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_launch_two);
        tv = findViewById(R.id.tv);
        jump = findViewById(R.id.jump);

        Log.i("TAG","页面二的onCreate方法被调用");

        jump.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG","页面二的onDestroy方法被调用");
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this,TestLaunch.class);
//        Intent i = new Intent(this,TestLaunchTwo.class);
        startActivity(i);

    }
}
