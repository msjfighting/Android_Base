package com.example.zlhj.classone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class test_ListView extends AppCompatActivity {
    private ListView lv;
    private List <String> list;
    private ArrayAdapter <String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test__list_view);

        lv = findViewById(R.id.lv);

        list = new ArrayList<String>();

        for (int i = 0 ; i < 20; i++){
            list.add("数据"+i);
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);

        View header = LayoutInflater.from(this).inflate(R.layout.header,null);
        lv.addHeaderView(header);

        Button footer = new Button(this);
        footer.setText("加载更多");
        lv.addFooterView(footer);
        lv.setAdapter(adapter);
    }
}
