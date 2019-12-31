package myAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.zlhj.classone.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public MyAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    // 计算需要适配的item的总数
    @Override
    public int getCount() {
        return list.size();
    }

    // 获取每个item对象
    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    // 获取每个item的id值
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 获取每个item对应的view视图 ---- 重要
    // convertView 系统复用的视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         /*
            // 获取布局映射对象
            LayoutInflater inflater = LayoutInflater.from(custom_activity.this);
            // 将定义好的xml文件转化为View对象
            View view = inflater.inflate(R.layout.my_adapter,null);
            // 给View对象中的控件进行赋值
            TextView tv = view.findViewById(R.id.myadapter_tv);
            // 赋值
            tv.setText(getItem(position));
            */
        Holder holder;
        // 表示第一次运行
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.my_adapter,null);
            holder = new Holder();
            holder.tv = convertView.findViewById(R.id.myadapter_tv);
            // 打标签
            convertView.setTag(holder);
        }else {
            // 复用
            holder = (Holder) convertView.getTag();
        }
        //  赋值
        holder.tv.setText(list.get(position));
        return convertView;
    }
}
    class Holder{
        TextView tv;
    }
