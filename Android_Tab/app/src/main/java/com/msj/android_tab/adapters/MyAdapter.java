package com.msj.android_tab.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.msj.android_tab.BannerActivity;
import com.msj.android_tab.MainActivityWithTab;
import com.msj.android_tab.R;
import com.msj.android_tab.SegmentActivity;
import com.msj.android_tab.SplashActivity;

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
        return new MyViewHolder(View.inflate(mContext,R.layout.item_list,null));
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
                         i = new Intent(mContext, MainActivityWithTab.class);
                        break;
                    case 1:
                         i = new Intent(mContext, SplashActivity.class);
                        break;
                    case 2:
                         i = new Intent(mContext, BannerActivity.class);
                        break;
                    case 3:
                         i = new Intent(mContext, SegmentActivity.class);
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
        public void onItemClick(int position);
    }
    private OnItemClickLinsener onItemClickLinsener;

    public void setOnItemClickLinsener(OnItemClickLinsener onItemClickLinsener) {
        this.onItemClickLinsener = onItemClickLinsener;
    }
}
