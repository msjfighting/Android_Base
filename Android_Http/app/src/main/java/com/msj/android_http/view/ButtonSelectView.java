package com.msj.android_http.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.msj.android_http.R;

public class ButtonSelectView extends RelativeLayout {

    private String mText;
    private Drawable mDrawable;

    private TextView textView;
    private ImageView imageView;

    private onButtonSelectClickListener mListener;


    public ButtonSelectView(Context context) {
        super(context);
        initView(context,null);
    }

    public ButtonSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public ButtonSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    public ButtonSelectView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context,attrs);
    }

    private void initView(Context context,AttributeSet attrs){
        if (attrs == null) return;
        inflate(context, R.layout.button_select_layout,this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ButtonSelectView);

        mText = typedArray.getString(R.styleable.ButtonSelectView_text);
        mDrawable = typedArray.getDrawable(R.styleable.ButtonSelectView_icon_left);

        textView = findViewById(R.id.tv_option);
        imageView = findViewById(R.id.img_option);

        textView.setText(mText);

        imageView.setImageDrawable(mDrawable);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.onClick();
                }
            }
        });
    }

    public void setIcon(int resource){
        imageView.setImageResource(resource);
    }

    public void setText(String text){
        textView.setText(text);
    }
    public void setButtonSate(String text,int resource){
        setText(text);
        setIcon(resource);
    }

    public void enabled(boolean enable){
        textView.setEnabled(enable);
    }

    public void setSelect(boolean select){
        textView.setSelected(select);
        setEnabled(false);
    }

    public interface  onButtonSelectClickListener{
        void onClick();
    }

    public void setListener(onButtonSelectClickListener listener){
        this.mListener = listener;
    }
}
