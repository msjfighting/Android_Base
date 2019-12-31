package adapter;

import activitys.AlbumListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.classtwo.R;
import models.AlbumModel;
import views.WEqualsHImageView;

import java.util.List;

public class MusicGridAdapter extends RecyclerView.Adapter<MusicGridAdapter.ViewHolder> {


    private Context content;
    private List<AlbumModel> mDataSource;


    public MusicGridAdapter(Context mcontent,List<AlbumModel> mDataSource) {
        content = mcontent;
        this.mDataSource = mDataSource;
    }

    // 相当于ListView中getView方法的创建view和ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(content).inflate(R.layout.item_grid,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final AlbumModel albumModel = mDataSource.get(i);
        // 加载网络图片
        Glide.with(content).load(albumModel.getPoster()).into(viewHolder.gridIcon);
        viewHolder.playNum.setText(albumModel.getPlayNum());
        viewHolder.songName.setText(albumModel.getName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(content, AlbumListActivity.class);
                i.putExtra(AlbumListActivity.ALBUM_ID, albumModel.getAlbumId());
                content.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        WEqualsHImageView gridIcon;
         TextView playNum, songName;
         View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            gridIcon = itemView.findViewById(R.id.grid_pic);
            playNum = itemView.findViewById(R.id.grid_play_num);
            songName = itemView.findViewById(R.id.grid_song_name);


        }
    }
}
