package com.msj.android_project.mmodel;

import com.msj.android_project.bean.Account;

public interface MVP {

    String getUserInfoMVP();
    void showSuccessMVP(Account account);
    void showfailedMVP();

}
