package com.msj.android_imageselector;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.msj.android_imageselector.bean.FolderBean;
import com.msj.android_imageselector.utils.ImageLoader;

import java.util.List;

public class ListImageDirPopupWindow extends PopupWindow {

    private int mWidth;
    private int mHeight;
    private View mConvertView;
    private ListView mListView;
    private List<FolderBean> mDatas;

    public interface OnDirSelectListener{
        void OnDirSelectListener(FolderBean bean);
    }

    private OnDirSelectListener mListener;

    public void setmListener(OnDirSelectListener mListener) {
        this.mListener = mListener;
    }

    public ListImageDirPopupWindow(Context context, List<FolderBean> mDatas) {

        calWidthAndHeight(context);
        this.mDatas = mDatas;
        mConvertView = LayoutInflater.from(context).inflate(R.layout.popup_main,null);
        setContentView(mConvertView);
        setWidth(mWidth);
        setHeight(mHeight);

        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE){
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        initView(context);
        initEvent();
    }

    private void initView(Context context) {
        mListView = mConvertView.findViewById(R.id.id_list_dir);
        mListView.setAdapter(new ListDirAdapter(context,mDatas));
    }

    private void initEvent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mListener != null){
                    mListener.OnDirSelectListener(mDatas.get(i));
                }

            }
        });
    }

    /**
     * 计算PopupWindow高度和宽度
     * @param context
     */
    private void calWidthAndHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outM = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outM);

        mWidth = outM.widthPixels;
        mHeight = (int) (outM.heightPixels * 0.7);
    }

    private  class ListDirAdapter extends ArrayAdapter<FolderBean>{
        private LayoutInflater inflater;
        private List<FolderBean> datas;

        public ListDirAdapter( Context context, List<FolderBean> objects) {
            super(context, 0, objects);

            inflater = LayoutInflater.from(context);

        }

        @Override
        public View getView(int position,  View convertView,  ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.item_popup_main,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.item_image = convertView.findViewById(R.id.id_dir_item_image);
                viewHolder.item_name = convertView.findViewById(R.id.id_dir_item_name);
                viewHolder.item_count = convertView.findViewById(R.id.id_dir_item_count);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            FolderBean bean = getItem(position);
            // 重置代码
            viewHolder.item_image.setImageResource(R.drawable.picture_no);
            ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(bean.getFirstImgPath(),viewHolder.item_image);
            viewHolder.item_name.setText(bean.getName());
            viewHolder.item_count.setText(bean.getCount()+"");
            return convertView;
        }

        private class ViewHolder{
            private ImageView item_image;
            private TextView item_name,item_count;
        }
    }


}
