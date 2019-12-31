package activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.classtwo.R;

public class BaseActivity extends Activity implements View.OnClickListener {
    private ImageView navbar_left,navbar_right;
    private TextView navbar_title;

    /*
       findViewById()
     */
    protected <T extends View> T fd(@IdRes int id){
        return findViewById(id);
    }

    /*
     初始化navbar

     */
    protected  void initNavBar(boolean isShowBack, String title, boolean isShowMe){
        navbar_left = fd(R.id.navbar_left);
        navbar_right = fd(R.id.navbar_right);
        navbar_title = fd(R.id.navbar_title);

        navbar_left.setVisibility(isShowBack ? View.VISIBLE : View.GONE);

        navbar_right.setVisibility(isShowMe ? View.VISIBLE : View.GONE);

        navbar_title.setText(title);

        navbar_left.setOnClickListener(this);

        navbar_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.navbar_left:{
                onBackPressed();
            }
                break;
            case R.id.navbar_right:{
                startActivity(new Intent(this,MeActivity.class));
            }
            break;
        }
    }
}
