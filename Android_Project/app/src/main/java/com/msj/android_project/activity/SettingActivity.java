package com.msj.android_project.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.msj.android_project.R;
import com.msj.android_project.utils.Constants;
import com.msj.android_project.utils.MToast;
import com.msj.android_project.utils.PreferenceUtils;
import com.msj.android_project.utils.ToolsUtil;
import com.msj.android_project.view.FingerprintDialogFragment;
import com.msj.android_project.view.TextImageCell;

import javax.crypto.Cipher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.fingerprintCL)
    TextImageCell fingerprintCL;
    @BindView(R.id.fingerprintTV)
    TextView fingerprintTV;

    @BindView(R.id.gestureLockCL)
    TextImageCell gestureLockCL;
    @BindView(R.id.changeGesture)
    TextView changeGesture;

    private final int SETGESTURELOCK = 100;
    private Context mContext;
    private Boolean isFingerprint, isGesture;
    private Cipher cipher;
    private FingerprintDialogFragment dialogFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mContext = this;
        isFingerprint = PreferenceUtils.getBoolean(Constants.ISFINGERPRINT_KEY, false);
        isGesture = PreferenceUtils.getBoolean(Constants.ISGESTURELOCK_KEY, false);
        if (ToolsUtil.supportFingerprint(this)) {
            /**
             * 生成一个对称加密的key
             */
            ToolsUtil.initKey();

            /**
             * 生成一个Cipher对象
             */
            cipher = ToolsUtil.initCipher();
            if (isFingerprint) {
                fingerprintCL.setImageViewIcon(R.drawable.open);
                fingerprintTV.setVisibility(View.VISIBLE);
            }
        } else {
            fingerprintCL.setVisibility(View.GONE);
        }

        if (isGesture) {
            gestureLockCL.setImageViewIcon(R.drawable.open);
            changeGesture.setVisibility(View.VISIBLE);
        }

        fingerprintCL.setItemClickListener(new TextImageCell.itemClickListener() {
            @Override
            public void itemClick() {
                if (isFingerprint) {
                    showDeleteDialog();
                } else {
                    showFingerPrintDialog(cipher);
                }
            }
        });


        gestureLockCL.setItemClickListener(new TextImageCell.itemClickListener() {
            @Override
            public void itemClick() {
                if (isGesture) {
                    gestureLockCL.setImageViewIcon(R.drawable.close);
                    PreferenceUtils.commitBoolean(Constants.ISGESTURELOCK_KEY, false);
                    changeGesture.setVisibility(View.GONE);
                    isGesture = false;
                } else {
                    startActivityForResult(SetGestureLockActivity.class, null, SETGESTURELOCK);
                }
            }
        });

    }

    @OnClick(R.id.changeGesture)
    public void onViewClicked(View view) {
        Intent intent = new Intent(mContext, GestureLockActivity.class);
        intent.putExtra("type", "change");
        startActivity(intent);

    }
    private void showFingerPrintDialog(Cipher cipher) {
        dialogFragment = new FingerprintDialogFragment();
        dialogFragment.setCipher(cipher);
        dialogFragment.show(getSupportFragmentManager(), "fingerprint");

        dialogFragment.setOnFingerprintSetting(new FingerprintDialogFragment.OnFingerprintSetting() {
            @Override
            public void onFingerprint(boolean isSucceed) {
                if (isSucceed) {
                    isFingerprint = true;
                    PreferenceUtils.commitBoolean(Constants.ISFINGERPRINT_KEY, true);
                    MToast.showToastLong("指纹设置成功！");
                    fingerprintCL.setImageViewIcon(R.drawable.open);
                    fingerprintTV.setVisibility(View.VISIBLE);
                } else {
                    MToast.showToastLong("指纹设置失败！");
                }
            }
        });
    }
    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("南网商旅");
        builder.setMessage("是否关闭指纹登录？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fingerprintCL.setImageViewIcon(R.drawable.open);
                fingerprintTV.setVisibility(View.GONE);
                changeGesture.setVisibility(View.GONE);
                isFingerprint = false;
                PreferenceUtils.commitBoolean(Constants.ISFINGERPRINT_KEY, false);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SETGESTURELOCK:
                    isGesture = true;
                    gestureLockCL.setImageViewIcon(R.drawable.open);
                    changeGesture.setVisibility(View.VISIBLE);
                    PreferenceUtils.commitBoolean(Constants.ISGESTURELOCK_KEY, true);
                    break;
            }
        }
    }
}
