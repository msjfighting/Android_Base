package com.msj.android_tab.views;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.msj.android_tab.R;

public class TabView extends FrameLayout {

    private ImageView mIvIcon,mSelect;
    private TextView mTvTitle;

    private static final int COLOR_DEFAULT = Color.parseColor("#ff000000");
    private static final int COLOR_SELECT = Color.parseColor("#ff45c01a");

    public TabView( Context context) {
        super(context);
        initView(context,null);
    }

    public TabView( Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public TabView( Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context,attrs);
    }

    public void initView(Context context, AttributeSet attrs){
        if (attrs == null) return;
        inflate(context, R.layout.tab_view,this);

        mIvIcon = findViewById(R.id.iv_icon);
        mSelect = findViewById(R.id.iv_select);
        mTvTitle = findViewById(R.id.iv_title);

        setProgress(0);
    }

    public void setIconAndTitle(int icon, int iconSelect, String title){
        mIvIcon.setImageResource(icon);
        mSelect.setImageResource(iconSelect);
        mTvTitle.setText(title);
    }


    public void setProgress(float progress){
        mIvIcon.setAlpha(progress);
        mSelect.setAlpha( 1- progress);

        mTvTitle.setTextColor(evaluate(progress,COLOR_DEFAULT,COLOR_SELECT));
    }
    public int evaluate(float fraction, int startValue, int endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int)((startA + (int)(fraction * (endA - startA))) << 24) |
                (int)((startR + (int)(fraction * (endR - startR))) << 16) |
                (int)((startG + (int)(fraction * (endG - startG))) << 8) |
                (int)((startB + (int)(fraction * (endB - startB))));
    }
}
