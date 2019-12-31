package com.msj.android_tab.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Px;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msj.android_tab.R;

import java.util.List;

public class ViewPagerIndicator extends LinearLayout {

    private Paint mPaint;
    private Path mPath;

    private int mTriangleWidth;

    private int mTriangleHeight;

    private static final float RADIO_TRIANGLE_WIDTH = 1/6F;
    private static final int color_text_normal = 0x77ffffff;
    private static final int color_text_heighlight = 0xffffffff;
    private static final int count_default_count = 4;
    /**
     * 三角形底边最大宽度
     */
    private final int dimension_triangle_width_max = (int) (getScreenWidth() / 3 * RADIO_TRIANGLE_WIDTH);

    private int mInitTranslationX;

    private int mTranslationX;

    private int mTabVisibleCount;


    private List<String> mTitles;

    public ViewPagerIndicator(Context context) {
        this(context,null);
    }

    public ViewPagerIndicator(Context context,  AttributeSet attrs) {
        super(context, attrs);

        /**
         * 获取自定义属性
         */

       TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);

       mTabVisibleCount = arr.getInt(R.styleable.ViewPagerIndicator_visivle_tab_count,count_default_count);

       if (mTabVisibleCount < 0){
           mTabVisibleCount = count_default_count;
       }

       arr.recycle();

        /**
         * 初始化画笔
         */

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaint.setColor(Color.parseColor("#ffffff"));

        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setPathEffect(new CornerPathEffect(3));


    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        canvas.save();
        canvas.translate(mInitTranslationX+mTranslationX,getHeight() + 2);
        canvas.drawPath(mPath,mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGLE_WIDTH);
        mTriangleWidth = Math.min(mTriangleWidth,dimension_triangle_width_max);
        mInitTranslationX = w / mTabVisibleCount / 2 - mTriangleWidth / 2;
        initTriangle();

    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();

        int count = getChildCount();
        if (count == 0) return;
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth() / mTabVisibleCount;
            view.setLayoutParams(lp);
        }
        clickTextView();
    }
    /**
     * 获取屏幕高度
     */
    private int getScreenWidth(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outM = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outM);
        return outM.widthPixels;
    }

    /**
     * 绘制三角形
     */
    private void initTriangle() {
        mTriangleHeight = mTriangleWidth / 2;
        mPath = new Path();
        mPath.moveTo(0,0);
        mPath.lineTo(mTriangleWidth,0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    /**
     * 指示器根据手指滚动
     * @param position
     * @param positionOffset
     */
    public void scroll(int position, float positionOffset) {
            int tabWidth = getWidth() / mTabVisibleCount;
            mTranslationX = (int) (tabWidth * (positionOffset + position));

            // 容器移动,在tab处于移动至最后一个时
            if (position >= mTabVisibleCount - 2 && positionOffset > 0 && getChildCount() > mTabVisibleCount){
                if (mTabVisibleCount != 1){
                    this.scrollTo((int) (tabWidth * ((position - (mTabVisibleCount - 2))  + positionOffset)),0);
                }else{
                    this.scrollTo(mTranslationX,0);
                }

            }
            heighLightTextView(position);
            invalidate();
    }

    public void setTabTitle(List<String> titles){
        if (titles != null && titles.size() > 0){
            this.removeAllViews();

            mTitles = titles;

            for(String title:mTitles){
                addView(generateTextView(title));
            }
            clickTextView();
        }

    }

    /**
     * 设置可见tab数量
     * @param count
     */
    public void setVisibleTabCount(int count){
        mTabVisibleCount = count;
    }

    /**
     * 根据title创建tab
     * @param title
     * @return
     */
    private View generateTextView(String title) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mTabVisibleCount;
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        tv.setTextColor(color_text_normal);
        tv.setLayoutParams(lp);
        return tv;
    }
    private ViewPager mViewPager;
    public interface PageOnChangeListener{
        void onPageScrolled(int var1, float var2, @Px int var3);

        void onPageSelected(int var1);

        void onPageScrollStateChanged(int var1);
    }

    public PageOnChangeListener mListener;

    public void setOnPageChangeListener(PageOnChangeListener mListener) {
        this.mListener = mListener;
    }


    public void setViewPager(ViewPager viewPager,int position){
        mViewPager = viewPager;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                scroll(position,positionOffset);

                if (mListener != null){
                    mListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int i) {
                if (mListener != null){
                    mListener.onPageSelected(i);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (mListener != null){
                    mListener.onPageScrollStateChanged(i);
                }
            }

        });
        mViewPager.setCurrentItem(position);
        heighLightTextView(position);

    }

    /**
     * 重置文本颜色
     */
    public void resetTextViewColor(){
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView){
                ((TextView) view).setTextColor(color_text_normal);
            }
        }
    }

    /**
     * 高亮选中的tab的文本
     * @param pos
     */
    private void heighLightTextView(int pos){
        resetTextViewColor();
        View view = getChildAt(pos);
        if (view instanceof TextView){
            ((TextView) view).setTextColor(color_text_heighlight);
        }
    }

    /**
     * 设置tab点击事件

     */
    private void clickTextView(){

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final  int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }

    }
}
