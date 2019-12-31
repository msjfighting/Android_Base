package com.msj.android_project.activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.msj.android_project.R;
import com.msj.android_project.utils.Constants;
import com.msj.android_project.utils.MToast;
import com.msj.android_project.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FingerActivity extends BaseActivity {
    @BindView(R.id.fingerprintLoginBtn)
    TextView fingerprintLoginBtn;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.fingerprintLoginBtn,R.id.loginBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fingerprintLoginBtn:
                startActivity(SettingActivity.class);
                break;

            case R.id.loginBtn:
                Intent intent = null;
                boolean isFingerprint = PreferenceUtils.getBoolean(Constants.ISFINGERPRINT_KEY, false);
                boolean isGesture = PreferenceUtils.getBoolean(Constants.ISGESTURELOCK_KEY, false);
                if(!isFingerprint && !isGesture) {
                    MToast.showToast("请先设置指纹解锁或手势解锁！");
                }else {
                    intent = new Intent(this,GestureLockActivity.class);
                    intent.putExtra("type","login");
                    startActivity(intent);
                }
                break;
        }
    }
}
