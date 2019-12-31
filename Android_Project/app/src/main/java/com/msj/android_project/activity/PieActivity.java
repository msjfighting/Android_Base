package com.msj.android_project.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.msj.android_project.bean.BillBean;
import com.msj.android_project.fragment.PieFragment;
import com.msj.android_project.R;
import com.msj.android_project.utils.DataUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class PieActivity extends BaseActivity {
    private ViewPager vpMian;
    private String jsonStr;
    private List<BillBean> bills;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);

        initNavBar(true,"好好学习");

        initData();

        initView();
    }

    private void initData() {
        jsonStr = DataUtil.getJsonFromAssets(this,"json.json");

        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<BillBean>>(){}.getType();

        bills = gson.fromJson(jsonStr,type);

    }

    private void initView() {
        vpMian = findViewById(R.id.vp_main);

        vpMian.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return PieFragment.newInstance(bills.get(i));
            }
            @Override
            public int getCount() {
                return bills.size();
            }
        });
    }
}
