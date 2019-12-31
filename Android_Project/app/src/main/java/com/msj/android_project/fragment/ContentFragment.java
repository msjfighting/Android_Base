package com.msj.android_project.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.msj.android_project.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentFragment extends Fragment {

    @BindView(R.id.tv_content)
    TextView textView;
    @BindView(R.id.li_layout)
    LinearLayout mLayout;
    private String text;

    private ClickItem listener;

    public static ContentFragment newInstance() {
        ContentFragment fragment = new ContentFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 由于 activity 实现了 ClickItem 则采用此方法
        listener = (ClickItem) getActivity();
    }

    /**
     * 第一次绘制用户界面时系统回调
     * @param inflater  布局填充器或加载器 将xml转换为view
     * @param container 表示当前fragment插入activity的布局视图对象
     * @param savedInstanceState 表示存储上一个fragment的信息
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content,container,false);
        ButterKnife.bind(this,view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            text = bundle.getString("info");
            setText(text);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    public void setText(String text){
        textView.setText(text);
    }

    @OnClick(R.id.tv_content)
    public void click(){
        if (listener != null){
            listener.click("fragmnet向activity传值");
        }
    }




    public interface ClickItem{
        void click(String text);
    }
}
