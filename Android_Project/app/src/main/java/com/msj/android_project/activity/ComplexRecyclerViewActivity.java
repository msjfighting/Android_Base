package com.msj.android_project.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.msj.android_project.R;
import com.msj.android_project.adapter.ComplexAdapter;
import com.msj.android_project.bean.DataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComplexRecyclerViewActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ComplexAdapter complexAdapter;

    int colors[] = {android.R.color.holo_red_dark,
            android.R.color.holo_blue_dark,
            android.R.color.holo_orange_dark};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_recycler_view);
        ButterKnife.bind(this);

        initNavBar(true, "好好学习");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = recyclerView.getAdapter().getItemViewType(position);
                if (type == DataModel.TYPE_THREE){
                    return gridLayoutManager.getSpanCount();
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);

        complexAdapter = new ComplexAdapter(this);

        recyclerView.setAdapter(complexAdapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
               GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
               int spanSize = layoutParams.getSpanSize();
               int spanIndex = layoutParams.getSpanIndex();
               outRect.top = 20;
               if (spanSize != gridLayoutManager.getSpanCount()) {
                   if (spanIndex == 1){
                       outRect.left = 20;
                   }else {
                       outRect.right = 20;
                   }
               }
            }
        });
        initData();
    }

    private void initData() {

        List<DataModel> list = new ArrayList<>();

        for (int i = 0; i < 30 ; i++){
            int type = (int) ((Math.random() * 3) + 1);
            DataModel dataModel = new DataModel();
            dataModel.avatarColor = colors[type - 1];
            dataModel.type = type;
            dataModel.name = "name : " + type;
            dataModel.content = "content : " + i;
            dataModel.contentColor = colors[(type + 1) % 3];
            list.add(dataModel);
        }

        complexAdapter.addList(list);
        complexAdapter.notifyDataSetChanged();
    }
}
