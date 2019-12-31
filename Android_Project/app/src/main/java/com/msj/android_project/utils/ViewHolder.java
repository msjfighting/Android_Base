package com.msj.android_project.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPostion;
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {

        mPostion = position;
        mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        mConvertView.setTag(this);
        mContext = context;

    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent,int layoutId, int position){
        if (convertView == null){
            return new ViewHolder(context,parent,layoutId,position);
        }else{
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPostion = position;
            holder.mContext = context;
            return holder;
        }
    }

    /**
     * 通过viewId获取控件
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends  View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    public View getConvertView (){
        return mConvertView;
    }

    public int getPostion() {
        return mPostion;
    }

    /**
     * 设置TextView  (链式编程)
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置 ImageView (链式编程)
     * @param viewId
     * @param
     * @return
     */
    public ViewHolder setImageResource(int viewId, int resId){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    /**
     * 设置 ImageView (链式编程)
     * @param viewId
     * @param
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    /**
     * Glide 加载网络图片
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageUrl(int viewId, String url){
        ImageView imageView = getView(viewId);
        Glide.with(mContext).load(url).into(imageView);
        return this;
    }
}
