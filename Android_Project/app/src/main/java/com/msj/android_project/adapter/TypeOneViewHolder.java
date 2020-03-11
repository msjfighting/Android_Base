package com.msj.android_project.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.msj.android_project.R;
import com.msj.android_project.bean.DataModel;

public class TypeOneViewHolder extends TypeAbstractViewHolder{

    private ImageView imageView;
    private TextView textView;

    public TypeOneViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.avatar);
        textView = itemView.findViewById(R.id.name);
        itemView.setBackgroundColor(Color.YELLOW);
    }

   @Override
    public void bindHolder(DataModel dataModel){
        imageView.setBackgroundResource(dataModel.avatarColor);
        textView.setText(dataModel.name);
    }
}
