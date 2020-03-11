package com.msj.android_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.msj.android_project.R;
import com.msj.android_project.bean.DataModel;

import java.util.ArrayList;
import java.util.List;

public class ComplexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

     private LayoutInflater layoutInflater;
     private List<DataModel> list = new ArrayList<>();

    public ComplexAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void addList(List<DataModel> list){
        this.list.addAll(list);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case DataModel.TYPE_ONE:{
                return new TypeOneViewHolder(layoutInflater.inflate(R.layout.item_one,parent,false));
            }
            case DataModel.TYPE_TWO:{
                return new TypeTwoViewHolder(layoutInflater.inflate(R.layout.item_two,parent,false));
            }
            case DataModel.TYPE_THREE:{
                return new TypeThreeViewHolder(layoutInflater.inflate(R.layout.item_three,parent,false));
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TypeAbstractViewHolder)holder).bindHolder(list.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
