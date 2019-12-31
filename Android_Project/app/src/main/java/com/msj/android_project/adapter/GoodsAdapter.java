package com.msj.android_project.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.msj.android_project.R;
import com.msj.android_project.bean.GoodsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.MyViewHondle> {

    private Context mContext;
    private List<GoodsBean> mBeans;

    public GoodsAdapter(Context context, List<GoodsBean> mGoodsBean) {
        mContext = context;
        mBeans = mGoodsBean;
    }

    @NonNull
    @Override
    public MyViewHondle onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHondle(View.inflate(mContext,R.layout.item_goods,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHondle myViewHondle, int i) {
        GoodsBean mGoodsBean = mBeans.get(i);
        myViewHondle.mTextViews1.setText(mGoodsBean.itemtitle);
        Glide.with(myViewHondle.mImageViews1).load(mGoodsBean.itempic+"_310x310.jpg").into(myViewHondle.mImageViews1);

        if (mGoodsBean.shoptype.equals("B")){
            myViewHondle.mImageViews2.setImageResource(R.drawable.tianmao);
        }else{
            myViewHondle.mImageViews2.setImageResource(R.drawable.taobao);
        }

        if (Float.parseFloat(mGoodsBean.itemsale) >= 10000.0){
            myViewHondle.mTextViews4.setText(Float.parseFloat(mGoodsBean.itemsale)/10000.0 + "万人已购");
        }else{
            myViewHondle.mTextViews4.setText(mGoodsBean.itemsale + "人已购");
        }

        if (mGoodsBean.tkmoney.isEmpty()){
            myViewHondle.mTextViews2.setText("预计奖励:￥"+ Float.parseFloat(mGoodsBean.itemendprice)*Float.parseFloat(mGoodsBean.tkrates));
        }else{
            myViewHondle.mTextViews2.setText("预计奖励:￥"+mGoodsBean.tkmoney);
        }
        myViewHondle.mTextViews3.setText("淘宝价:￥"+mGoodsBean.itemprice);

        myViewHondle.mTextViews6.setText(mGoodsBean.couponmoney+"券");
        myViewHondle.mTextViews5.setVisibility(View.VISIBLE);
        if (mGoodsBean.couponmoney.isEmpty()){
            myViewHondle.mTextViews5.setVisibility(View.GONE);
        }

        myViewHondle.mTextViews3.setText(mGoodsBean.itemendprice);
    }

    @Override
    public int getItemCount() {
        return mBeans.size();
    }

    class MyViewHondle extends ViewHolder{
        @BindView(R.id.goods_img) ImageView mImageViews1;
        @BindView(R.id.goods_source_img) ImageView mImageViews2;

        @BindView(R.id.goods_title) TextView mTextViews1;
        @BindView(R.id.goods_reward) TextView mTextViews2;
        @BindView(R.id.goods_price) TextView mTextViews3;
        @BindView(R.id.goods_sale) TextView mTextViews4;
        @BindView(R.id.goods_discount) TextView mTextViews5;
        @BindView(R.id.goods_quan) TextView mTextViews6;
        public MyViewHondle(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public void setData(List<GoodsBean> mBeans){
            this.mBeans = mBeans;

    }


}
