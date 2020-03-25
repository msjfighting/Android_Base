package com.msj.android_project.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.msj.android_project.R;
import com.msj.android_project.base.BaseDialog;
import com.msj.android_project.base.OnClicked;
import com.msj.android_project.bean.ScreeningResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public  class PrimaryScreeningDialog extends BaseDialog<ScreeningResult> {
    @BindView(R.id.dialog_img)
    ImageView dialogImg;
    @BindView(R.id.id_dialog_title)
    TextView idDialogTitle;
    @BindView(R.id.id_dialog_content)
    TextView idDialogContent;

    private ScreeningResult mBean;

    private static final String KEY_PARAMS_BEAN = "params_bean";

    private OnClicked linstenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mBean =  (ScreeningResult)arguments.getSerializable(KEY_PARAMS_BEAN);
        }
    }

    @Override
    public int getDialogLayout() {
        return R.layout.primary_screening_dialog;
    }

    @Override
    public PrimaryScreeningDialog show(FragmentActivity activity, ScreeningResult resultInfo, @NonNull OnClicked onClicked) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PARAMS_BEAN, resultInfo);

        setArguments(bundle);
        show(activity.getSupportFragmentManager(), "PrimaryScreeningDialog");
        linstenter = onClicked;
        return this;
    }

    @Override
    protected void bindEvents(View view) {
        ButterKnife.bind(this, view);
        if (mBean != null){
            if (mBean.title.equals("true")){
                idDialogTitle.setText("初筛通过");
                dialogImg.setBackgroundResource(R.drawable.right);
            }else {
                idDialogTitle.setText("初筛拒绝，无法进件");
                dialogImg.setBackgroundResource(R.drawable.error);
                idDialogContent.setText("提示:\n"+mBean.result);
            }
        }
    }


    @OnClick(R.id.id_dialog_commit)
    public void onViewClicked() {
        dismiss();
        if (linstenter != null){
            linstenter.onClicked();
        }
    }


}
