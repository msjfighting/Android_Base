package com.msj.android_project.activity;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.msj.android_project.R;
import com.msj.android_project.view.ZoomImageView;

public class ImageActivity extends BaseActivity {
    private ViewPager viewpager;
    private int[] mImgs = new int[]{R.drawable.guide_image1,R.drawable.guide_image2,R.drawable.guide_image3,R.drawable.guide_image4,R.drawable.guide_image5};
    private ImageView[] mImageViews = new ImageView[mImgs.length];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp);

        initNavBar(true,"好好学习");

        viewpager = findViewById(R.id.id_viewpager);

        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageViews.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
                return view == obj;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {

                ZoomImageView imageView = new ZoomImageView(getApplicationContext());
                imageView.setImageResource(mImgs[position]);

                container.addView(imageView);
                mImageViews[position] = imageView;

                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
               container.removeView(mImageViews[position]);
            }
        });


    }
}
