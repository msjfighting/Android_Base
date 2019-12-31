package com.msj.android_project.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.msj.android_project.R;

import java.util.ArrayList;

public class LayoutActivity extends BaseActivity {

    private ArrayList<String> lists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        initNavBar(true,"流失布局");

    }

    public void goFlowLayout(View view){
      Intent i = new Intent(LayoutActivity.this,FlowLayoutActivity.class);
      startActivity(i);
    }

    public void goFlexboxLayout(View view){
        Intent i = new Intent(LayoutActivity.this,FlexboxLayoutActivity.class);
        startActivity(i);
    }

    public void goFlexboxLayoutManager(View view){
        Intent i = new Intent(LayoutActivity.this,FlexBoxLayoutManagerActivity.class);
        startActivity(i);
    }

    public void goTagFlowLayout(View view){
        Intent i = new Intent(LayoutActivity.this,TagFlowLayoutActivity.class);
        startActivity(i);
    }
}
