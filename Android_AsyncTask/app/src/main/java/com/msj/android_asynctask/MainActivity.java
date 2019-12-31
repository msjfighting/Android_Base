package com.msj.android_asynctask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.msj.android_asynctask.AsyncTask.ProgressBarTest;
import com.msj.android_asynctask.util.ImageLoader;

public class MainActivity extends AppCompatActivity {
    private ImageView imgV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgV = findViewById(R.id.image);

    }

    public void loadImage(View view){
//        startActivity(new Intent(MainActivity.this, ImageTest.class));
        new ImageLoader().getInstance().displayImage(imgV,"https://upload-images.jianshu.io/upload_images/1479676-341551795bdb4a95?imageMogr2/auto-orient/strip%7CimageView2/2/w/300/format/webp");
    }
    public void progressbar(View view){
        startActivity(new Intent(MainActivity.this, ProgressBarTest.class));
    }
}
