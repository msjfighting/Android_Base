package com.msj.android_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.msj.android_project.R;
import com.msj.android_project.databinding.ActivityDataBindingBinding;

public class DataBindingDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingBinding binding =  DataBindingUtil.setContentView(this,R.layout.activity_data_binding);
    }
}
