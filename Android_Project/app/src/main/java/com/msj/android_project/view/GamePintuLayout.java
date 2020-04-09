package com.msj.android_project.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.msj.android_project.R;
import com.msj.android_project.utils.ImagePiece;
import com.msj.android_project.utils.ImageSplitterUtil;
import com.msj.android_project.utils.ToolsUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class GamePintuLayout extends RelativeLayout implements View.OnClickListener {


    private int mColumn = 3;

    /**
     * 容器的内边距
     */
    private int mPadding;

    /**
     * 每张小图之间的距离(横,纵)(dp)
     */
    private int mMagin = 3;

    private ImageView[] mGamePintuItems;

    private int mItemWidth;

    /**
     * 游戏图片
     */
    private Bitmap mBitmap;

    private List<ImagePiece> mItemBitmaps;

    private boolean once;
    /**
     * 游戏面板宽度
     */
    private int mWidth;

    private boolean isGameSuccess;
    private boolean isGameOver;

    public interface GamePintuListener {
        void nextLevel(int nextLevel);

        void timeChangeed(int currentTime);

        void gameOver();
    }

    public GamePintuListener mListener;

    public void setOnGamePintuListener(GamePintuListener mListener) {
        this.mListener = mListener;
    }

    private static final int TIME_CHANGED = 0x110;

    private static final int NEXT_LEVEL = 0x111;

    private int mLevel = 1;

    private int mDuration = 60;

    /**
     * 设置游戏时长
     * @param duration
     */
    public void setDuration(int duration) {
        mDuration = duration;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME_CHANGED:
                    if (isGameSuccess || isGameOver || isPause) return;
                    if (mListener != null) {
                        mListener.timeChangeed(mTime);
                    }
                    if (mTime == 0) {
                        isGameOver = true;
                        mListener.gameOver();
                        return;
                    }
                    mTime--;
                    mHandler.sendEmptyMessageDelayed(TIME_CHANGED, 1000);
                    break;

                case NEXT_LEVEL:
                    mLevel = mLevel + 1;
                    if (mListener != null) {
                        mListener.nextLevel(mLevel);
                    } else {
                        nextLevel();
                    }
                    break;

                default:
                    break;
            }
        }
    };

    public boolean isTimeEnabled = false;
    private int mTime;

    /**
     * 设置是否开启时间限制
     *
     * @param timeEnabled
     */
    public void setTimeEnabled(boolean timeEnabled) {
        isTimeEnabled = timeEnabled;
    }


    public GamePintuLayout(Context context) {
        this(context, null);
    }

    public GamePintuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GamePintuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public GamePintuLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init() {
        mMagin = ToolsUtil.px2dip(getContext(), 3);
        mPadding = min(getPaddingLeft(), getPaddingRight(), getPaddingTop(), getPaddingBottom());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 取宽高中的小值
        mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
        if (!once) {
            // 进行切图和排序
            initBitmap();

            // 设置imageView的宽高
            initItem();

            // 判断是否开启时间

            checkTimeEnabled();

            once = true;
        }
        setMeasuredDimension(mWidth, mWidth);

    }


    /**
     * 判断是否开启时间
     */
    private void checkTimeEnabled() {
        if (isTimeEnabled) {
            //根据当前等级设置时间
            countTimeBaseLevel();
            mHandler.sendEmptyMessage(TIME_CHANGED);
        }
    }

    /**
     * 根据当前等级设置时间
     */
    private void countTimeBaseLevel() {
        mTime = (int) (Math.pow(2, mLevel) * mDuration);
    }

    /**
     * 进行切图和排序
     */
    private void initBitmap() {

        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        }
        mItemBitmaps = ImageSplitterUtil.splitImage(mBitmap, mColumn);

        // 将图片乱序
        Collections.sort(mItemBitmaps, new Comparator<ImagePiece>() {
            @Override
            public int compare(ImagePiece a, ImagePiece b) {
                return Math.random() > 0.5 ? 1 : -1;
            }
        });
    }


    /**
     * 设置imageView的宽高
     * 0 1 2
     * 3 4 5
     * 6 7 8
     */
    private void initItem() {

        mItemWidth = (mWidth - mPadding * 2 - mMagin * (mColumn - 1)) / mColumn;

        mGamePintuItems = new ImageView[mColumn * mColumn];

        // 生成我们的item,设置Rule
        for (int i = 0; i < mGamePintuItems.length; i++) {
            ImageView item = new ImageView(getContext());
            item.setOnClickListener(this);
            item.setImageBitmap(mItemBitmaps.get(i).getBitmap());
            mGamePintuItems[i] = item;
            item.setId(i + 1);
            // 在item的tag中存储index,拼图是否成功的验证
            item.setTag(i + "_" + mItemBitmaps.get(i).getIndex());

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mItemWidth, mItemWidth);

            //设置item间横向间隙 rightMargin
            // 不是最后一列
            if ((i + 1) % mColumn != 0) {
                lp.rightMargin = mMagin;
            }

            //设置item 布局rightOf
            // 不是第一列

            if (i % mColumn != 0) {
                // 布局rightOf
                lp.addRule(RelativeLayout.RIGHT_OF, mGamePintuItems[i - 1].getId());
            }

            // 如果不是第一行,添加topMagin与below
            if ((i + 1) > mColumn) {
                lp.topMargin = mMagin;
                lp.addRule(RelativeLayout.BELOW, mGamePintuItems[i - mColumn].getId());
            }
            addView(item, lp);
        }
    }


    /**
     * 获取多个参数的最小值
     */
    private int min(int... params) {
        int min = params[0];
        for (int param : params) {
            if (param < min) {
                min = param;
            }
        }
        return min;
    }


    private ImageView mFirst;
    private ImageView mSecond;
    private boolean isAniming;

    @Override
    public void onClick(View v) {
        if (isAniming) return;
        // 两次点击同一个item
        if (mFirst == v) {
            mFirst.setColorFilter(null);
            mFirst = null;
            return;
        }
        if (mFirst == null) {
            mFirst = (ImageView) v;
            mFirst.setColorFilter(Color.parseColor("#55ff0000"));
        } else {
            mSecond = (ImageView) v;
            // 交换选中图片
            exchangeView();
        }


    }

    /**
     * 动画层
     */
    private RelativeLayout mAnimLayout;

    /**
     * 交换选中图片
     */
    private void exchangeView() {
        mFirst.setColorFilter(null);

        // 构造动画层
        setUpAnimLayout();


        String firstTag = (String) mFirst.getTag();
        String secondTag = (String) mSecond.getTag();

        Bitmap firstBitmap = mItemBitmaps.get(getImageIdByTag(firstTag)).getBitmap();

        Bitmap secBitmap = mItemBitmaps.get(getImageIdByTag(secondTag)).getBitmap();

        ImageView firstV = new ImageView(getContext());
        firstV.setImageBitmap(firstBitmap);

        LayoutParams lp = new RelativeLayout.LayoutParams(mItemWidth, mItemWidth);
        lp.leftMargin = mFirst.getLeft() - mPadding;
        lp.topMargin = mFirst.getTop() - mPadding;
        firstV.setLayoutParams(lp);
        mAnimLayout.addView(firstV);


        ImageView secondV = new ImageView(getContext());
        secondV.setImageBitmap(secBitmap);
        LayoutParams lp2 = new RelativeLayout.LayoutParams(mItemWidth, mItemWidth);
        lp2.leftMargin = mSecond.getLeft() - mPadding;
        lp2.topMargin = mSecond.getTop() - mPadding;
        secondV.setLayoutParams(lp2);
        mAnimLayout.addView(secondV);


        // 设置动画
        TranslateAnimation animation = new TranslateAnimation(0,
                mSecond.getLeft() - mFirst.getLeft(),
                0,
                mSecond.getTop() - mFirst.getTop());

        animation.setDuration(300);
        animation.setFillAfter(true);
        firstV.startAnimation(animation);


        TranslateAnimation animation2 = new TranslateAnimation(0,
                -mSecond.getLeft() + mFirst.getLeft(),
                0,
                -mSecond.getTop() + mFirst.getTop());

        animation2.setDuration(300);
        animation2.setFillAfter(true);
        secondV.startAnimation(animation2);


        // 监听动画
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mFirst.setVisibility(View.INVISIBLE);
                mSecond.setVisibility(View.INVISIBLE);
                isAniming = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFirst.setImageBitmap(secBitmap);
                mSecond.setImageBitmap(firstBitmap);

                mFirst.setTag(secondTag);
                mSecond.setTag(firstTag);

                mFirst.setVisibility(View.VISIBLE);
                mSecond.setVisibility(View.VISIBLE);

                mFirst = mSecond = null;
                // 移除动画层上的view
                mAnimLayout.removeAllViews();
                // 判断游戏是否成功
                checkSuccess();
                isAniming = false;
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    /**
     * 判断游戏是否成功
     */
    private void checkSuccess() {
        boolean isSuccess = true;

        for (int i = 0; i < mGamePintuItems.length; i++) {
            ImageView imageView = mGamePintuItems[i];
            if (getImageIndex(String.valueOf(imageView.getTag())) != i) {
                isSuccess = false;
                break;
            }
        }

        if (isSuccess) {
            isGameSuccess = true;
            mHandler.removeMessages(TIME_CHANGED);
            mHandler.sendEmptyMessage(NEXT_LEVEL);
        }
    }

    /**
     * 根据tag获取id
     *
     * @param tag
     * @return
     */
    private int getImageIdByTag(String tag) {
        String[] s = tag.split("_");
        return Integer.parseInt(s[0]);
    }

    /**
     * 根据tag获取 拼图的index
     *
     * @param tag
     * @return
     */
    private int getImageIndex(String tag) {
        String[] s = tag.split("_");
        return Integer.parseInt(s[1]);
    }

    /**
     * 时间结束,重新开始定时开始游戏
     */
    public void restart() {
        //根据当前等级设置时间
        isGameOver = false;
        isGameSuccess = false;
        countTimeBaseLevel();
        mHandler.sendEmptyMessage(TIME_CHANGED);
    }

    private boolean isPause;

    public void pause(){
        isPause = true;
        mHandler.removeMessages(TIME_CHANGED);
    }

    public void resume(){
        if (isPause){
            isPause = false;
            mHandler.sendEmptyMessage(TIME_CHANGED);
        }
    }

    public void nextLevel() {
        this.removeAllViews();
        mAnimLayout = null;
        mColumn++;
        isGameSuccess = false;

        // 下一关重新开始计时
        checkTimeEnabled();
        initBitmap();
        initItem();
    }


    /**
     * 构造动画层
     */
    private void setUpAnimLayout() {
        if (mAnimLayout == null) {
            mAnimLayout = new RelativeLayout(getContext());
            addView(mAnimLayout);
        }
    }
}
