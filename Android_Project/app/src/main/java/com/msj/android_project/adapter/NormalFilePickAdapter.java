package com.msj.android_project.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.msj.android_project.R;
import com.msj.android_project.activity.CommonWebViewActivity;
import com.msj.android_project.bean.PDFFile;
import com.msj.android_project.utils.MToast;
import com.msj.android_project.utils.ToolsUtil;

import java.util.List;


/**
 * Created by Vincent Woo
 * Date: 2016/10/26
 * Time: 10:23
 */

public class NormalFilePickAdapter extends RecyclerView.Adapter<NormalFilePickAdapter.NormalFilePickViewHolder> {
    private int mMaxNumber;
    private int mCurrentNumber = 0;
    private Context mContext;
    private List<PDFFile> mList;

    public NormalFilePickAdapter(Context ctx, List<PDFFile> list, int max) {
        mContext = ctx;
        mList = list;
        mMaxNumber = max;
    }

    @Override
    public NormalFilePickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.vw_layout_item_normal_file_pick, parent, false);
        return new NormalFilePickViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NormalFilePickViewHolder holder, final int position) {
        final PDFFile file = mList.get(position);

        holder.mTvTitle.setText(file.getFileName());
        holder.mTvTitle.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        if (holder.mTvTitle.getMeasuredWidth() >
                ToolsUtil.getScreenWidth(mContext) - ToolsUtil.dip2px(mContext, 10 + 32 + 10 + 48 + 10 * 2)) {
            holder.mTvTitle.setLines(2);
        } else {
            holder.mTvTitle.setLines(1);
        }

        if (file.isSelected()) {
            holder.mCbx.setSelected(true);
        } else {
            holder.mCbx.setSelected(false);
        }


        holder.mIvIcon.setImageResource(R.drawable.vw_ic_pdf);
        holder.mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, CommonWebViewActivity.class);
                intent.putExtra("url", file.getFilePath());
                intent.putExtra("isOpenFile", true);
                mContext.startActivity(intent);
            }
        });

        holder.mCbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isSelected() && isUpToMax()) {
                    MToast.showToast("已达到选择上限");
                    return;
                }

                if (v.isSelected()) {
                    holder.mCbx.setSelected(false);
                    mCurrentNumber--;
                } else {
                    holder.mCbx.setSelected(true);
                    mCurrentNumber++;
                }

                mList.get(holder.getAdapterPosition()).setSelected(holder.mCbx.isSelected());

                if (mListener != null) {
                    mListener.OnSelectStateChanged(holder.mCbx.isSelected(), mList.get(holder.getAdapterPosition()));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class NormalFilePickViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvIcon;
        private TextView mTvTitle;
        private ImageView mCbx;

        public NormalFilePickViewHolder(View itemView) {
            super(itemView);
            mIvIcon =  itemView.findViewById(R.id.ic_file);
            mTvTitle = itemView.findViewById(R.id.tv_file_title);
            mCbx =  itemView.findViewById(R.id.cbx);
        }
    }

    public void refresh(List<PDFFile> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }


    public  interface  OnSelectStateListener{
        void OnSelectStateChanged(boolean state, PDFFile file);
    }

    private OnSelectStateListener mListener;

    public void setOnSelectStateListener(OnSelectStateListener listener) {
        mListener = listener;
    }


    private boolean isUpToMax() {
        return mCurrentNumber >= mMaxNumber;
    }

}
