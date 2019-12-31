package views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.classtwo.R;
import de.hdodenhof.circleimageview.CircleImageView;
import models.MusicModel;
import services.MusicService;

public class PlayMusicView extends FrameLayout {
    private Context mContent;
    private View mView;
    private CircleImageView iv_icon;
    private Animation mPlayMusicAnim,mPlayNeedleAnim,mStopNeedleAnim;
    private FrameLayout fl_play_music;
    private ImageView iv_needle,play_btn;
    private  boolean isPlaying,isBindService;

    private Intent mServiceIntent;
    private MusicService.MusicBind mMusicBind;
    private MusicModel mMusicModel;


    public PlayMusicView( Context context) {
        super(context);
        init(context,null);
    }

    public PlayMusicView( Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public PlayMusicView( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayMusicView( Context context,AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context content,AttributeSet attrs) {
        if (attrs == null) return;

        mContent = content;
        mView = LayoutInflater.from(mContent).inflate(R.layout.play_music,PlayMusicView.this,false);
        iv_icon = mView.findViewById(R.id.iv_icon);

        fl_play_music = mView.findViewById(R.id.fl_play_music);

        fl_play_music.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                    trigger();
            }
        });

        iv_needle = mView.findViewById(R.id.iv_needle);
        play_btn = mView.findViewById(R.id.play_btn);

        /*
          1. 定义所需要执行的动画
            1.1 光盘进行转动的动画
            1.2 指针指向光盘的动画
            1.3 指针离开光盘的动画
          2. startAnimation
         */

        mPlayMusicAnim = AnimationUtils.loadAnimation(mContent,R.anim.play_music_anim);

        mPlayNeedleAnim = AnimationUtils.loadAnimation(mContent,R.anim.play_needle_anim);

        mStopNeedleAnim = AnimationUtils.loadAnimation(mContent,R.anim.stop_needle_anim);


        addView(mView);

    }


    /**
     * 播放状态切换
     */
    public void trigger(){
        if (isPlaying) {
            stopMusic();
        }else {
            playmusic();
        }
    }

    /**
     * 暂停播放
     */
    public void stopMusic(){
        isPlaying = false;
        play_btn.setVisibility(View.VISIBLE);
        fl_play_music.clearAnimation();
        iv_needle.startAnimation(mStopNeedleAnim);
        if (mMusicBind != null)
             mMusicBind.stopMusic();
    }

    /**
     * 开始播放
     */
    public void playmusic(){
        isPlaying = true;
        play_btn.setVisibility(View.GONE);
        fl_play_music.startAnimation(mPlayMusicAnim);
        iv_needle.startAnimation(mPlayNeedleAnim);

        startMusicService();

    }

    // 设置光盘中显示的音乐封面图片
    public void setMusicIcon() {
        Glide.with(mView).load(mMusicModel.getPoster()).into(iv_icon);
    }

    public  void setMusic(MusicModel musicModel){
        mMusicModel = musicModel;
        setMusicIcon();
    }
    /**
     * 启动音乐服务
     */
    private void startMusicService(){
        // 启动service
        if (mServiceIntent == null){
            mServiceIntent = new Intent(mContent, MusicService.class);
            mContent.startService(mServiceIntent);
        }else{
            mMusicBind.playMusic();
        }

        // 绑定service 当前service未绑定,绑定服务
        if (!isBindService){
            isBindService = true;
            mContent.bindService(mServiceIntent,conn,Context.BIND_AUTO_CREATE);
        }
    }

    /**
     * 解除绑定
     */
    public  void destory(){
        if (isBindService){
            isBindService = false;
            mContent.unbindService(conn);
        }
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mMusicBind = (MusicService.MusicBind) iBinder;
            mMusicBind.setMusic(mMusicModel);
            mMusicBind.playMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
