package com.example.zlhj.classone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class test_autocompletetextview extends AppCompatActivity {
    private AutoCompleteTextView auto;
    private ArrayAdapter<String> adapter;
    private  String[] tips = {"a1","aa2","bb3","aabb4","cbbc5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_autocompletetextview);

        auto = findViewById(R.id.auto);

        adapter = new ArrayAdapter<String>(this,R.layout.adappter_item,tips);

        // 设置适配器
        auto.setAdapter(adapter);
    }
}
