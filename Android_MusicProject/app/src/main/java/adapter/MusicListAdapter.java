package adapter;

import activitys.PlayMusicActivity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.classtwo.R;
import models.MusicModel;
import views.PlayMusicView;

import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MyViewHolder> {
    private Context mContext;
    private View mItemView;
    private RecyclerView mRv;
    private boolean isCalcaulationRvHeight;
    private List<MusicModel> mData;
    public MusicListAdapter(Context content,RecyclerView rv,List<MusicModel> mData) {
        mContext = content;
        mRv = rv;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mItemView = LayoutInflater.from(mContext).inflate(R.layout.item_list,viewGroup,false);
        return new MyViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final MusicModel model = mData.get(i);
        // 加载网络图片
        Glide.with(mContext).load(model.getPoster()).into(myViewHolder.ivIcon);

        myViewHolder.ivTitle.setText(model.getName());
        myViewHolder.ivAuthor.setText(model.getAuthor());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, PlayMusicActivity.class);
                i.putExtra(PlayMusicActivity.MUSIC_ID,model.getMusicId());
                mContext.startActivity(i);
            }
        });
        setRecycleViewHeight();

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /*
      1. 获取itemview的高度
      2. itemview的数量
      3. 使用itemview的高度 * itemview的数量 = recycleview的高度
     */
    private void setRecycleViewHeight(){
        if (isCalcaulationRvHeight || mRv == null) return;
        isCalcaulationRvHeight = true;
        // 获取itemview的高度
      RecyclerView.LayoutParams itemviewlp = (RecyclerView.LayoutParams) mItemView.getLayoutParams();

      // 获取itemview的数量
      int itemCount = getItemCount();
      // 计算recycleview的高度
      int rvHeight = itemviewlp.height * itemCount;

      // 设置recycleview的高度
       LinearLayout.LayoutParams rvLp = (LinearLayout.LayoutParams) mRv.getLayoutParams();
        rvLp.height = rvHeight;
       mRv.setLayoutParams(rvLp);
    }


     class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ivIcon;
        TextView ivTitle,ivAuthor;
        View itemView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivIcon = itemView.findViewById(R.id.item_pic);
            ivTitle = itemView.findViewById(R.id.item_title);
            ivAuthor = itemView.findViewById(R.id.item_author);
        }
    }
}
