package com.msj.android_project.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.msj.android_project.R;
import com.msj.android_project.utils.ToolsUtil;
import com.msj.android_project.view.CustomVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VedioActivity extends BaseActivity {

    @BindView(R.id.vedio)
    CustomVideoView vedio;
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

    private static final int UPDATE_UI = 1;

    @BindView(R.id.vedio_layout)
    RelativeLayout vedioLayout;
    @BindView(R.id.controllerBar_layout)
    LinearLayout controllerBarLayout;

    private int screen_width;
    private int screent_height;

    private AudioManager audioManager;

    private boolean isFullScreen = false; // 是否是横屏

    private int startRotation;

    private OrientationEventListener mOrientationListener; // 屏幕方向改变监听器


    /**
     * 实时播放刷新播放时间显示
     */
    private Handler UIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_UI) {
                // 获取视频当前的播放时间
                int currentPosition = vedio.getCurrentPosition();
                // 获取视频播放的总时间
                int totalduration = vedio.getDuration();


                // 格式化时间
                updateTextViewWithTimeFormat(timeCurrentTv, currentPosition);

                updateTextViewWithTimeFormat(timeTotalTv, totalduration);

                currentBar.setMax(totalduration);
                currentBar.setProgress(currentPosition);
                UIHandler.sendEmptyMessageDelayed(UPDATE_UI, 500);
            }
        }
    };

    /**
     * 用来开启自动旋转，响应屏幕旋转事件
     */
    private Handler orientationHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startRotation = -2;
            mOrientationListener.enable();
        }
    };

    private Handler hideHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_UI){
                controllerBarLayout.setVisibility(View.GONE);
            }else {
                controllerBarLayout.setVisibility(View.VISIBLE);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);
        ButterKnife.bind(this);
        // 获取音频服务
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        initNavBar(true, "视频播放");
        screen_width = getResources().getDisplayMetrics().widthPixels;
        screent_height = getResources().getDisplayMetrics().heightPixels;

//        MediaController controller = new MediaController(this);
//        controller.setMediaPlayer(vedio);
//        vedio.setMediaController(controller);

        initNetVedio();
    }

    /**
     * 网络视频播放
     */
    private void initNetVedio() {
        vedio.setVideoURI(Uri.parse("https://gqnew2.new1-youku.com/20200216/qFwIhEvniZkCfJM4/index.m3u8"));

        vedio.start();
        UIHandler.sendEmptyMessage(UPDATE_UI);

        // 获取设备的最大音量
        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 获取设备当前的音量
        int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeBar.setMax(streamMaxVolume);
        volumeBar.setProgress(streamVolume);
        volumeBar.setEnabled(true);
        currentBar.setEnabled(true);
        // 播放拖动
        currentBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTextViewWithTimeFormat(timeCurrentTv, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                UIHandler.removeMessages(UPDATE_UI);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                // 让视频播放进度遵循SeekBar 停止拖动这一刻的进度
                vedio.seekTo(progress);
                UIHandler.sendEmptyMessage(UPDATE_UI);
            }
        });

        // 声音拖动
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                /**
                 * 设置当前设备音量
                 */
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        vedio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                pauseImg.setImageResource(R.drawable.play_btn_style);
                UIHandler.removeMessages(UPDATE_UI);
            }
        });

        vedio.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
                if (what != MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    pauseImg.setImageResource(R.drawable.pause_btn_style);
                }
                return true;
            }
        });


    }

    @OnClick({R.id.pause_img, R.id.screen_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pause_img: {
                if (vedio.isPlaying()) {
                    pauseImg.setImageResource(R.drawable.play_btn_style);
                    vedio.pause();
                    UIHandler.removeMessages(UPDATE_UI);
                } else {
                    pauseImg.setImageResource(R.drawable.pause_btn_style);
                    vedio.start();
                    UIHandler.sendEmptyMessage(UPDATE_UI);
                }
            }
            break;
            case R.id.screen_img:
                startListener();
                // 设定了屏幕方向是portrait,和在清单文件中配置android:screenOrientation="portrait"是同等的效果;也即不再响应屏幕方向改变,只支持portrait方向;
                if (isFullScreen) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                // 2秒后开启屏幕旋转监听，用来开启自动旋转，响应屏幕旋转事件
                orientationHandler.sendEmptyMessageDelayed(0, 2000);
                break;
        }
    }

    /**
     * 播放视频时设置播放时间TV与已播放时间TV的文字
     */
    private void updateTextViewWithTimeFormat(TextView textView, int millisecond) {
        int second = millisecond / 1000;
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String string = null;
        if (hh != 0) {
            string = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            string = String.format("%02d:%02d", mm, ss);
        }
        textView.setText(string);
    }

    /**
     * 横竖屏切换时修改布局大小
     *
     * @param width
     * @param height
     */
    private void setVedioScale(int width, int height) {
        ViewGroup.LayoutParams layoutParams = vedio.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        vedio.setLayoutParams(layoutParams);

        ViewGroup.LayoutParams layoutParams1 = vedioLayout.getLayoutParams();
        layoutParams1.width = width;
        layoutParams1.height = height;
        vedioLayout.setLayoutParams(layoutParams1);
    }


    /**
     * 监听屏幕方向的改变
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 横屏
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setVedioScale(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            volumeImg.setVisibility(View.VISIBLE);
            volumeBar.setVisibility(View.VISIBLE);
            isFullScreen = true;
            // 移除半屏状态
            getWindow().clearFlags((WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN));
            getWindow().addFlags((WindowManager.LayoutParams.FLAG_FULLSCREEN));
        } else {
            // 竖屏
            setVedioScale(ViewGroup.LayoutParams.MATCH_PARENT, ToolsUtil.dp2px(this, 240));
            volumeImg.setVisibility(View.GONE);
            volumeBar.setVisibility(View.GONE);
            isFullScreen = false;
            // 移除半屏状态
            getWindow().clearFlags((WindowManager.LayoutParams.FLAG_FULLSCREEN));
            getWindow().addFlags((WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN));
        }
        //2秒后隐藏播放工具栏
        hideHandler.sendEmptyMessageDelayed(UPDATE_UI,2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UIHandler.removeMessages(UPDATE_UI);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    /**
     * 开启监听器
     */
    private final void startListener() {
        mOrientationListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int rotation) {

                if (startRotation == -2) {//初始化角度
                    startRotation = rotation;
                }
                //变化角度大于30时，开启自动旋转，并关闭监听
                int r = Math.abs(startRotation - rotation);
                r = r > 180 ? 360 - r : r;
                if (r > 30) {
                    //开启自动旋转，响应屏幕旋转事件
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
                    this.disable();
                }
            }
        };
    }
}
