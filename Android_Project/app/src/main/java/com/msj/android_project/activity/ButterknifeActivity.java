package com.msj.android_project.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.msj.android_project.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ButterknifeActivity extends AppCompatActivity {
    @BindView(R.id.id_imageview)  ImageView imageview;
    @BindView(R.id.id_tv) TextView textView;
//    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterknife);
        ButterKnife.bind(this);
    }

    public void goActivity(View view){
        imageview.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.id_tv)
    public void jump(){
        Toast.makeText(this,"跳转页面",Toast.LENGTH_LONG).show();
    }

    public void goFragment(View view){
        Intent intent = new Intent(this, RefreshActivity.class);
        this.startActivity(intent);
    }
}
