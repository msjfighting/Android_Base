package com.msj.android_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.msj.android_project.R;

import butterknife.ButterKnife;

/**
 * 静态fragment
 * 1.继承fragment,重写onCreateView()回调方法,设置fragment的布局
 * 2.在activity中声明fragment 使用方法同view
 */
public class StaticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_static);
        ButterKnife.bind(this);
    }


//    @OnClick({R.id.fragment_title,R.id.fragment_content})
//    public click(View view){
//
//    }
}
