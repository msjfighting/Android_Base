package com.msj.android_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.msj.android_project.R;
import com.msj.android_project.adapter.FlexBoxLayoutManagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlexBoxLayoutManagerActivity extends AppCompatActivity {
    private RecyclerView recycleview;
    private static final List<String> mDataList = Arrays.asList("Android","hyman","imooc.com","Android","hyman","imooc.com","Android","hyman","imooc.com","Android","hyman","imooc.com");

    private List<String> mDatas =  new ArrayList<>();
    private FlexBoxLayoutManagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flex_box_layout_manager);

        recycleview = findViewById(R.id.recycleview);
        recycleview.setLayoutManager(new FlexboxLayoutManager(this));
        mDatas.clear();
        for (int i = 0; i < 100; i++) {
            mDatas.add(addTag());
        }

        mAdapter = new FlexBoxLayoutManagerAdapter(this,mDatas);

        recycleview.setAdapter(mAdapter);

    }


    public String addTag(){
        return mDataList.get((int) (Math.random() * (mDataList.size() - 1)));
    }
}
