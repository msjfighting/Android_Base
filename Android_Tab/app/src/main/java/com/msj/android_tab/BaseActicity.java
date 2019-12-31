package com.msj.android_tab;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class BaseActicity extends AppCompatActivity {
    private TextView navbar_title,tv_back;
    protected <T extends View> T fd(@IdRes int id){
        return findViewById(id);
    }

    public void initNavTab(boolean isShowBack, String title){
       tv_back = fd(R.id.tv_back);
       navbar_title = fd(R.id.tv_title);

       tv_back.setVisibility(isShowBack ? View.VISIBLE : View.GONE);

       navbar_title.setText(title);

       tv_back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });
    }
}
