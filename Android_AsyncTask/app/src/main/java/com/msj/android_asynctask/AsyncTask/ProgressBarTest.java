package com.msj.android_asynctask.AsyncTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.msj.android_asynctask.R;

public class ProgressBarTest extends Activity {
    private ProgressBar bar;
    private MyAsyncTask mTask;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);
        bar = findViewById(R.id.p1);

       mTask = new MyAsyncTask();
       mTask.execute();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //task不为空,并且它处于运行状态,那么我们将调用cancel方法
        if (null != mTask && mTask.getStatus() == AsyncTask.Status.RUNNING) {
            mTask.cancel(true);
        }
    }

    class MyAsyncTask extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            // 模拟进度更新
            for (int i = 0; i < 100; i++) {
                if (isCancelled()){
                    break;
                }
                publishProgress(i);
                try {
                    Thread.sleep(300);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (isCancelled()) return;
            // 获取进度更新值
            bar.setProgress(values[0]);

        }
    }
}
