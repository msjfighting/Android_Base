package com.msj.android_project.bean;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
// extends BaseObservable
public class Account{
    private String name;
    private int level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /// @Bindable
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        // notifyPropertyChanged(BR.level);
    }
}
