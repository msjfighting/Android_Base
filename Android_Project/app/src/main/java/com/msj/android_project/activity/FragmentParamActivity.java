package com.msj.android_project.activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.msj.android_project.R;
import com.msj.android_project.fragment.ContentFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentParamActivity extends AppCompatActivity implements ContentFragment.ClickItem {
    @BindView(R.id.et_content)
    EditText input;

    @BindView(R.id.ll_layout)
    LinearLayout layout;

    private FragmentManager manager;

    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_param);

        ButterKnife.bind(this);

        manager = getSupportFragmentManager();

        transaction = manager.beginTransaction();

        ContentFragment fragment = new ContentFragment().newInstance();
        transaction.add(R.id.ll_layout,fragment);
        transaction.commit();

    }

    @OnClick(R.id.btn_pass)
    public void click(){
        String text = input.getText().toString().trim();
//        ContentFragment content = (ContentFragment) manager.findFragmentByTag("content");
//        content.setText(text);

        ContentFragment fragment = new ContentFragment().newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("info",text);
        fragment.setArguments(bundle);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.ll_layout,fragment);
        transaction.commit();


    }

    @Override
    public void click(String text) {
        input.setText(text);
    }
}
