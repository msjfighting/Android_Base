package com.msj.android_project.activity;

import android.Manifest;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.msj.android_project.R;
import com.msj.android_project.view.DownApkService;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

public class DownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        Button btn = findViewById(R.id.down);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions.getInstance(DownActivity.this)
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean granted) {
                                if (granted) {
                                    String downUrl = "https://appsh.swlc.net.cn/shfcoc_webdata/download/shflcp.apk";
                                    Intent intent = new Intent(DownActivity.this, DownApkService.class);
                                    intent.putExtra("apk_url", downUrl);
                                    startService(intent);
                                }
                            }
                        });
            }
        });
    }
}
