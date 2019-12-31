package com.msj.android_project.activity;

import android.content.Context;
import android.os.Handler;
import androidx.constraintlayout.widget.Group;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.msj.android_project.R;
import com.msj.android_project.gesture.GestureLockLayout;
import com.msj.android_project.utils.Constants;
import com.msj.android_project.utils.MToast;
import com.msj.android_project.utils.PreferenceUtils;
import com.msj.android_project.utils.ToolsUtil;
import com.msj.android_project.view.FingerprintDialogFragment;

import javax.crypto.Cipher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GestureLockActivity extends BaseActivity {
    @BindView(R.id.gestureLock)
    GestureLockLayout mGestureLockLayout;
    @BindView(R.id.hintTV)
    TextView hintTV;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.group)
    Group group;
    private Context mContext;
    private Animation animation;

    /**
     * 最大解锁次数
     */
    private int mNumber = 5;
    /**
     * change:修改手势  login:登录
     */
    private String type;

    /**
     * true:设置   false:未设置
     */
    private Boolean isFingerprint, isGesture;

    private FingerprintDialogFragment dialogFragment;
    private Cipher cipher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_lock);
        ButterKnife.bind(this);
        initView();
    }

    protected void initView() {
        mContext = this;
        type = getIntent().getStringExtra("type");
        isGesture = PreferenceUtils.getBoolean(Constants.ISGESTURELOCK_KEY, false);
        isFingerprint = PreferenceUtils.getBoolean(Constants.ISFINGERPRINT_KEY, false);
        if (isGesture) {
            group.setVisibility(View.VISIBLE);
            setGestureListener();
        }

        if ("login".equals(type) && isFingerprint) {
            setFingerprint();
        }

    }

    private void setFingerprint() {
        if (ToolsUtil.supportFingerprint(this)) {
            ToolsUtil.initKey(); //生成一个对称加密的key
            //生成一个Cipher对象
            cipher = ToolsUtil.initCipher();
        }
        if (cipher != null) {
            showFingerPrintDialog(cipher);
        }
    }

    private void showFingerPrintDialog(Cipher cipher) {
        dialogFragment = new FingerprintDialogFragment();
        dialogFragment.setCipher(cipher);
        dialogFragment.show(getSupportFragmentManager(), "fingerprint");

        dialogFragment.setOnFingerprintSetting(new FingerprintDialogFragment.OnFingerprintSetting() {
            @Override
            public void onFingerprint(boolean isSucceed) {
                if (isSucceed) {
                    MToast.showToastLong("指纹解锁成功！");
                    startActivity(MainActivity.class);
                    finish();
                } else {
                    MToast.showToastLong("指纹解锁失败！");
                }
            }
        });
    }

    private void setGestureListener() {
        String gestureLockPwd = PreferenceUtils.getString(Constants.GESTURELOCK_KEY, "");
        if (!TextUtils.isEmpty(gestureLockPwd)) {
            mGestureLockLayout.setAnswer(gestureLockPwd);
        } else {
            MToast.showToast("没有设置过手势密码");
        }
        mGestureLockLayout.setDotCount(3);
        mGestureLockLayout.setMode(GestureLockLayout.VERIFY_MODE);
        //设置手势解锁最大尝试次数 默认 5
        mGestureLockLayout.setTryTimes(5);
        animation = AnimationUtils.loadAnimation(this, R.anim.shake);
        mGestureLockLayout.setOnLockVerifyListener(new GestureLockLayout.OnLockVerifyListener() {
            @Override
            public void onGestureSelected(int id) {
                //每选中一个点时调用
            }

            @Override
            public void onGestureFinished(boolean isMatched) {
                //绘制手势解锁完成时调用
                if (isMatched) {
                    if ("change".equals(type)) {
                        startActivity(SetGestureLockActivity.class);
                    } else if ("login".equals(type)) {
                        startActivity(MainActivity.class);
                    }
                    finish();
                } else {
                    hintTV.setVisibility(View.VISIBLE);
                    mNumber = --mNumber;
                    hintTV.setText("你还有" + mNumber + "次机会");
                    hintTV.startAnimation(animation);
                    mGestureLockLayout.startAnimation(animation);
                    ToolsUtil.setVibrate(mContext);
                }
                resetGesture();
            }

            @Override
            public void onGestureTryTimesBoundary() {
                //超出最大尝试次数时调用
                mGestureLockLayout.setTouchable(false);
            }
        });
    }

    /**
     * 重置手势布局（只是布局）
     */
    private void resetGesture() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mGestureLockLayout.resetGesture();
            }
        }, 300);
    }

}
