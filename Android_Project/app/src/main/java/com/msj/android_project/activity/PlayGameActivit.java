package com.msj.android_project.activity;

import android.os.Bundle;

import com.msj.android_project.R;

public class PlayGameActivit extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        initNavBar(true, "美女拼图小游戏");

    }
}
