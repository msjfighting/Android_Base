package com.msj.android_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.msj.android_project.R;
import com.msj.android_project.utils.ViewHolder;

import java.util.List;

public abstract class CommonAdapter <T> extends BaseAdapter {
    // protected 子类可以访问
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;

    public CommonAdapter(Context context, List<T> datas,int layoutId) {
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public  View getView(int i, View view, ViewGroup viewGroup){
        ViewHolder holder = ViewHolder.get(mContext,view,viewGroup, layoutId,i);
        convert(holder,getItem(i));
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T t);
}
