package com.msj.android_tab;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.msj.android_tab.adapters.MyAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestActivity extends BaseActicity {

    private RecyclerView listView;
    private List<String>  list = new ArrayList<>(Arrays.asList("ViewPager实现Tab效果","ViewPager实现导航图效果","ViewPager实现轮播动画","ViewPager实现Segment效果"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        listView = findViewById(R.id.rv_list);

        initNavTab(false,"ViewPager学习");

        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        // 添加分割线
        listView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        listView.setAdapter(new MyAdapter(this,list));
    }
}
