package myAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zlhj.classone.R;

import junit.framework.Test;

import java.util.List;
import java.util.Map;

public class TestAdapter extends BaseAdapter {
    private Context content;
    private int[] images;
    private String[] names;

    public TestAdapter(Context content, int[] images, String[] names) {
        this.content = content;
        this.images = images;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public String getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TestHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(content).inflate(R.layout.gridview,null);
           holder = new TestHolder();
           holder.image = convertView.findViewById(R.id.image);
            holder.name = convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }else{
            holder = (TestHolder) convertView.getTag();
        }
        holder.image.setImageResource(images[position]);
        holder.name.setText(names[position]);
        return convertView;
    }
    class TestHolder{
        TextView name;
        ImageView image;
    }
}
