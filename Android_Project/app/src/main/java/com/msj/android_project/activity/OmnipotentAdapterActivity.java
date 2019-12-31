package com.msj.android_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.msj.android_project.R;
import com.msj.android_project.adapter.CommonAdapter;
import com.msj.android_project.bean.Bean;
import com.msj.android_project.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class OmnipotentAdapterActivity extends AppCompatActivity {
    private ListView listview;
    private List<Bean> mDatas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omnipotent_adapter);

        listview = findViewById(R.id.listview);

        Bean bean = new Bean("Android新技能Get1","打造万能适配器1","2019-09-25","12345627898");
        mDatas.add(bean);
         bean = new Bean("Android新技能Get!2","打造万能适配器2","2019-09-12","12345673898");
        mDatas.add(bean);
         bean = new Bean("Android新技能Get!3","打造万能适配器3","2019-09-01","12345647898");
        mDatas.add(bean);
         bean = new Bean("Android新技能Get4!","打造万能适配器4","2019-09-15","12345167898");
        mDatas.add(bean);
         bean = new Bean("Android新技能Get5!","打造万能适配器5","2019-09-21","12345657898");
        mDatas.add(bean);
        bean = new Bean("Android新技能Get6!","打造万能适配器5","2019-09-21","12345657898");
        mDatas.add(bean);
        bean = new Bean("Android新技能Get7!","打造万能适配器5","2019-09-21","12345657898");
        mDatas.add(bean);
        bean = new Bean("Android新技能Get8!","打造万能适配器5","2019-09-21","12345657898");
        mDatas.add(bean);
        bean = new Bean("Android新技能Get9!","打造万能适配器5","2019-09-21","12345657898");
        mDatas.add(bean);
        bean = new Bean("Android新技能Get10!","打造万能适配器5","2019-09-21","12345657898");
        mDatas.add(bean);
        bean = new Bean("Android新技能Get11!","打造万能适配器5","2019-09-21","12345657898");
        mDatas.add(bean);
        bean = new Bean("Android新技能Get12!","打造万能适配器5","2019-09-21","12345657898");
        mDatas.add(bean);
        bean = new Bean("Android新技能Get12!","高德sdk定位","2019-09-21","12345657898");
        mDatas.add(bean);


        listview.setAdapter(new CommonAdapter<Bean>(this,mDatas,R.layout.item_listview) {
            private List<Integer> mPos = new ArrayList<>();
            @Override
            public void convert(final ViewHolder holder, Bean bean) {
                holder.setText(R.id.id_title,bean.getTitle())
                        .setText(R.id.id_desc,bean.getDesc())
                        .setText(R.id.id_time,bean.getTitle())
                        .setText(R.id.id_phone,bean.getPhone());

                final CheckBox cb = holder.getView(R.id.id_cb);
                // 解决复用问题 方式一
                // cb.setChecked(bean.isCheck());

                // 解决复用问题 方式二
                cb.setChecked(false);
                if ( mPos.contains(holder.getPostion())){
                    cb.setChecked(true);
                }
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //  bean.setCheck(cb.isChecked());

                        if (cb.isChecked()){
                            mPos.add(holder.getPostion());
                        }else{
                            mPos.remove((Integer) holder.getPostion());
                        }

                    }
                });
            }
        });

    }
}
