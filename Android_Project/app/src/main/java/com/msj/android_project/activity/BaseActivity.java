package com.msj.android_project.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.msj.android_project.R;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView navbar_left;
    private TextView navbar_title;

    /*
       findViewById()
     */
    protected <T extends View> T fd(@IdRes int id){
        return findViewById(id);
    }

    /*
     初始化navbar

     */
    protected  void initNavBar(boolean isShowBack, String title){
        navbar_left = fd(R.id.navbar_left);
        navbar_title = fd(R.id.navbar_title);

        navbar_left.setVisibility(isShowBack ? View.VISIBLE : View.GONE);


        navbar_title.setText(title);

        navbar_left.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.navbar_left:{
                onBackPressed();
            }
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 不携带数据的页面跳转
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 有回调的跳转
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
