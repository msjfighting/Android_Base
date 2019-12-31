package com.msj.android_project.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.msj.android_project.R;

public class TabBarView extends FrameLayout {

    private ImageView mIvIcon,mSelect;
    private TextView mTvTitle;

    private static final int COLOR_DEFAULT = Color.parseColor("#ff000000");
    private static final int COLOR_SELECT = Color.parseColor("#ffFE3435");

    public TabBarView( Context context) {
        super(context);
        initView(context);
    }

    public TabBarView( Context context,  AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TabBarView( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public TabBarView( Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }


    private void initView(Context context) {
        inflate(context, R.layout.tableview,this);

        mIvIcon = findViewById(R.id.id_tabbar_icon);
        mSelect = findViewById(R.id.id_tabbar_select);
        mTvTitle = findViewById(R.id.id_tabbar_title);

        setProgress(0);

    }
    public void setIconAndTitle(int icon, int iconSelect, String title){
        mIvIcon.setImageResource(icon);
        mSelect.setImageResource(iconSelect);
        mTvTitle.setText(title);
    }
    public void setProgress(float progress){
        mIvIcon.setAlpha(progress);
        mSelect.setAlpha( 1 - progress);

        mTvTitle.setTextColor(evaluate(1 - progress,COLOR_DEFAULT,COLOR_SELECT));
    }


    public int evaluate(float fraction, int startValue, int endValue) {
        int startInt = startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return ((startA + (int)(fraction * (endA - startA))) << 24) |
                ((startR + (int)(fraction * (endR - startR))) << 16) |
                ((startG + (int)(fraction * (endG - startG))) << 8) |
                (startB + (int)(fraction * (endB - startB)));
    }

}
