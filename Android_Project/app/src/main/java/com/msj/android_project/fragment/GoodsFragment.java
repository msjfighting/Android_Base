package com.msj.android_project.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.msj.android_project.R;
import com.msj.android_project.adapter.GoodsAdapter;
import com.msj.android_project.bean.DataBean;
import com.msj.android_project.bean.GoodsBean;
import com.msj.android_project.okhttp.CommonOkHttpClient;
import com.msj.android_project.okhttp.listener.DisposeDataHandle;
import com.msj.android_project.okhttp.listener.DisposeDataListener;
import com.msj.android_project.okhttp.request.CommonRequets;
import com.msj.android_project.utils.L;
import com.msj.android_project.utils.Constants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wega.library.loadingDialog.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsFragment extends Fragment {
    private static final String DATA_KEY = "goods_key";
    private static final String DATA_TYPE = "goods_type";

    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.smartRefreshLayout) RefreshLayout smartRefreshLayout;
    private int index;
    private int mType;

    private LoadingDialog mLoadview;

    private List<GoodsBean> mList;

    private GoodsAdapter mAdapter;

    private int mid;

    public static GoodsFragment newInstance(int i,int type) {

        Bundle args = new Bundle();
        args.putInt(DATA_KEY,i);
        args.putInt(DATA_TYPE,type);

        GoodsFragment fragment = new GoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            index = bundle.getInt(DATA_KEY);
            mType = bundle.getInt(DATA_TYPE);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goods_fregment,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLoadview = new LoadingDialog(getContext());

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        // 添加分割线
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        // 设置数据源
        mList = new ArrayList<>();
        mid = 1;

        mAdapter = new GoodsAdapter(getContext(),mList);

        mRecyclerview.setAdapter(mAdapter);

        mLoadview.loading("加载中...");

        if (mType == 0){
            loadHomeData(mid,index);
        }else{
            loadHotData(mid,index + 1);
        }


        //设置 Header
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        //设置 Footer 为 球脉冲 样式
        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        smartRefreshLayout.setEnableAutoLoadMore(true);
        smartRefreshLayout.setScrollBoundaryDecider(new ScrollBoundaryDecider() {
            @Override
            public boolean canRefresh(View content) {
               return true;
            }

            @Override
            public boolean canLoadMore(View content) {
               return true;
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mid = 1;
                mList.clear();
                mLoadview.loading("加载中...");
                if (mType == 0){
                    loadHomeData(mid,index);
                }else{
                    loadHotData(mid,index + 1);
                }

               smartRefreshLayout.finishRefresh();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mLoadview.loading("加载中...");
                if (mType == 0){
                    loadHomeData(mid,index);
                }else{
                    loadHotData(mid,index + 1);
                }
                smartRefreshLayout.finishLoadMore();
            }
        });

    }

    private  void loadHomeData(int min_id, final int cid) {

        CommonOkHttpClient.get(CommonRequets.createGetRequest(
                Constants.URL + "/column/apikey/" + Constants.APP_KEY + "/type/1/back/10/min_id/" + min_id + "/cid/" + cid+"/sort/0"
                , null)
                , new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        L.e(responseObj.toString());
                        Gson gson = new Gson();
                        DataBean bean = gson.fromJson(responseObj.toString(), DataBean.class);//解析成集合

                        mid = Integer.parseInt(bean.min_id);

                        mList.addAll(bean.data);
                        mAdapter.setData(mList);
                        mAdapter.notifyDataSetChanged();

                        mLoadview.loadComplete("数据加载完成");
                    }

                    @Override
                    public void onFailure(Object responseObj) {

                    }
                }));

    }

    private  void loadHotData(int min_id, final int sale_type) {

        CommonOkHttpClient.get(CommonRequets.createGetRequest(
                Constants.URL + "/sales_list/apikey/" + Constants.APP_KEY + "/sale_type/" + sale_type +"/min_id/" + min_id
                , null)
                , new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        L.e(responseObj.toString());
                        Gson gson = new Gson();
                        DataBean bean = gson.fromJson(responseObj.toString(), DataBean.class);//解析成集合

                        mid = Integer.parseInt(bean.min_id);

                        mList.addAll(bean.data);
                        mAdapter.setData(mList);

                        mAdapter.notifyDataSetChanged();

                        mLoadview.loadComplete("数据加载完成");
                    }

                    @Override
                    public void onFailure(Object responseObj) {

                    }
                }));

    }
}
