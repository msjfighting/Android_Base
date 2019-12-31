package activitys;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.classtwo.R;
import helps.RealmHelp;
import jp.wasabeef.glide.transformations.BlurTransformation;
import models.MusicModel;
import views.PlayMusicView;

public class PlayMusicActivity extends BaseActivity {
    public static final String MUSIC_ID = "musicid";
    private ImageView iv_bg;
    private PlayMusicView playView;
    private String musicId;
    private RealmHelp mRealmH;
    private MusicModel musicModel;
    private TextView song_name,song_author;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmusic);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        initData();

        initView();
    }

    private void initData() {
        musicId = getIntent().getStringExtra(MUSIC_ID);
        mRealmH = new RealmHelp();
        musicModel = mRealmH.getMusic(musicId);

    }

    private void initView() {
        iv_bg = fd(R.id.iv_bg);
        playView = fd(R.id.play_music_icon);
        song_author = fd(R.id.song_author);
        song_name = fd(R.id.song_name);

        //背景高斯模糊
        Glide.with(this)
                .load(musicModel.getPoster())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25,10)))
                .into(iv_bg);

        song_name.setText(musicModel.getName());
        song_author.setText(musicModel.getAuthor());

//        playView.setMusicIcon(musicModel.getPoster());
        playView.setMusic(musicModel);
        playView.playmusic();
    }


    public void onBackClick(View view){
        onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        playView.destory();
        mRealmH.close();
    }
}
