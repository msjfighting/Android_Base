package com.msj.android_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.msj.android_project.databinding.ActivityMvvmBinding;
import com.msj.android_project.mmodel.MVVMViewModel;
import com.msj.android_project.R;

public class MvvmActivity extends AppCompatActivity {

    private MVVMViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMvvmBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_mvvm);


        viewModel = new MVVMViewModel(getApplication());
        binding.setViewmodel(viewModel);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
