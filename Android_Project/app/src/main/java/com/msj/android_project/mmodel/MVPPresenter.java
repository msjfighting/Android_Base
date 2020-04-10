package com.msj.android_project.mmodel;

import com.msj.android_project.bean.Account;
import com.msj.android_project.callback.MCallback;

public class MVPPresenter {
    private MVPModel mvpModel;

    private MVP mvp;

    public MVPPresenter(MVP mvp) {
        this.mvp = mvp;
        mvpModel = new MVPModel();
    }

    public void getData() {
        mvpModel.getAccountData(mvp.getUserInfoMVP(), new MCallback() {
            @Override
            public void onSuccess(Account account) {
                mvp.showSuccessMVP(account);
            }

            @Override
            public void onFailed() {
                mvp.showfailedMVP();
            }
        });
    }
}
