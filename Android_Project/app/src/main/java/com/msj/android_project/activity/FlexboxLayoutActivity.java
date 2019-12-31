package com.msj.android_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.msj.android_project.R;

import java.util.Arrays;
import java.util.List;

public class FlexboxLayoutActivity extends AppCompatActivity {
    private FlexboxLayout flexbox;
    private static final List<String> mDataList = Arrays.asList("Android","hyman","imooc.com","Android","hyman","imooc.com","Android","hyman","imooc.com","Android","hyman","imooc.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexbox_layout);
        flexbox = findViewById(R.id.flexbox);


    }

    public void addTag(View view){
        TextView tag = (TextView) LayoutInflater.from(this).inflate(R.layout.item_tag,flexbox,false);

        tag.setText(mDataList.get((int) (Math.random() * (mDataList.size() - 1))));

        flexbox.addView(tag);
    }
}
