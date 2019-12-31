package com.msj.android_project.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.msj.android_project.R;

import java.util.List;

public class FlexBoxLayoutManagerAdapter extends RecyclerView.Adapter<FlexBoxLayoutManagerAdapter.ViewHolder> {
    private List<String> mLists;
    private Context mContext;
    private LayoutInflater mInflater;

    public FlexBoxLayoutManagerAdapter( Context mContext,List<String> mLists) {
        this.mContext = mContext;
        this.mLists = mLists;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.item_tag,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.tvTag.setText(mLists.get(i));

    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTag = itemView.findViewById(R.id.tv);
        }
    }
}
