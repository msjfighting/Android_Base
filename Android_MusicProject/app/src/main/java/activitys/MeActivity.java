package activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.classtwo.R;
import helps.UserHelper;
import untils.UserUtils;

public class MeActivity extends BaseActivity {
    TextView user_name;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        initNavBar(true,"个人中心",false);

        user_name = findViewById(R.id.user_name);

        user_name.setText("用户名: "+ UserHelper.getInstance().getPhone());
    }


    public void onChangeClick(View view){
        Intent i = new Intent(MeActivity.this,ChangePassword.class);
        startActivity(i);
    }

    public void loginOut(View view){
        UserUtils.logout(this);
    }


}
