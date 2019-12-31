package com.msj.android_project.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.msj.android_project.utils.L;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    private List<List<View>> mAllView = new ArrayList<>();

    private List<Integer> mLineHeight = new ArrayList<>();

    private int mMaxLines;


    private static final int[] LL = new int[]{android.R.attr.maxLines};

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        // xml 调用方法 app:maxLines="30"
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
//        mMaxLines = typedArray.getInt(R.styleable.FlowLayout_maxLines,Integer.MAX_VALUE);

        //  xml 调用方法  android:maxLines="30"
        TypedArray typedArray = context.obtainStyledAttributes(attrs, LL);

        mMaxLines = typedArray.getInt(0,Integer.MAX_VALUE);

        typedArray.recycle();

        L.e("mMaxLines : " + mMaxLines);



    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int left = getPaddingLeft();
        int top = getPaddingTop();

        int lineCount = mAllView.size();
        for (int i = 0; i < lineCount; i++) {
            List<View> lineViews = mAllView.get(i);

            int lineHeight = mLineHeight.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);

                // gone...

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int lc = left + lp.leftMargin;

                int tc = top + lp.topMargin;

                int rc = lc + child.getMeasuredWidth();

                int bc = tc + child.getMeasuredHeight();

                child.layout(lc,tc,rc,bc);

                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            }

            left = getPaddingLeft();

            top += lineHeight;

        }
    }


    /**
     * 宽度确定
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mAllView.clear();
        mLineHeight.clear();

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // widthMeasureSpec
        // 建议宽度 + mode
        // 1. 300dp + exactly;
        // 2. parent width + at_most;
        // 3. 0, parent width  + unspecified;

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
       // int modeWidth = MeasureSpec.getMode(widthMeasureSpec);

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int cCount = getChildCount();

        int lineW = 0;

        int lineH = 0;

        int height = 0;

        List<View> lineViews = new ArrayList<>();

        // 拿到当前所有child需要占据的高度,设置给我们的容器
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == View.GONE){
                continue;
            }

            measureChild(child,widthMeasureSpec,heightMeasureSpec);

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int cW = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int cH = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineW + cW > sizeWidth - (getPaddingLeft() + getPaddingRight())){
                // 换行
                height += lineH;

                mLineHeight.add(lineH);
                mAllView.add(lineViews);
                lineViews = new ArrayList<>();
                lineViews.add(child);

                lineW = cW;
                lineH = cH;

            }else {
                lineW += cW;
                lineH = Math.max(lineH,cH);
                lineViews.add(child);
            }

            if (i == cCount -1){
                height += lineH;
                mLineHeight.add(lineH);
                mAllView.add(lineViews);
            }
        }


        // mMaxLines 校正

        if (mMaxLines < mLineHeight.size()){
            height = getMaxLinesHeight();
        }

        if (modeHeight == MeasureSpec.EXACTLY){
            height = sizeHeight;
        } else if (modeHeight == MeasureSpec.AT_MOST){
            height = Math.min(sizeHeight,height);
            height = height + getPaddingTop() + getPaddingBottom();
        } else if (modeHeight == MeasureSpec.UNSPECIFIED){
            height = height + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(sizeWidth,height);

    }

    private int getMaxLinesHeight() {

        int height = 0;
        for (int i = 0; i < mMaxLines; i++) {
            height += mLineHeight.get(i);
        }
        return height;
    }

    /**
     * child 没有设置 LayoutParams
     * @return
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }

    /**
     * inflater
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /** addView
     * 处理 LayoutParams 强转 MarginLayoutParams 的问题
     * @param p
     * @return
     */
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    /**
     * addView 处理 LayoutParams 强转 MarginLayoutParams 的问题
     * @param p
     * @return
     */
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }
}
