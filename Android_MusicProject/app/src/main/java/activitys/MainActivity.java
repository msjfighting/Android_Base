package activitys;

import adapter.MusicGridAdapter;
import adapter.MusicListAdapter;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.classtwo.R;
import helps.RealmHelp;
import models.MusicSourceModel;
import views.GridSpaceItemDecoration;

public class MainActivity extends BaseActivity {
    private RecyclerView recycleView,listRecycleView;
    private RealmHelp mRealmH;
    private MusicSourceModel musicSourceModel;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();

    }

    private void initData() {
        mRealmH = new RealmHelp();
        musicSourceModel = mRealmH.getMusicSource();
    }

    private void initView() {
        recycleView = fd(R.id.rv_grid);
        listRecycleView = fd(R.id.rv_list);
        initNavBar(false,"小黄🐻音乐酒吧",true);

        recycleView.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));
        recycleView.addItemDecoration(new GridSpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.albumMarginSize),recycleView));
        recycleView.setNestedScrollingEnabled(false);
        recycleView.setAdapter(new MusicGridAdapter(this,musicSourceModel.getAlbum()));

        /*
          1. 假如已知列表高度的情况下,可以直接在布局中吧Recycleview的高度定义上
          2. 不知道列表高度的情况下,需要手动计算RecycleView的高度
         */
        listRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        // 限制自身的滑动
        listRecycleView.setNestedScrollingEnabled(false);
        // 添加分割线
        listRecycleView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        listRecycleView.setAdapter(new MusicListAdapter(this,listRecycleView,musicSourceModel.getHot()));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmH.close();
    }
}
