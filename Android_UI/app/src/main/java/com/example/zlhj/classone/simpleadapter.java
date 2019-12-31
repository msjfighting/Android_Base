package com.example.zlhj.classone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class simpleadapter extends AppCompatActivity {
   private ListView lv;
   private  SimpleAdapter simple;
   private List <Map<String,Object>> list,list1;
   private Map<String,Object> map,map1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpleadapter);

        lv = findViewById(R.id.lv);

        list = new ArrayList<Map<String,Object>>();

        for (int i = 0; i < 40; i++) {
            map = new HashMap<String, Object>();
            map.put("data","数据"+i);
            map.put("intro","介绍"+i);
            list.add(map);
        }
        String[] from = {"data","intro"};
        int[] to = {R.id.data,R.id.intro};
        // 参数1: 使用的上下文对象
        // 参数2: 数据源 List <Map<String,Object>>
        // 参数3: item对应的布局文件
        // 参数4: 表示有map中电仪的key组成的字符串类型的数据
        // 参数5: 需要显示的控件的id组成的int类型的数组
        // 保证参数4和参数5一一对应
//        simple = new SimpleAdapter(this,list,R.layout.simpleitem,from,to);


        // 图文混排
        list1 = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 40; i++) {
            map1 = new HashMap<String, Object>();
            map1.put("pic_image",R.drawable.female);
            map1.put("title","数据"+i);
            map1.put("desc","介绍"+i);
            list1.add(map1);
        }
        String[] from1 = {"pic_image","title","desc"};
        int[] to1 = {R.id.pic_image,R.id.title,R.id.desc};
        simple = new SimpleAdapter(this,list1,R.layout.simple_item_max,from1,to1);

        lv.setAdapter(simple);

    }
}
