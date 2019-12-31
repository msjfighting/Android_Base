package com.msj.android_project.activity;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.msj.android_project.R;
import com.msj.android_project.utils.ToolsUtil;

import java.util.ArrayList;
import java.util.List;

public class AssetsActivity extends BaseActivity {
    private RecyclerView listView;
    private List<Bitmap> emojidata = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets);

        listView = findViewById(R.id.assetsRV);
        initNavBar(true,"好好学习");

        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        // 添加分割线
        listView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        emojidata = ToolsUtil.deepFile(this,"help_IMG");
        Adapter adapter = new Adapter();
        listView.setAdapter(adapter);
        adapter.setData(emojidata);
    }



    class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

        List<Bitmap> list = new ArrayList<>();

        public void setData(List<Bitmap> l) {
            list.clear();
            list.addAll(l);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(AssetsActivity.this).inflate(R.layout.item_image,viewGroup,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            myViewHolder.imageView.setImageBitmap(list.get(i));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image);
            }
        }
    }
}



