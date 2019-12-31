package activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.example.classtwo.R;
import untils.CacheUtiles;
import untils.DensityUtil;
import untils.UserUtils;

import java.util.ArrayList;

import static activitys.SplashActivity.LOGIN;
import static activitys.SplashActivity.START_MAIN;

public class GuideActivity extends Activity implements View.OnClickListener {
    private ViewPager viewpage;
    private final int[] images = {R.mipmap.guide_1,R.mipmap.guide_2,R.mipmap.guide_3,R.mipmap.guide_1};
    private Button gomian;
    private LinearLayout point_group;
    private ArrayList<ImageView> imageViews;
    private ImageView iv_red_point;
    private static final int padding = 20;
    // 两点的间距
    private int leftMax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initAdapter();
    }
    private void initView() {
        viewpage = findViewById(R.id.viewpage);
        gomian = findViewById(R.id.gomian);
        point_group = findViewById(R.id.point_group);
        iv_red_point = findViewById(R.id.iv_red_point);
        gomian.setOnClickListener(this);
    }
    private void initAdapter() {
        /*
        // 方式一:
        viewpage.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return images.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView iv = new ImageView(GuideActivity.this);
                iv.setBackgroundResource(images[position]);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(iv);
                return iv;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        */
        // 方式二:
        imageViews = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(images[i]);
            imageViews.add(iv);

            // 创建点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            // 单位是像素
            // 把单位当成是dp转成像素
            int widthdpi = DensityUtil.dip2px(this,10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthdpi,widthdpi);
            if (i != 0){
                // 距离左边有20个像素
                params.leftMargin = padding;
            }
            point.setLayoutParams(params);
            //  添加到线性布局里面
            point_group.addView(point);
        }

        // 适配器
        viewpage.setAdapter(new MyPagerAdapter());


        // 根据view生命周期,当视图执行到onLayout或者onDraw的时候,视图的高和宽,边距都有了
        // 当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时,所要调用的回调函数的接口类
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

        // 得到屏幕滑动的百分比
        viewpage.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    public void onClick(View view) {
        CacheUtiles.setBoolean(this,START_MAIN,true);
        boolean isLogin = UserUtils.validateuserLogin(this);
        Intent i = null;
        if (isLogin){
            // 进入主页面
            i = new Intent(this,MainActivity.class);
        }else{
            i = new Intent(this,LoginActivity.class);
        }
        startActivity(i);
        finish();
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        /*
         当页面回调了会调用这个方法
         position : 当前滑动页面的位置
         positionOffset : 页面滑动的百分比
         positionOffsetPixels :  滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            // 两点间移动的距离 = 屏幕滑动百分比 * 间距
            int leftmargin = (int) (positionOffset * leftMax);
            leftmargin = position * leftMax + leftmargin;
            Log.i("TAG","position === "+ position + "  positionOffset == "+positionOffset+"  positionOffsetPixels == " +positionOffsetPixels);

            // 两点间滑动距离对应的坐标 = 原来的起始位置 + 两点间移动的距离
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();
            params.leftMargin = leftmargin;
            iv_red_point.setLayoutParams(params);
        }

        /*
         当页面被选中的时候 执行
         position : 被选中页面的对应位置
         */
        @Override
        public void onPageSelected(int position) {
            if (position == imageViews.size() - 1 ){
                // 最后一个页面显示
                gomian.setVisibility(View.VISIBLE);
            }else{
                gomian.setVisibility(View.GONE);
            }
        }

        /*
         当viewpager页面滑动状态发生变化的时候执行
         position :

         */
        @Override
        public void onPageScrollStateChanged(int position) {

        }
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener{

        @Override
        public void onGlobalLayout() {
            // 执行不止一次
            iv_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(MyOnGlobalLayoutListener.this);
            // 间距
            leftMax = point_group.getChildAt(1).getLeft() - point_group.getChildAt(0).getLeft();
        }
    }

    // 自定义适配器
    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageViews.size();
        }

        /*
        类似于ListView中的
         container : ViewPager
         position : 要创建页面的位置
         返回和创建当前页面有关系的值
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView img = imageViews.get(position);
            // 添加到容器中
            container.addView(img);

             return img;
//            return position ;
        }

        // 判断
        // view 当前视图
        // object 上个instantiateItem返回的
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            // 如果  instantiateItem 中 return position ; 则这样判断
           // return view == imageViews.get(Integer.parseInt((String) object));
            // 如果  instantiateItem 中 return img ; 则这样判断
            return view == object;
        }


        /*
         container : ViewPager
         position : 要销毁的位置
         object : 要销毁的页面
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

    }
}
