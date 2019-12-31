package activitys;

import android.os.Bundle;
import android.view.View;
import com.example.classtwo.R;
import untils.UserUtils;
import views.InputView;

public class ChangePassword extends BaseActivity {
    private InputView old,change_new_pwd1,change_new_pwd2;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        initNavBar(true,"修改密码",false);

        old = fd(R.id.change_pwd);
        change_new_pwd1 = fd(R.id.change_new_pwd1);
        change_new_pwd2 = fd(R.id.change_new_pwd2);

    }

    public void onChangePassword(View view){
        String oldStr = old.getInputStr();
        String pwd1Str = change_new_pwd1.getInputStr();
        String pwd2Str = change_new_pwd2.getInputStr();
        if(UserUtils.validateChangePassword(this,oldStr,pwd1Str,pwd2Str)){
              UserUtils.logout(this);
        }
    }
}
