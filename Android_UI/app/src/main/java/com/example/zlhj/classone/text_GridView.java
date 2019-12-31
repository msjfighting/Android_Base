package com.example.zlhj.classone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myAdapter.TestAdapter;

public class text_GridView extends AppCompatActivity {
    private GridView grid;
    private List<Map<String,Object>> list;
    private HashMap<String,Object> map;
    private TestAdapter adapter;
    private int[] images = {R.drawable.female,R.drawable.female,R.drawable.female,R.drawable.female,R.drawable.female};
    private String[] names = {"照相机","相片","联系人","性别","音乐"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text__grid_view);

        grid = findViewById(R.id.grid);

        // 图文混排
        /*
        list = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 40; i++) {
            map = new HashMap<String, Object>();
            map.put("pic_image",R.drawable.female);
            map.put("title","数据"+i);
            list.add(map);
        }
        */

        adapter = new TestAdapter(this,images,names);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(text_GridView.this,"选中了"+names[position],Toast.LENGTH_SHORT).show();
            }
        });
    }
}
