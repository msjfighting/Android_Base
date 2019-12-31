package com.msj.android_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class TagAdapter {

    public abstract int getItemCount();

    public abstract View createView(LayoutInflater inflater, ViewGroup viewGroup, int position);

    public abstract void bindView(View view,int position);

    public void onItemViewClick(View view, int position){

    }

    public void tipForSelectMax(View view, int mMaxSelectCount){

    }

    public void onItemSelected(View view,int position){

    }

    public void onItemUnSelected(View view,int position){

    }

    public interface OnDataSetChangedListener{
        void onDataChanged();
    }
    private OnDataSetChangedListener mListener;

    public void setOnDataSetChangedListener(OnDataSetChangedListener mListener) {
        this.mListener = mListener;
    }

    public void notifyDataSetChanged(){
        // 通知 TagFlowLayout.onDataChanged

        if (mListener != null){
            mListener.onDataChanged();
        }
    }
}
