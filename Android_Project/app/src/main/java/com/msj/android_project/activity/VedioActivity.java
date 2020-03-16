package com.msj.android_project.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.msj.android_project.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VedioActivity extends BaseActivity {

    @BindView(R.id.vedio)
    VideoView vedio;
    @BindView(R.id.current_bar)
    SeekBar currentBar;
    @BindView(R.id.pause_img)
    ImageView pauseImg;
    @BindView(R.id.time_current_tv)
    TextView timeCurrentTv;
    @BindView(R.id.time_total_tv)
    TextView timeTotalTv;
    @BindView(R.id.volume_img)
    ImageView volumeImg;
    @BindView(R.id.volume_bar)
    SeekBar volumeBar;
    @BindView(R.id.screen_img)
    ImageView screenImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);
        ButterKnife.bind(this);

        initNavBar(true, "视频播放");

        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(vedio);

        vedio.setMediaController(controller);


        //initLocalVedio();

        initNetVedio();
    }

    /**
     * 本地视频播放
     */
    private void initLocalVedio() {


    }

    /**
     * 网络视频播放
     */
    private void initNetVedio() {
        vedio.setVideoURI(Uri.parse("https://gqnew2.new1-youku.com/20200216/qFwIhEvniZkCfJM4/index.m3u8"));
    }

    @OnClick({R.id.pause_img, R.id.screen_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pause_img:{
                if (vedio.isPlaying()){
                    pauseImg.setBackgroundResource(R.drawable.play_btn_style);
                    vedio.pause();
                }else {
                    pauseImg.setBackgroundResource(R.drawable.pause_btn_style);
                    vedio.start();
                }
            }

                break;
            case R.id.screen_img:

                break;
        }
    }

    private void updateTextViewWithTimeFormat(TextView textView,int millisecond){
        int second = millisecond / 1000;
        int hh = second / 3600;
        int mm = second % 3600/60;
        int ss = second % 60;
        String string = null;
        if (hh != 0){
            string = String.format("%02d:%02d:%02d",hh,mm,ss);
        }else {
            string = String.format("%02d:%02d",mm,ss);
        }
        textView.setText(string);
    }
}
