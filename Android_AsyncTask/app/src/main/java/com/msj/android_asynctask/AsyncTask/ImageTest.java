package com.msj.android_asynctask.AsyncTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.msj.android_asynctask.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageTest extends Activity {
    private ImageView image;
    private ProgressBar bar;
    private static String IMAGE_URL = "https://img4.mukewang.com/57075b5a0001d47d06000338-240-135.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        image = findViewById(R.id.image);
        bar = findViewById(R.id.bar);


        new MyAsyncTask().execute(IMAGE_URL);

    }
    class MyAsyncTask extends AsyncTask<String ,Void, Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 开始之前 显示加载

            bar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            image.setImageBitmap(bitmap);
            bar.setVisibility(View.GONE);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            // 获取传递进来的参数
            String url = strings[0];
            Bitmap bitmap = null;
            URLConnection connection;
            InputStream is;
            try {
                // 获取 URLConnection
                connection = new URL(url).openConnection();
                // 获取输入流
                is = connection.getInputStream();

                BufferedInputStream bis = new BufferedInputStream(is);

                 Thread.sleep(3000);


                // 通过decodeStream 解析输入流
                bitmap = BitmapFactory.decodeStream(bis);

                is.close();
                bis.close();

                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
