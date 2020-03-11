package com.msj.android_project.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.msj.android_project.R;
import com.msj.android_project.bean.DataModel;

public abstract class TypeAbstractViewHolder extends RecyclerView.ViewHolder {


    public TypeAbstractViewHolder(@NonNull View itemView) {
        super(itemView);
    }


    public abstract void bindHolder(DataModel dataModel);
}
