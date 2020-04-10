package com.msj.android_project.mmodel;

import android.app.Application;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.msj.android_project.bean.Account;
import com.msj.android_project.callback.MCallback;

public class MVVMViewModel extends BaseObservable {

    private String result;

    private MVVMModel mvvmModel;

    public String getInputStr() {
        return inputStr;
    }

    public void setInputStr(String inputStr) {
        this.inputStr = inputStr;
    }

    private String inputStr;


    @Bindable
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
        notifyPropertyChanged(BR.result);
    }

    /**
     * 一盘需要传入Application对象,方便在Viewmodel中使用
     * 比如sharedpreferences需要使用
     * @param application
     */
    public MVVMViewModel (Application application){
        mvvmModel = new MVVMModel();

    }

    public void getData(View view){
        mvvmModel.getAccountData(inputStr, new MCallback() {
            @Override
            public void onSuccess(Account account) {
                setResult(account.getName()+"*******"+account.getLevel());
            }

            @Override
            public void onFailed() {
                setResult("获取数据失败");
            }
        });
    }
}
