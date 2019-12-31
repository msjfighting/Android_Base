package com.msj.android_http;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.msj.android_http.adapters.CardFragmentPageAdapter;
import com.msj.android_http.beans.CarInfo;
import com.msj.android_http.beans.QuestionBeanInfo;
import com.msj.android_http.fragment.CardFragment;
import com.msj.android_http.presenters.TestPresenter;
import com.msj.android_http.transform.CardTransformer;
import com.msj.android_http.transform.LoopTransformer;
import com.msj.android_http.view.EmotionRainView;
import com.msj.android_http.view.ITestView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends FragmentActivity implements ITestView {
    private ViewPager viewPager;
    private EmotionRainView rainView;
    private CardFragmentPageAdapter mAdpter;
    private List<QuestionBeanInfo> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpage);
        rainView = findViewById(R.id.rain);


        TestPresenter presenter = new TestPresenter(this);

        presenter.getData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rainView != null){
            rainView.stopRain();
        }
    }

    public void startRain(){

        rainView.startRain(getBitmap());

    }

    private List<Bitmap> getBitmap(){
        List<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeResource(getResources(),R.mipmap.pic1));

        bitmaps.add(BitmapFactory.decodeResource(getResources(),R.mipmap.pic2));

        bitmaps.add(BitmapFactory.decodeResource(getResources(),R.mipmap.pic3));
        return bitmaps;
    }

    @Override
    public void updateUI(List<CarInfo> list,List<String> answers) {
        Collections.reverse(list);
        mAdpter = new CardFragmentPageAdapter(getSupportFragmentManager(),list,answers);
        viewPager.setAdapter(mAdpter);
        // 预加载后3个页面
        viewPager.setOffscreenPageLimit(3);
        // 一屏内显示多页
//        viewPager.setPageMargin(20);
        viewPager.setCurrentItem(0, false);
        viewPager.setPageTransformer(true,new CardTransformer());
    }

//    @Override
//    public void setBottomtipView(String count) {
//        tv_bottom_text.setText("恭喜你!已累计回答正确"+count+"题");
//    }
}
