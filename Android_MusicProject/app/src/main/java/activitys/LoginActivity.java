package activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.classtwo.R;
import untils.UserUtils;
import views.InputView;

public class LoginActivity extends BaseActivity {

    private InputView login_phone,login_pwd;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }
    private void init() {
        login_phone = fd(R.id.login_phone);
        login_pwd = fd(R.id.login_pwd);

        initNavBar(false,"登录",false);
    }

    /**
     * 跳转到注册页面
     * @param view
     */
    public void onRegister(View view){
        Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(i);

    }

    /**
     * 登录
     * @param view
     */
    public  void onCommit(View view){
        String phone = login_phone.getInputStr();
        String pwd = login_pwd.getInputStr();
        if (UserUtils.validateLogin(this,phone,pwd)){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
