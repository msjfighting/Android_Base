package com.msj.android_imageselector;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.msj.android_imageselector.utils.ImageLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public   class ImageAdapter extends BaseAdapter {
    private List<String> mList;
    private String mDirPath;
    private static Set<String> mSelectImg = new HashSet<>();

    private LayoutInflater mInflater;

    public ImageAdapter(Context context, List<String> list, String path) {
        mList = list;
        mDirPath = path;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null){
            view = mInflater.inflate(R.layout.item_gridview,viewGroup,false);
            viewHolder = new ViewHolder();

            viewHolder.mImg = view.findViewById(R.id.id_item_image);
            viewHolder.mBtn = view.findViewById(R.id.id_item_selsect);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.mImg.setImageResource(R.drawable.picture_no);
        viewHolder.mBtn.setImageResource(R.drawable.picture_unselected);
        viewHolder.mImg.setColorFilter(null);

        ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(mDirPath+"/"+mList.get(i),viewHolder.mImg);
        final String path = mDirPath + "/"+mList.get(i);
        viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mSelectImg.contains(path)){

                    mSelectImg.remove(path);
                    viewHolder.mImg.setColorFilter(null);
                    viewHolder.mBtn.setImageResource(R.drawable.picture_unselected);
                }else{
                    viewHolder.mImg.setColorFilter(Color.parseColor("#77000000"));
                    viewHolder.mBtn.setImageResource(R.drawable.picture_selected);
                    mSelectImg.add(path);
                }
            }
        });

        if (mSelectImg.contains(path)){
            viewHolder.mImg.setColorFilter(Color.parseColor("#77000000"));
            viewHolder.mBtn.setImageResource(R.drawable.picture_selected);
        }

        return view;
    }

    private class ViewHolder{
        ImageView mImg;
        ImageButton mBtn;
    }
}