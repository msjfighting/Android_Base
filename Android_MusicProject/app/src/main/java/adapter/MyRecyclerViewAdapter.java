package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.classtwo.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private final Context content;
    private  ArrayList<String> datas;

    public MyRecyclerViewAdapter(Context content, ArrayList<String> datas) {
        this.content = content;
        this.datas = datas;
    }


    // 相当于ListView中getView方法的创建view和ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = View.inflate(content, R.layout.item_recyclerview,null);

        return new MyViewHolder(itemView);
    }
    // 相当于getView绑定数据部分的代码
    // 数据和view绑定
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        // 根据位置得到对应的数据

        String data = datas.get(i);
        myViewHolder.text.setText(data);
    }

    // 得到总条数
    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(int position, String new_content) {
        datas.add(position,new_content);
        // 刷新适配器
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        datas.remove(position);
        // 刷新适配器
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView icon;
        private TextView text;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iv_icon);
            text = itemView.findViewById(R.id.iv_title);
            // item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(content,"data=="+datas.get(getLayoutPosition()),Toast.LENGTH_SHORT).show();
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(view,datas.get(getLayoutPosition()));
                    }
                }
            });
        }
    }
    // 自定义点击item事件
    public interface OnItemClickListener{
        // 当RecyclerView某个被点击的时候回调
        public  void onItemClick(View view,String data);
    }
    private  OnItemClickListener onItemClickListener;

    // 设置RecyclerView某条监听
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
