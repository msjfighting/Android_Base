package com.example.zlhj.classone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TestTwo extends AppCompatActivity {
    // 页面实例被创建的方法
    private Button btn,threeBtn;
    EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_two);

        btn = findViewById(R.id.button);
        threeBtn = findViewById(R.id.button1);
        edit = findViewById(R.id.text);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("name","天气不错啊");
                // 参数一 : 返回码  参数二 : Intent对象
                setResult(RESULT_OK,i);

                // 结束当前的activity
                finish();
            }
        });


        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 带参数的跳转
                Intent i = new Intent(TestTwo.this,TestThree.class);
//                i.putExtra("name",edit.getText().toString());

                Bundle bundle = new Bundle();
                bundle.putString("name",edit.getText().toString());
                i.putExtras(bundle);

                startActivity(i);
            }
        });

        Log.i("TAG","onCreate方法执行了");
    }

    // 启动当前的activity实例的方法
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("TAG","onStart方法执行了");
    }

    // 如果该方法执行,页面的实例和用户可以交互
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG","onResume方法执行了");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("TAG","onPause方法执行了");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("TAG","onStop方法执行了");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG","onDestroy方法执行了");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("TAG","onRestart方法执行了");
    }
}
