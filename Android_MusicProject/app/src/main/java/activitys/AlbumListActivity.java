package activitys;

import adapter.MusicListAdapter;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.classtwo.R;
import helps.RealmHelp;
import models.AlbumModel;
import models.MusicModel;

import java.util.List;

public class AlbumListActivity extends BaseActivity {
    public static final String ALBUM_ID = "albumid";

    RecyclerView albumlist;

    private String mAlbumId;
    private RealmHelp mRealmH;
    private AlbumModel albumModel;
    private List<MusicModel> mData;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albumlist);

        initData();

        initView();

    }

    private void initData() {
        mAlbumId = getIntent().getStringExtra(ALBUM_ID);
        mRealmH = new RealmHelp();
        albumModel = mRealmH.getAlbum(mAlbumId);
        mData = albumModel.getList();
    }

    private void initView() {
        initNavBar(true,"专辑列表",false);
        albumlist = fd(R.id.albumlist);

        albumlist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        // 限制自身的滑动
        albumlist.setNestedScrollingEnabled(false);
        // 添加分割线
        albumlist.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        albumlist.setAdapter(new MusicListAdapter(this,null,mData));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmH.close();
    }
}
