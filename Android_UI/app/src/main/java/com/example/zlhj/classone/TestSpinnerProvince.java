package com.example.zlhj.classone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TestSpinnerProvince extends AppCompatActivity {
    private Spinner sheng,shi,qu;
    private TextView text;
    private ArrayAdapter<String> proviceAdapter;
    private ArrayAdapter<String> cityAdapter;
    private ArrayAdapter<String> countyAdapter;

    private int provicePosition;
    private  int cityPosition;
    private String[] provice = new  String[]{"北京","上海","天津","广东"};
    private String[][] city = new  String[][]{
                                        {"东城区","西城区","崇文区","宣武区","朝阳区","海淀区","丰台区","石景山区","门头沟区", "房山区","通州区","顺义区","大兴区","昌平区","平谷区","怀柔区","密云县","延庆县"},
                                        {"长宁区","静安区","普陀区","闸北区","虹口区","浦东新区"},
                                        {"和平区","河东区","河西区","南开区","河北区","红桥区","塘沽区","汉沽区","大港区","东丽区"},
                                        {"广州","深圳","韶关"}
                                        };
    private String[][][] county = new  String[][][]{
                                       {{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"}},
                                       {{"无"},{"无"},{"无"},{"无"},{"无"},{"无"}},
                                       {{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"}},
                                       {{"海珠区","荔湾区","越秀区","白云区","萝岗区","天河区","黄浦区","花都区","从化区","增城市","番禺区","南山区"},
                                         {"海珠区","荔湾区","越秀区","白云区","萝岗区","天河区","黄浦区","花都区","从化区","增城市","番禺区","南山区"},
                                         {"海珠区","荔湾区","越秀区","白云区","萝岗区","天河区","黄浦区","花都区","从化区","增城市","番禺区","南山区"}}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_spinner_province);
        initViewsById();

        initByList();


    }

    private void initViewsById() {
        sheng = findViewById(R.id.sheng);
        shi = findViewById(R.id.shi);
        qu = findViewById(R.id.qu);

        text = findViewById(R.id.text);
    }

    // 通过Java代码获取list<String>
    private void initByList() {
        proviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, provice);
        proviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sheng.setAdapter(proviceAdapter);


        sheng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cityAdapter = new ArrayAdapter<String>(TestSpinnerProvince.this, android.R.layout.simple_list_item_1, city[position]);
                    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    shi .setAdapter(cityAdapter);

                    // 记录当前的省级索引位置

                provicePosition = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        shi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                         countyAdapter = new ArrayAdapter<String>(TestSpinnerProvince.this, android.R.layout.simple_list_item_1, county[provicePosition][position]);
                         countyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                         qu.setAdapter(countyAdapter);
                       // 记录当前的市级索引位置
                         cityPosition = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        qu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text.setText("选中的城市是:"+provice[provicePosition] + city[provicePosition][cityPosition] + county[provicePosition][cityPosition][position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
