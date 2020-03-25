package com.msj.android_project.activity;

import android.os.Bundle;
import android.view.View;

import com.msj.android_project.R;
import com.msj.android_project.base.BaseDialog;
import com.msj.android_project.base.DialogManager;
import com.msj.android_project.bean.ScreeningResult;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        initNavBar(true, "弹框");
    }

    @OnClick({R.id.id_nomal, R.id.id_not_dismiss_1, R.id.id_not_dismiss_2, R.id.id_not_dismiss_1_2})
    public void onViewClicked(View view) {
        ScreeningResult result = new ScreeningResult();
        result.title = "初筛通过";
        result.result = "true";
        switch (view.getId()) {
            case R.id.id_nomal:
                DialogManager.getInstance().setDialogAttrs().getDialog().show(this,result,null);
                break;
            case R.id.id_not_dismiss_1:
                DialogManager.getInstance().dismissOnTouchOutside(false).setDialogAttrs().getDialog().show(this,result,null);
                break;
            case R.id.id_not_dismiss_2:
                DialogManager.getInstance().dismissOnBackPressed(false).setDialogAttrs().getDialog().show(this,result,null);
                break;
            case R.id.id_not_dismiss_1_2:
                DialogManager.getInstance().dismissOnBackPressed(false).dismissOnTouchOutside(false).setDialogAttrs().getDialog().show(this,result,null);
                break;
        }
    }
}
