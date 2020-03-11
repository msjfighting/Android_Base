package com.msj.android_project.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.msj.android_project.R;
import com.msj.android_project.bean.DataModel;

public class TypeThreeViewHolder extends TypeAbstractViewHolder {

    private ImageView imageView;
    private TextView textView;

    private TextView content;
    private ImageView contentImageView;



    public TypeThreeViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.avatar);
        textView = itemView.findViewById(R.id.name);
        content = itemView.findViewById(R.id.content);
        contentImageView = itemView.findViewById(R.id.contentImage);
        itemView.setBackgroundColor(Color.RED);
    }

    @Override
    public void bindHolder(DataModel dataModel){
        imageView.setBackgroundResource(dataModel.avatarColor);
        textView.setText(dataModel.name);
        content.setText(dataModel.content);
        contentImageView.setBackgroundResource(dataModel.contentColor);
    }
}
