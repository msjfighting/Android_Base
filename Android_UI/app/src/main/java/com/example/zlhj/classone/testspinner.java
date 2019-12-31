package com.example.zlhj.classone;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.widget.ArrayAdapter.createFromResource;

public class testspinner extends AppCompatActivity {
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<CharSequence> adapterXML;
    private List<String> list;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testspinner);

        spinner = findViewById(R.id.testspinner);
        text = findViewById(R.id.spinner_tv);
//        initByList();
        initByXML();

        // 设置spinner的选中item
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            // adapterView中item被选中的时候实现的放法
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text.setText(adapterXML.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initByXML() {
        //
        adapterXML = ArrayAdapter.createFromResource(this, R.array.datalist, android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapterXML);
    }

    // 通过Java代码获取list<String>
    private void initByList() {
        list = new ArrayList<String>();
        for (int i = 0; i< 10; i++){
            list.add("数据"+i);
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }

}
