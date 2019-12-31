package com.msj.android_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.msj.android_project.R;
import com.msj.android_project.http.UserInfoModel;
import com.msj.android_project.http.UserMgrService;
import com.msj.android_project.utils.Constants;
import com.msj.android_project.utils.L;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

    }

    public void onAction(View view){
        // 创建Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.Login_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        // 获取UserMgrService对象
        UserMgrService userMgrService = retrofit.create(UserMgrService.class);

        // 请求登录login方法

       Call<UserInfoModel> call = userMgrService.login(Constants.KEY,"13594347817","123456");

        call.enqueue(new Callback<UserInfoModel>() {
            @Override
            public void onResponse(Call<UserInfoModel> call, Response<UserInfoModel> response) {
                L.e(response.toString());
            }

            @Override
            public void onFailure(Call<UserInfoModel> call, Throwable t) {

            }
        });

    }



}
