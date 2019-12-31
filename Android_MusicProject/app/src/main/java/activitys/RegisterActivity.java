package activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.classtwo.R;
import untils.CacheUtiles;
import untils.UserUtils;
import views.InputView;

import static activitys.SplashActivity.LOGIN;

public class RegisterActivity extends BaseActivity {
    private InputView phone,pwd1,pwd2;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

    }

    private void initView() {
        initNavBar(true,"注册",false);

        phone = fd(R.id.register_phone);
        pwd1 = fd(R.id.register_pwd1);
        pwd2 = fd(R.id.register_pwd2);
    }

    public void onRegister(View view){
        String phoneStr = phone.getInputStr();
        String pwd1Str = pwd1.getInputStr();
        String pwd2Str = pwd2.getInputStr();
        // 手机号,密码的合法性
        boolean isRight = UserUtils.validateRegister(this,phoneStr,pwd1Str,pwd2Str);
        if (!isRight) return;
         onBackPressed();
    }
}
