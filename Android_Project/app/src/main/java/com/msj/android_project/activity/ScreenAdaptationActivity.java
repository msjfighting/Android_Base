package com.msj.android_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BaseObservable;

import android.os.Bundle;

import com.msj.android_project.R;

import butterknife.ButterKnife;

public class ScreenAdaptationActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_adaptation);
        ButterKnife.bind(this);
        initNavBar(true, "屏幕适配");
    }
}
