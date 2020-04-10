package com.msj.android_project.mmodel;

import com.msj.android_project.bean.Account;
import com.msj.android_project.callback.MCallback;

import java.util.Random;

public class MVCModel {
    public void getAccountData(String name, MCallback callback) {
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

}
