package services;

import activitys.SplashActivity;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.view.View;
import com.example.classtwo.R;
import helps.MediaPlayerHelp;
import models.MusicModel;

/**
 * 1, 通过Service 链接PlayMusicView 和 MediaPlayHelper
 * 2, PlayMusicView -- Service:
 *     1, 播放音乐,暂停音乐
 *     2, 启动Service,绑定Service ,接触绑定Service
 * 3, MediaPlayHelper -- Service
 *    1, 播放音乐,暂停音乐
 *    2, 监听音乐播放完成,停止Service
 *
 */
public class MusicService extends Service {
    // 设置id时 值不可为0
    private static final int NOTIFICATION_ID = 1;
    private MediaPlayerHelp mMediaPlayerHelp;
    private MusicModel mMusicModel;


    public MusicService() {
    }

    public class MusicBind extends Binder{
        /**
         * 1, 设置音乐(MusicModel)
         */
        public void setMusic(MusicModel musicModel){
            mMusicModel = musicModel;
            startforeground();
        }

        // 播放音乐
        public void playMusic(){
            // 1. 判断当前音乐是否已经在播放的音乐
            // 如果当前的音乐是已经在播放的音乐 则执行start
            // 否则 重置播放音乐
            if ( mMediaPlayerHelp.getPath()!= null && mMediaPlayerHelp.getPath().equals(mMusicModel.getPath())){
                mMediaPlayerHelp.start();
            }else{
                mMediaPlayerHelp.setPath(mMusicModel.getPath());
                mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mMediaPlayerHelp.start();
                    }

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                            stopSelf();
                    }
                });
            }
        }

        // 暂停播放
        public void stopMusic(){
            mMediaPlayerHelp.pause();

        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new MusicBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayerHelp = MediaPlayerHelp.getInstance(this);
    }


    /**
     * 系统默认不允许不可见的后台服务播放音乐.
     * Notification
     */
    // 设置服务前台可见

    private void startforeground(){

        // 通知栏点击跳转的intent
        PendingIntent pendingIntent = PendingIntent
                .getActivities(this,0, new Intent[]{new Intent(this, SplashActivity.class)},PendingIntent.FLAG_CANCEL_CURRENT);

        /**
         * 创建Notification
         */
        Notification notification = null;
        /**
         * android API 26 以上 NotificationChannel 特性适配
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = createNotificationChannel();
            notification = new Notification.Builder(this, channel.getId())
                    .setContentTitle(mMusicModel.getName())
                    .setContentText(mMusicModel.getAuthor())
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pendingIntent)
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        } else {
            notification = new Notification.Builder(this)
                    .setContentTitle(mMusicModel.getName())
                    .setContentText(mMusicModel.getAuthor())
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pendingIntent)
                    .build();
        }

        // 设置notification 在前台展示
        startForeground(NOTIFICATION_ID,notification);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel createNotificationChannel () {
        String channelId = "imooc";
        String channelName = "ImoocMusicService";
        String Description = "ImoocMusic";
        NotificationChannel channel = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(Description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        channel.setShowBadge(false);

        return channel;

    }


}
