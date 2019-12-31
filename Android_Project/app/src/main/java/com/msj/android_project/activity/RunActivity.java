package com.msj.android_project.activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.msj.android_project.R;
import com.msj.android_project.fragment.ContentFragment;
import com.msj.android_project.fragment.TitleFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RunActivity extends AppCompatActivity {
    private FragmentManager manager;
    private FragmentTransaction transaction;
    @BindView(R.id.rb_home)
    RadioButton home;

    @BindView(R.id.rb_hot)
    RadioButton hot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        ButterKnife.bind(this);

        // 1.创建fragment管理器对象
         manager = getSupportFragmentManager();

        // 2.获取fragment的事务对象并且开启事务
        transaction = manager.beginTransaction();

        // 3.调用事务中响应的fragment
        transaction.add(R.id.content_layout,new ContentFragment());

        // 提交事务
        transaction.commit();

    }

    @OnClick({R.id.rb_home,R.id.rb_hot})
    public void click(View view){
        transaction = manager.beginTransaction();
        switch (view.getId()){
            case R.id.rb_home:
                transaction.replace(R.id.content_layout,new ContentFragment());
                break;
            case R.id.rb_hot:
                transaction.replace(R.id.content_layout,new TitleFragment());
                break;

        }

        transaction.commit();
    }


    // 动态添加fragment
    public void createRunFragment(){
        // 1.创建fragment管理器对象
        FragmentManager manager = getSupportFragmentManager();

        // 2.获取fragment的事务对象并且开启事务
        FragmentTransaction transaction = manager.beginTransaction();

        // 3.调用事务中响应的fragment
//        transaction.add(R.id.title_layout,new TitleFragment());
//        transaction.add(R.id.content_layout,new ContentFragment());

        // 提交事务
        transaction.commit();
    }
}
