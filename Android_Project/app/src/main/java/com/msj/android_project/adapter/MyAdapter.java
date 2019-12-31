package com.msj.android_project.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.msj.android_project.R;
import com.msj.android_project.activity.AssetsActivity;
import com.msj.android_project.activity.ButterknifeActivity;
import com.msj.android_project.activity.DialogActivity;
import com.msj.android_project.activity.DownActivity;
import com.msj.android_project.activity.EventbusActivity;
import com.msj.android_project.activity.FingerActivity;
import com.msj.android_project.activity.FragmentParamActivity;
import com.msj.android_project.activity.HTDetailActivity;
import com.msj.android_project.activity.HttpTestActivity;
import com.msj.android_project.Image2PDFActivity;
import com.msj.android_project.activity.ImageActivity;
import com.msj.android_project.activity.NormalFilePickActivity;
import com.msj.android_project.activity.OmnipotentAdapterActivity;
import com.msj.android_project.activity.PieActivity;
import com.msj.android_project.activity.RefreshActivity;
import com.msj.android_project.activity.LayoutActivity;
import com.msj.android_project.activity.RetrofitActivity;
import com.msj.android_project.activity.RunActivity;
import com.msj.android_project.activity.StaticActivity;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mList;

    public MyAdapter(Context mContext,List<String> list) {
        this.mContext = mContext;
        mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.item_list,null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.title.setText(mList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = null;
                switch (position){
                    case 0:
                         i = new Intent(mContext, ImageActivity.class);
                        break;
                    case 1:
                         i = new Intent(mContext, PieActivity.class);
                        break;
                    case 2:
                         i = new Intent(mContext, HttpTestActivity.class);
                        break;
                    case 3:
                         i = new Intent(mContext, RefreshActivity.class);
                        break;
                    case 4:
                        i = new Intent(mContext, LayoutActivity.class);
                        break;
                    case 5:
                        i = new Intent(mContext, OmnipotentAdapterActivity.class);
                        break;
                    case 6:
                        i = new Intent(mContext, ButterknifeActivity.class);
                        break;
                    case 7:
                        i = new Intent(mContext, EventbusActivity.class);
                        break;
                    case 8:
                        i = new Intent(mContext, RetrofitActivity.class);
                        break;
                    case 9:
                        i = new Intent(mContext, FingerActivity.class);
                        break;
                    case 10:
                        i = new Intent(mContext, StaticActivity.class);
                        break;
                    case 11:
                        i = new Intent(mContext, RunActivity.class);
                        break;
                    case 12:
                        i = new Intent(mContext, FragmentParamActivity.class);
                        break;
                    case 13:
                        i = new Intent(mContext, DialogActivity.class);
                        break;
                    case 14:
                        i = new Intent(mContext, DownActivity.class);
                        break;
                    case 15:
                        i = new Intent(mContext, AssetsActivity.class);
                        break;
                    case 16:
                        i = new Intent(mContext, HTDetailActivity.class);
                        break;
                    case 17:
                        i = new Intent(mContext, NormalFilePickActivity.class);
                        break;

                }
                mContext.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private View itemView;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            title = itemView.findViewById(R.id.tv_title);
        }
    }

    public interface OnItemClickLinsener{
        void onItemClick(int position);
    }
    private OnItemClickLinsener onItemClickLinsener;

    public void setOnItemClickLinsener(OnItemClickLinsener onItemClickLinsener) {
        this.onItemClickLinsener = onItemClickLinsener;
    }
}