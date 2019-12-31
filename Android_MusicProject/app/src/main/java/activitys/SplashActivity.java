package activitys;

import activitys.GuideActivity;
import activitys.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.classtwo.R;
import untils.CacheUtiles;
import untils.UserUtils;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    public static final String START_MAIN = "start_main";
    public static final String LOGIN = "isLogin";

    private Timer myTimer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        ImageView imagev = findViewById(R.id.imagev);

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 主线程
                jumpToMain();
            }
        },1000);
      /*
        imagev.postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToMain();
            }
        },1500);
        */
    }

    private void jumpToMain() {

        // 判断是否进入过主页面
        Intent i = null;
        boolean isSatrtMain = CacheUtiles.getBoolean(this, START_MAIN);
        boolean isLogin = UserUtils.validateuserLogin(this);
        if (isSatrtMain){
            if (isLogin){
                // 已经登录过 进入过主页面
                i = new Intent(this, MainActivity.class);
            }else{
                i = new Intent(this, LoginActivity.class);
            }

        }else {
            // 进去引导页
            i = new Intent(this, GuideActivity.class);
        }
        startActivity(i);
        finish();
    }
}
