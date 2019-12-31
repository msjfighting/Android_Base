package com.example.zlhj.classone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TestWebView extends AppCompatActivity {
    private EditText edit;
    private Button btn;
    private WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_view);

        edit = findViewById(R.id.wv_input);
        btn = findViewById(R.id.wv_btn);
        web = findViewById(R.id.wv);

        btn.setOnClickListener(new View.OnClickListener() {
            private String info;
            @Override
            public void onClick(View v) {
                info = edit.getText().toString();
                if (!TextUtils.isEmpty(info)){
                    shouPage(info);
                }else{
                    Toast.makeText(TestWebView.this,"请检查输入", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void shouPage(String info) {
        web.loadUrl(info);
        // 设置使用本地的客户端进行呈现
        web.setWebChromeClient(new WebChromeClient());
    }

    // 点击back按钮实现返回上一级页面,并且如果没有上一级,才进行退出当前应用程序
    // 重写back 方法
    @Override
    public void onBackPressed() {

        if (web.canGoBack()){
            web.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
