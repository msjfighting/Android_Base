package com.example.zlhj.classone;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class testAdapter extends AppCompatActivity {
    private ListView lv;
    private ArrayAdapter <String> adapter;
    private ArrayAdapter <CharSequence> adapter1;
    private List <String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_adapter);

        lv = findViewById(R.id.lv);
        list = new ArrayList<String>();
        for (int i = 0; i< 40; i++){
            list.add("数据"+i);
        }
        // 参数1: 上下文对象 参数2: 使用到的布局文件,给item进行使用的 参数3: 数据源对象
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        // 自定义view
//        adapter = new ArrayAdapter<String>(this,R.layout.array_adapter_item,list);

        // 数据源: string-array 读取xml文件中内容
        // 使用这种方式构造的适配器泛型是字符序列
//        adapter1 =  ArrayAdapter.createFromResource(this,R.array.list_array,android.R.layout.simple_list_item_1);

        // 将适配器设置到listView上
        lv.setAdapter(adapter);

        // 给ListView的item设置点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // parent : 适配器设置到的adapterView对象,在这里表示就是ListView
            // view : 适配器item对应的View
            // position : 索引位置 从0 开始 向下递增
            // id : 在listView中的item对应的行id
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(testAdapter.this,"点击的是索引:"+position+"id是:"+id,Toast.LENGTH_SHORT).show();
            }
        });

        // 实现item长按删除
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                list.remove(position);
                // 如果适配发生变化,需要当前的listView也通知到
                adapter.notifyDataSetChanged();
                return true;
            }

        });
    }
}
