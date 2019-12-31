package com.example.zlhj.classone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import myAdapter.MyAdapter;

public class custom_activity extends AppCompatActivity {
    private ListView lv;
    private List<String> list;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_activity);

        lv = findViewById(R.id.lv);

        list = new ArrayList<String>();
        for (int i = 0; i< 40; i++){
            list.add("数据"+i);
        }

        adapter = new MyAdapter(list,this);

        lv.setAdapter(adapter);
    }
   /* // 继承自BaseAdapter,实现四个方法
    class MyAdapter extends BaseAdapter{

        // 计算需要适配的item的总数
        @Override
        public int getCount() {
            return list.size();
        }

        // 获取每个item对象
        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        // 获取每个item的id值
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 获取每个item对应的view视图 ---- 重要
        // convertView 系统复用的视图
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
         *//*
            // 获取布局映射对象
            LayoutInflater inflater = LayoutInflater.from(custom_activity.this);
            // 将定义好的xml文件转化为View对象
            View view = inflater.inflate(R.layout.my_adapter,null);
            // 给View对象中的控件进行赋值
            TextView tv = view.findViewById(R.id.myadapter_tv);
            // 赋值
            tv.setText(getItem(position));
            *//*
         Holder holder;
         // 表示第一次运行
            if (convertView == null){
                convertView = LayoutInflater.from(custom_activity.this).inflate(R.layout.my_adapter,null);
                holder = new Holder();
                holder.tv = convertView.findViewById(R.id.myadapter_tv);
                // 打标签
                convertView.setTag(holder);
            }else {
                // 复用
                holder = (Holder) convertView.getTag();
            }
            //  赋值
            holder.tv.setText(list.get(position));
            return convertView;
        }
    }
    class Holder{
        TextView tv;
    }*/
}
