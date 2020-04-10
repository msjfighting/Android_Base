package com.msj.android_project.callback;

import com.msj.android_project.bean.Account;

public interface MCallback {
    void onSuccess(Account account);
    void onFailed();
}
