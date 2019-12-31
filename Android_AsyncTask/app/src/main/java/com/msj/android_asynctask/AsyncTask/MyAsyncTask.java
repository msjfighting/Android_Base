package com.msj.android_asynctask.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;

public class MyAsyncTask extends AsyncTask<Void,Void,Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("TAG","doInBackground");
        publishProgress(); // 触发onProgressUpdate方法
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("TAG","onPreExecute");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        Log.d("TAG","onProgressUpdate");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("TAG","onPostExecute");
    }


}
