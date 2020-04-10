package com.msj.android_project.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import com.msj.android_project.adapter.MyAdapter;
import com.msj.android_project.R;
import com.msj.android_project.bean.PDFFile;
import com.msj.android_project.utils.Constants;
import com.msj.android_project.utils.MToast;
import com.msj.android_project.utils.ToolsUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends BaseActivity {
    public final static int REQUEST_READ_PHONE_STATE = 1;
    private RecyclerView listView;
    private List<String> list = new ArrayList<>(Arrays.asList(
                    "图片预览效果","饼图","OkHttp","下拉刷新/下拉加载","流式布局","万能适配器",
                    "第三方Butterknife的学习","Eventbus(通信)学习","Retrofit网络框架",
                    "指纹,手势登录","静态使用Fragment","动态使用Fragment",
                    "activity与fragment间传值","提示框","app更新","访问assets下的图片",
                    "加载网络PDF","加载手机上的pdf","RecyclerView实现复杂布局","认识Handler",
                    "视频播放","Android美女拼图小游戏","架构MVC-MVP-MVVM","Data Binding"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.rv_list);

        initNavBar(false,"好好学习");

        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        // 添加分割线
        listView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        MyAdapter myAdapter = new MyAdapter(this);
        listView.setAdapter(myAdapter);
        myAdapter.addList(list);
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    ToolsUtil.getIMEI(this);
                    // 首次申请权限
                } else {
                    Toast.makeText(this, "权限已被用户拒绝", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            ArrayList<PDFFile> list = data.getParcelableArrayListExtra(Constants.RESULT_PICK_FILE);
            StringBuilder builder = new StringBuilder();
            for (PDFFile file : list) {
                String path = file.getFilePath();
                builder.append(path + "\n");
            }
            MToast.showToast(builder.toString());
        }

    }

}
