package com.msj.android_project.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.msj.android_project.R;
import com.msj.android_project.okhttp.CommonOkHttpClient;
import com.msj.android_project.okhttp.listener.DisposeDataHandle;
import com.msj.android_project.okhttp.listener.DisposeDataListener;
import com.msj.android_project.okhttp.listener.DisposeDownloadListener;
import com.msj.android_project.okhttp.request.CommonRequets;
import com.msj.android_project.okhttp.request.RequestParams;
import com.msj.android_project.utils.L;
import com.msj.android_project.interfaceUtil.CountingRequestBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpTestActivity extends BaseActivity {
    private TextView result;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private  Request.Builder builder = new Request.Builder();
    private ImageView id_imgv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);
        initNavBar(true,"好好学习");
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }

        result = findViewById(R.id.tv_result);
        id_imgv = findViewById(R.id.id_imgv);
    }

    public void doGet(View view){
        // 获取okHttpClient 对象
        //        OkHttpClient okHttpClient = new OkHttpClient();
        // 构造 Request
        //        Request.Builder builder = new Request.Builder();

//        Request request = builder.get().url("http://v2.api.haodanku.com/sales_list/apikey/youquanxiang/sale_type/1").build();
//        // 将request封装为Call
//        executeRequest(request);

        RequestParams params = new RequestParams();
        params.put("apikey","youquanxiang");
        params.put("sale_type","1");
        CommonOkHttpClient.get(CommonRequets.createGetRequest("http://v2.api.haodanku.com/sales_list",params)
                            ,new DisposeDataHandle(new DisposeDataListener() {
                                @Override
                                public void onSuccess(Object responseObj) {
                                    result.setText(responseObj.toString());
                                }

                                @Override
                                public void onFailure(Object responseObj) {

                                }
                        }));
    }

    public void download (View view){

//        Request request = builder.get().url("https://cms-bucket.ws.126.net/2019/08/25/a13431ba1df24c13985e434c320827ec.png").build();
//        // 将request封装为Call
//        Call call = okHttpClient.newCall(request);
//
//        // 执行Call
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                L.e("onFailure" + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                long total = response.body().contentLength();
//
//                long sum = 0L;
//
//
//                InputStream is = response.body().byteStream();
//
//                final Bitmap bitmap = BitmapFactory.decodeStream(is);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        id_imgv.setImageBitmap(bitmap);
//                    }
//                });
//
//
//                File file = new File(Environment.getExternalStorageDirectory(),"banner.png");
//                FileOutputStream fos = new FileOutputStream(file);
//                int len = 0;
//                byte[] buf = new byte[128];
//                while ((len = is.read(buf)) != -1){
//                    fos.write(buf,0,len);
//                    sum += len;
//                }
//                fos.flush();
//                fos.close();
//                is.close();
//
//
//            }
//        });
        CommonOkHttpClient.downloadFile(CommonRequets
                        .createGetRequest("https://img4.mukewang.com/590a955f0001b6aa01000100-100-100.jpg",null)
                        ,new DisposeDataHandle(new DisposeDownloadListener() {
                            @Override
                            public void onProgress(int progrss) {

                            }

                            @Override
                            public void onSuccess(Object responseObj) {
                              final Bitmap bitmap = BitmapFactory.decodeFile(((File)responseObj).getAbsolutePath());
                                id_imgv.setImageBitmap(bitmap);
                            }

                            @Override
                            public void onFailure(Object responseObj) {

                            }
        },Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+System.currentTimeMillis()+"jpg"));
    }

    public void doPost(View view){
//        RequestBody requestBody = new FormBody.Builder()
//                .add("parent_id","1")
//                .build();
//        //请求header的添加
//        //        Headers headers = new Headers.Builder().add("test","12")
//        //                .add("test1","456").build();
//
//        Request request = builder
//                .url("http://api.wangshuwen.com/getRegion")
//                .post(requestBody)
//                .build();
//
//        // 将request封装为Call
//        executeRequest(request);


        RequestParams params = new RequestParams();
        params.put("parent_id","1");
        CommonOkHttpClient.post(CommonRequets.createPostRequest("http://api.wangshuwen.com/getRegion",params)
                ,new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        result.setText(responseObj.toString());
                    }
                    @Override
                    public void onFailure(Object responseObj) {

                    }
                }));
    }

    public void doPostString(View view){

        RequestBody requestBody = RequestBody
                .create(MediaType.parse("text/plain;application/json;text/html"),"{parent_id:1862}");


        Request request = builder
                .url("http://api.wangshuwen.com/getRegion")
                .post(requestBody)
                .build();

        // 将request封装为Call
        executeRequest(request);

    }


    public void doPostFile (View view){
        File file = new File(Environment.getExternalStorageDirectory(),"msj.jpg");
        //        File file = new File("fileDir","guide_image1.jpg");
        if (!file.exists()){
            L.e(file.getAbsolutePath()+" not exist");
            return;
        }

        RequestBody fileBody = RequestBody
                .create(MediaType.parse("application/octet-stream"),file);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image","guide_image1.jpg",fileBody)
                .build();

        Headers headers = new Headers.Builder()
                .add("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiMTA4IiwiMTU2NjgwNDE2MDM3NyJdfQ.uS5ocgAy3b4eu2CQhqHvLg_RBu27b-Cv4XAc4mPIa5I")
                .add("app-version","1.0.4")
                .add("app-source","iOS")
                .build();

        Request request = builder
                .url("http://192.168.88.32:8900/rrd//user/head")
                .post(requestBody)
                .headers(headers)
                .build();

        // 将request封装为Call
        executeRequest(request);
    }

    public void doUpload(View view){
        File file = new File(Environment.getExternalStorageDirectory(),"msj.jpg");
        //        File file = new File("fileDir","guide_image1.jpg");
        if (!file.exists()){
            L.e(file.getAbsolutePath()+" not exist");
            return;
        }


        RequestBody fileBody = RequestBody
                .create(MediaType.parse("multipart/form-data"),file);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("head",file.getName(),fileBody)
                .build();

        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(long byteWrite, long contentLength) {
                L.e(byteWrite + "/" + contentLength);
            }
        });

        Headers headers = new Headers.Builder()
                .add("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiMTA4IiwiMTU2NjgwNjEwNTQ3NiJdfQ.uMYTXlsm_N70UEKoh6gFVJ4_8fBKgGYB_HMG4ZTB_qI")
                .add("app-version","1.0.4")
                .add("app-source","iOS")
                .build();

        Request request = builder
                .url("http://192.168.88.32:8900/rrd//user/head")
                .post(requestBody)
                .headers(headers)
                .build();

        // 将request封装为Call
        executeRequest(request);
    }


    private void executeRequest(Request request) {
        Call call = okHttpClient.newCall(request);

        // 执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("onFailure" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                final String res = response.body().string();
                L.e("onResponse" + res);

                // 主线程刷新页面
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(res);
                    }
                });
            }
        });
    }



}