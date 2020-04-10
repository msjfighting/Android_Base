package com.msj.android_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.msj.android_project.mmodel.MVCModel;
import com.msj.android_project.mmodel.MVP;
import com.msj.android_project.mmodel.MVPPresenter;
import com.msj.android_project.R;
import com.msj.android_project.bean.Account;
import com.msj.android_project.callback.MCallback;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArchitectureActivity extends BaseActivity implements MVP {

    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.content)
    TextView content;

    private MVCModel mvcModel;

    private MVPPresenter mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_architecture);
        ButterKnife.bind(this);
        initNavBar(true, "架构小能手");
        mvcModel = new MVCModel();

        mvpPresenter = new MVPPresenter(this);
    }

    private String getUserInfo() {
        return edit.getText().toString();
    }

    private void showSuccess(Account account) {
        content.setText("用户账户:" + account.getName() + "用户等级" + account.getLevel());
    }

    private void showfailed() {
        content.setText("获取数据失败");
    }

    private void getAccountDataNormal(String name, MCallback callback) {
        Random random = new Random();
        boolean isSuccess = random.nextBoolean();
        if (isSuccess) {
            Account account = new Account();
            account.setName(name);
            account.setLevel(random.nextInt(100));
            callback.onSuccess(account);
        } else {
            callback.onFailed();
        }
    }


    @OnClick({R.id.queryNormalBtn, R.id.queryMVCBtn, R.id.queryMVPBtn, R.id.queryMVVMBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.queryNormalBtn:
                getAccountDataNormal(getUserInfo(), new MCallback() {
                    @Override
                    public void onSuccess(Account account) {
                        showSuccess(account);
                    }
                    @Override
                    public void onFailed() {
                        showfailed();
                    }
                });
                break;
            case R.id.queryMVCBtn:
                mvcModel.getAccountData(getUserInfo(), new MCallback() {
                    @Override
                    public void onSuccess(Account account) {
                        showSuccess(account);
                    }
                    @Override
                    public void onFailed() {
                        showfailed();
                    }
                });
                break;
            case R.id.queryMVPBtn:
                mvpPresenter.getData();
                break;
            case R.id.queryMVVMBtn:

                // 启用dataBinding
                // 布局改为dataBinding
                Intent i = new Intent(this, MvvmActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public String getUserInfoMVP() {
        return edit.getText().toString();
    }

    @Override
    public void showSuccessMVP(Account account) {
        content.setText("用户账户:" + account.getName() + "用户等级" + account.getLevel());
    }

    @Override
    public void showfailedMVP() {
        content.setText("获取数据失败");
    }
}
