package com.msj.android_project.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener , View.OnTouchListener {

    private boolean mOnce;

    /**
     * 初始化时缩放的值
     */
    private float mInitScale;

    /**
     * 中等缩放比例
     */
    private float mMidScale;

    /**
     * 最小缩放比例
     */
    private float mMinScale;


    /**
     * 放大的最大值
     */
    private float mMaxScale;

    private Matrix mScaleMatrix;
    /**
     * 捕获用户多指触控时缩放比例
     */
    private ScaleGestureDetector mScaleGestureDetector;

    //---------------------------自由移动------------------------------
    /**
     * 记录上次多点触控的数量
     */
    private int mLastpPointCount;

    /**
     *
     */
    private float mLastX;
    private float mLastY;
    private int mTouchSlop;
    private boolean isCanDrag;
    private  boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    //---------------------------双击放大缩小------------------------------

    private GestureDetector mGestureDetector;
    private boolean isAutoScale;

    public ZoomImageView(Context context) {
        super(context);
        initView(context);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context){

        // 图片的矩阵
        mScaleMatrix = new Matrix();

        // 在这里设置是屏蔽掉xml文件中设置的缩放模式
        setScaleType(ScaleType.MATRIX);

        // 监听多指缩放手势
        mScaleGestureDetector = new ScaleGestureDetector(context,this);
        // 监听触摸事件
        setOnTouchListener(this);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        // 监听双击缩放
        mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {

                if (isAutoScale) return true;

                float x = e.getX();
                float y = e.getY();
                if (mMinScale <= getScale() && getScale() < mMidScale){
                    postDelayed(new AutoScaleRunnable(mMidScale,x,y),16);

                }else if(mMidScale <= getScale() && getScale() < mMaxScale){
                    postDelayed(new AutoScaleRunnable(mMaxScale,x,y),16);

                }else{
                    postDelayed(new AutoScaleRunnable(mInitScale,x,y),16);

                }
                return true;
            }

            /*
             * 如果当前图片是在一个Activity的ViewPager中打开的，单击可以关闭图片
             */
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                ViewParent parent = getParent();
//                if (parent instanceof ViewPager) {
//                    Context context = ((ViewPager) parent).getContext();
//                    if (context instanceof Activity) {
//                        ((Activity) context).finish();
//                    }
//                }
//
//                return true;
//            }
        });
    }

    /**
     * 双击缩放图片时，使图片缩放产生动画效果的内部类
     */
    private class AutoScaleRunnable implements Runnable{
        /**
         * 缩放的目标值
         */
        private float mTargetScale;
        private float x;
        private float y;

        private final float BIGGER = 1.07f;
        private final float SMALL = 0.93f;

        private float tmpScale;

        public AutoScaleRunnable (float mTargetScale,float x,float y){
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;

            if (getScale() < mTargetScale){
                tmpScale = BIGGER;
            }
            if (getScale() > mTargetScale){
                tmpScale = SMALL;
            }
        }
        @Override
        public void run() {
            isAutoScale = true;

            mScaleMatrix.postScale(tmpScale,tmpScale,x,y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            // 缩放之后判断当前图片是否已达到最终需要缩放的大小
            float currentScale = getScale();

            if ((tmpScale > 1.0f && currentScale < mTargetScale) // 正在放大，且未达到目标
                    || (tmpScale < 1.0f && currentScale > mTargetScale) ){ // 正在缩小且未达到目标
                postDelayed(this,16);
            }else {
                float scale = mTargetScale / currentScale;

                mScaleMatrix.postScale(scale,scale,x,y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    /**
     * 获取imageView加载完成的图片
     */
    @Override
    public void onGlobalLayout() {
        if (!mOnce){

            int width = getWidth();
            int height = getHeight();

           Drawable d = getDrawable();
           if ( d == null) {
               return;
           }
          int dw = d.getIntrinsicWidth();
          int dh = d.getIntrinsicHeight();

          float scale = 1.0f;
          // 如果图片的宽度大于控件宽度,但是高度小于控件高度 我们将其缩小
          if (dw > width && dh < height){
              scale = width * 1.0f / dw;
          }
            // 如果图片的高度大于控件高度,但是宽度小于控件宽度 我们将其缩小
          if (dh > height && dw < width){
              scale = height *1.0f / dh;
          }

          if ((dw > width && dh > height) || (dw < width && dh < height)){
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
          }

          // 设置初始、中等、最大缩放比例

          mInitScale = scale;

          mMinScale = mInitScale * 0.8f;

          mMaxScale = mInitScale * 4;

          mMidScale = mInitScale * 2;

          // 移动图片至中心位置(当前控件中心)
            float dx = getWidth() / 2 - dw / 2;

            // 长图片从头开始查看，所以偏移为0
            float dy = getHeight() > dh * mInitScale ? getHeight() / 2 - dh / 2 : 0;


            mScaleMatrix.postTranslate(dx,dy);
            // 长图片Y轴的缩放位置不在中心，而在顶部
            mScaleMatrix.postScale(mInitScale,mInitScale,width / 2
                    ,getHeight() > dh * mInitScale ? getHeight() / 2 : 0);
            setImageMatrix(mScaleMatrix);

            mOnce = true;
        }
    }

    /**
     *  获取当前图片的缩放值
     * @return
     */
    public float getScale(){
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        float scale = getScale();// 当前图片缩放比例
        float scaleFactor = scaleGestureDetector.getScaleFactor(); // 手势缩放因素
        if (getDrawable() == null) {
            return true;
        }
        /*
         * 控制缩放范围：
         * 放大手势——scaleFactor > 1.0f；
         * 缩小手势——scaleFactor < 1.0f；
         *
         * matrix.postScale()方法是按照已经缩放过的图片，再去进行一次缩放的。
         * 之前如果已经调用了postScale(scale, scale)，那么图片宽高就已经缩放了scale个系数，
         * 再调用postScale(scaleFactor, scaleFactor)，就会在scale系数的基础上缩放scaleFactor个系数，
         * 除以currentScale这个参数，就是为了将之前已经缩放过的scale个系数给抵消掉。
         */
        if ((scale < mMaxScale && scaleFactor > 1.0f)
                || (scale > mMinScale && scaleFactor < 1.0f)){
             if (scale * scaleFactor < mMinScale){
                 scaleFactor = mMinScale / scale;
             }
             if (scale *scaleFactor > mMaxScale){
                 scaleFactor = mMaxScale / scale;
             }

             mScaleMatrix.postScale(scaleFactor,scaleFactor
                     ,scaleGestureDetector.getFocusX(),scaleGestureDetector.getFocusY());

             // 缩放时,进行边界检测 防止出现白边
             checkBorderAndCenterWhenScale();

             setImageMatrix(mScaleMatrix);
        }
        return true;
    }

    /**
     * 获取图片方法缩小以后的宽高,left top right bottom
     * @return
     */
    private RectF getMatrixRectF(){
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();

        Drawable drawable = getDrawable();
        if (drawable != null){
            rectF.set(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    /**
     * 在缩放时进行边界控制以及我们位置的控制
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();

        float deltaX = 0;
        float deltaY = 0;

        float width = getWidth();
        float height = getHeight();

        // 缩放时,进行边界检测 防止出现白边
        if (rectF.width() >= width){ // 图片宽度大于控件宽度
            if (rectF.left > 0){ // 左边留有空白
                deltaX =  -rectF.left;
            }
            if (rectF.right < width){ // 右边留有空白
                deltaX = width -rectF.right;
            }
        }

        if (rectF.height() >= height){
            if (rectF.top > 0){
                deltaY =  -rectF.top;
            }
            if (rectF.bottom < height){
                deltaY = height -rectF.bottom;
            }
        }
        // 如果宽度和高度小于控件的宽或高,则让其居中
        if (rectF.width() < width){
            deltaX = width / 2 - rectF.right + rectF.width() / 2;
        }
        if (rectF.height() < height){
            deltaY = height / 2 - rectF.bottom + rectF.height() / 2;
        }
        mScaleMatrix.postTranslate(deltaX,deltaY);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {

        return true;// 这里return true才能识别缩放手势
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (mGestureDetector.onTouchEvent(motionEvent)) return

         mGestureDetector.onTouchEvent(motionEvent);
         mScaleGestureDetector.onTouchEvent(motionEvent);

        float x = 0; // 多点触控X中心
        float y = 0; // 多点触控Y中心

       int pointCount = motionEvent.getPointerCount();

        // 计算X、Y轴的手势中心
       for (int i = 0; i < pointCount; i++) {
           x += motionEvent.getX(i);
           y += motionEvent.getY(i);
        }

        x /= pointCount;
        y /= pointCount;

        // 手指数量发生改变，重新设置参数
        if (mLastpPointCount != pointCount){
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        mLastpPointCount = pointCount;
        RectF rectF = getMatrixRectF();

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_MOVE:
                // 兼容与ViewPager 滑动的冲突
                if (rectF.width() > getWidth() + 0.01 || rectF.height() > getHeight() + 0.01 ){
                    if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(true);
                    if (rectF.right == getWidth()
                            || rectF.left == 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                // 获取当前缩放中心和上一次缩放中心的差值
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (!isCanDrag){
                    isCanDrag = isMoveAction(dx,dy);
                }
                if (isCanDrag){
                   if (getDrawable() != null){
                       isCheckLeftAndRight = isCheckTopAndBottom = true;
                       // 如果宽度小于控件宽度,不允许横向移动
                       if (rectF.width() < getWidth()){
                           isCheckLeftAndRight = false;
                           dx = 0;
                       }
                       // 如果高度小于控件高度,不允许纵向移动
                       if (rectF.height() < getHeight()){
                           isCheckTopAndBottom = false;
                           dy = 0;
                       }
                       mScaleMatrix.postTranslate(dx,dy);
                       checkBorderAndCenterWhenScale();
                       setImageMatrix(mScaleMatrix);
                   }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_DOWN:

               if (rectF.width() > getWidth() + 0.01 || rectF.height() > getHeight() + 0.01){
                   if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(true);
               }
                break;
            case MotionEvent.ACTION_CANCEL:
                mLastpPointCount = 0;
                // 这里放大了一个肉眼看不出的数值：
                //
                // 在ACTION_MOVE中，如果图片的left或者right与控件边界相等，则不能移动图片，
                // 而是响应ViewPager的操作，这不是下一次操作所希望的；
                //
                // 缩放一个极小的数值，让图片的left、right与控件边界不等，使得可以重新进行移动图片。
                mScaleMatrix.postScale(1.00001f,1.000001f,getWidth() / 2,getHeight() / 2);
                setImageMatrix(mScaleMatrix);
                break;
        }
        return true;
    }

    /**
     * 在移动时进行边界控制
     */
    private void checkBorderAndCenterWhenTranslate() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        float width = getWidth();
        float height = getHeight();

        // 图片高度大于控件高度
        if (rectF.top > 0 && isCheckTopAndBottom){
            // 上边留有空白
            deltaY = -rectF.top;
        }
        if (rectF.bottom < height && isCheckTopAndBottom){
            // 下边留有空白
            deltaY = height - rectF.bottom;
        }

        if (rectF.left > 0 && isCheckLeftAndRight){
            // 左边留白 图片宽度大于控件宽度
            deltaX = -rectF.left;
        }
        if (rectF.right < width && isCheckLeftAndRight){
            // 右边留白
            deltaX = width - rectF.right;
        }
        // 图片宽度小于控件宽度，居中显示
        if (!isCheckLeftAndRight){
            deltaX = width / 2 - rectF.right + rectF.width()/2;
        }

        // 图片高度小于控件高度，居中显示
        if (!isCheckTopAndBottom){
            deltaY = height / 2 - rectF.bottom + rectF.height()/2;
        }

        mScaleMatrix.postTranslate(deltaX,deltaY);
    }

    /**
     * 判断是否move
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {

        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;

    }
}
