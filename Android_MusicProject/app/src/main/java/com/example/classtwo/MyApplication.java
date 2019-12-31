package com.example.classtwo;


import android.app.Application;
import com.blankj.utilcode.util.Utils;
import helps.RealmHelp;
import io.realm.Realm;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        Realm.init(this);
        RealmHelp.migration();

    }
}
