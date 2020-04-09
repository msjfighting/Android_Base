package com.msj.android_project.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import com.msj.android_project.R;
import com.msj.android_project.view.GamePintuLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayGameActivity extends BaseActivity {

    @BindView(R.id.id_pintu)
    GamePintuLayout idPintu;
    @BindView(R.id.id_level)
    TextView idLevel;
    @BindView(R.id.id_time)
    TextView idTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        ButterKnife.bind(this);
        initNavBar(true, "美女拼图小游戏");

        idLevel.setText("1");
        idPintu.isTimeEnabled = true;
        idPintu.setDuration(5);
        idPintu.setOnGamePintuListener(new GamePintuLayout.GamePintuListener() {
            @Override
            public void nextLevel(int nextLevel) {
                idLevel.setText(nextLevel + "");
                new AlertDialog.Builder(PlayGameActivity.this).setTitle("Game Info")
                        .setMessage("升级了")
                        .setPositiveButton("升级了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                idPintu.nextLevel();
                            }
                        }).show();
            }

            @Override
            public void timeChangeed(int currentTime) {
                idTime.setText(currentTime + "");
            }

            @Override
            public void gameOver() {
                new AlertDialog.Builder(PlayGameActivity.this).setTitle("Game Info")
                        .setMessage("游戏结束")
                        .setPositiveButton("继续", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                idPintu.restart();
                            }
                        })
                        .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onBackPressed();
                            }
                        }).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        idPintu.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        idPintu.resume();
    }
}
