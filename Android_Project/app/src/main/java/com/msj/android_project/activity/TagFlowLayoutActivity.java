package com.msj.android_project.activity;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.msj.android_project.R;
import com.msj.android_project.adapter.TagAdapter;
import com.msj.android_project.view.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagFlowLayoutActivity extends AppCompatActivity {
    private TagFlowLayout tagflowlayout;
    private static final List<String> mDataList = Arrays.asList("Android","hyman","imooc.com","Android","hyman","imooc.com","Android","hyman","imooc.com","Android","hyman","imooc.com");

    private List<String> mDatas =  new ArrayList<>();
    private TagAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_flow_layout);

        tagflowlayout = findViewById(R.id.tagflowlayout);
        tagflowlayout.setmMaxSelectCount(3);

        mDatas.clear();
        for (int i = 0; i < 30; i++) {
            mDatas.add(addTag());
        }
        tagflowlayout.setAdapter(mAdapter =new TagAdapter() {
            @Override
            public int getItemCount() {
                return mDatas.size();
            }

            @Override
            public View createView(LayoutInflater inflater, ViewGroup viewGroup, int position) {
                return inflater.inflate(R.layout.item_select_tag,viewGroup,false);
            }

            @Override
            public void bindView(View view, int position) {
                TextView textView = view.findViewById(R.id.tv);
                textView.setText(mDatas.get(position));

            }

            @Override
            public void onItemViewClick(View view, int position) {
                super.onItemViewClick(view, position);

            }

            @Override
            public void tipForSelectMax(View view, int mMaxSelectCount) {
                super.tipForSelectMax(view, mMaxSelectCount);
                Toast.makeText(view.getContext(),"最多选择 "+mMaxSelectCount+" 个",Toast.LENGTH_SHORT).show();
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(View view, int position) {
                super.onItemSelected(view, position);
                TextView textView = view.findViewById(R.id.tv);
                textView.setTextColor(R.color.greenColor);
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemUnSelected(View view, int position) {
                super.onItemUnSelected(view, position);
                TextView textView = view.findViewById(R.id.tv);
                textView.setTextColor(R.color.white);
            }
        });

    }

    public void changeData(View view){
        mDatas.clear();
        mDatas.addAll(new ArrayList<>(Arrays.asList("Discover interesting projects and people to populate your personal news feed".split(" "))));
        tagflowlayout.setmMaxSelectCount(1);
        mAdapter.notifyDataSetChanged();
    }
    public void getResult(View view){
        Toast.makeText(view.getContext(),"选择的items是: "+tagflowlayout.getSelectedItemPositionList().toString(),Toast.LENGTH_SHORT).show();
    }


    public String addTag(){
        return mDataList.get((int) (Math.random() * (mDataList.size() - 1)));
    }
}
