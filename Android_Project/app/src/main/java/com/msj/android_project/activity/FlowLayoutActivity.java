package com.msj.android_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.msj.android_project.R;
import com.msj.android_project.view.FlowLayout;

import java.util.Arrays;
import java.util.List;

public class FlowLayoutActivity extends AppCompatActivity {
    private FlowLayout flowlayout;
    private static final List<String> mDataList = Arrays.asList("Android","hyman","imooc.com","Android","hyman","imooc.com","Android","hyman","imooc.com","Android","hyman","imooc.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        flowlayout = findViewById(R.id.flowlayout);

    }

    private Button generateBtn(){
        Button btn = new Button(this);

        btn.setText("addd");

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        btn.setLayoutParams(lp);

        return btn;
    }

    public void addTag(View view){
      TextView tag = (TextView) LayoutInflater.from(this).inflate(R.layout.item_tag,flowlayout,false);

      tag.setText(mDataList.get((int) (Math.random() * (mDataList.size() - 1))));

      flowlayout.addView(tag);
    }
}
