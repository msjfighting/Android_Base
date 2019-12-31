package com.msj.android_project.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msj.android_project.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextImageCell extends LinearLayout {
    @BindView(R.id.cell_title)
    TextView mTextView;

    @BindView(R.id.cell_right_image)
    ImageView mImageView;

    @BindView(R.id.cell_linearLayout)
    LinearLayout mLinearLayout;

    public interface itemClickListener{
        void itemClick();
    }

    private static itemClickListener listener;

    //向外暴漏接口
    public void setItemClickListener(itemClickListener listener) {
        TextImageCell.listener = listener;
    }


    public TextImageCell(Context context) {
        super(context);
        initView(context,null);
    }

    public TextImageCell(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public TextImageCell(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TextImageCell(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        // 获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.TextImageCell);

        String text = typedArray.getString(R.styleable.TextImageCell_cell_title);
        int icon = typedArray.getResourceId(R.styleable.TextImageCell_cell_rightImage,R.mipmap.ic_launcher);

        // 获取图片
        Drawable drawable = typedArray.getDrawable(R.styleable.TextImageCell_cell_rightImage);

        // 获取引用类型，这里获取的是引用类型资源的资源 id ，然后还得我们自己用这个id 才恩那个拿到引用了；类型资源对象
        int resourceId = typedArray.getResourceId(R.styleable.TextImageCell_cell_rightImage, 0);

        typedArray.recycle();

        // 自定义xml转view 绑定layout布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_text_image,this,false);
        ButterKnife.bind(this,view);

        // 布局关联属性
        mImageView.setBackgroundResource(resourceId);
        mTextView.setText(text);
        if (mImageView.getVisibility() != View.GONE){
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        listener.itemClick();
                    }
                }
            });
        }

        addView(view);
    }


    public void setImageViewIcon(int resid){
        mImageView.setImageResource(resid);
    }

    public void setTitle(String title){
        mTextView.setText(title);
    }
}
