package helps;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * 1. 直接在Activity中去创建播放音乐,音乐与Activity绑定 Activity运行时播放音乐 Activity退出时停止播放音乐
 * 2. 通过全局单例类与Application绑定, Application被杀死时音乐停止播放
 * 3. 通过 service 进行音乐播放
 */
public class MediaPlayerHelp {
    public static  MediaPlayerHelp instance;
    private Context mcontent;
    private String mPath;
    private MediaPlayer mMediaplater;
    private OnMediaPlayerHelperListener onMediaPlayerHelperListener;

    public void setOnMediaPlayerHelperListener(OnMediaPlayerHelperListener onMediaPlayerHelperListener) {
        this.onMediaPlayerHelperListener = onMediaPlayerHelperListener;
    }

    // 单例
    public static  MediaPlayerHelp getInstance(Context content){
        if (instance == null){
            synchronized (MediaPlayerHelp.class){
                if (instance == null){
                    instance = new MediaPlayerHelp(content);
                }
            }
        }
        return instance;
    }

    private MediaPlayerHelp(Context content){
            mcontent = content;
            mMediaplater = new MediaPlayer();
    }

    /*
        1. setPath: 播放的地址
        2. start : 播放音乐
        3. pause : 暂停播放
     */

    public void setPath( String path){
        /*
          音乐正在播放/切换了音乐时
          设置播放音乐路径
          准备播放
         */

        /**
         * (逻辑错误!!!!)当音乐进行切换的时候,如果音乐处于播放状态,那么我们就去重置音乐播放状态,
         * 如果音乐处于不播放 (暂停) 状态,那么就不去重置播放状态
         */
        // 音乐正在播放/切换了音乐时,重置音乐播放状态
        if (mMediaplater.isPlaying() || !path.equals(mPath)){
            mMediaplater.reset();
        }

        mPath = path;


        try {
            mMediaplater.setDataSource(mcontent, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 异步加载音乐
        mMediaplater.prepareAsync();
        mMediaplater.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (onMediaPlayerHelperListener != null){
                    onMediaPlayerHelperListener.onPrepared(mediaPlayer);
                }
            }
        });

        // 监听音乐播放完成
        mMediaplater.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (onMediaPlayerHelperListener != null){
                    onMediaPlayerHelperListener.onCompletion(mediaPlayer);
                }
            }
        });
    }


    /**
     * @return 返回正在播放的音乐路径
     */
    public String getPath(){

        return mPath;
    }

    // 播放音乐
    public void start(){
        if (mMediaplater.isPlaying()) return;
        mMediaplater.start();

    }

    //  暂停播放
    public void pause(){
        mMediaplater.pause();
    }


    public interface  OnMediaPlayerHelperListener{
        void onPrepared(MediaPlayer mp);
        void onCompletion (MediaPlayer mp);
    }
}
