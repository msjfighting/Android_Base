package com.msj.android_project.activity;

import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.msj.android_project.R;
import com.tbruyelle.rxpermissions.RxPermissions;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        RxPermissions.getInstance(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {

                    } else {

                    }

                });
    }
}
