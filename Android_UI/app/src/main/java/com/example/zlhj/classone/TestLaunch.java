package com.example.zlhj.classone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TestLaunch extends AppCompatActivity {
    private Button jump;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_launch);

        jump = findViewById(R.id.jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip(v);
            }
        });

        Log.i("TAG","页面一的onCreate方法被调用");
    }

    public void skip(View view){
        Intent i = new Intent(this,TestLaunchTwo.class);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG","页面一的onDestroy方法被调用");
    }
}
