package com.msj.android_project.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.msj.android_project.R;
import com.msj.android_project.bean.DownLoadBean;
import com.msj.android_project.updater.AppUpdater;
import com.msj.android_project.updater.net.NetDownLoadCallBack;
import com.msj.android_project.utils.MToast;
import com.msj.android_project.utils.SystemUtil;

import java.io.File;

public class UpdateVersionShowDialog extends DialogFragment {

    private static final String KEY_DOWNLOAD_BEAN  = "download_bean";

    private DownLoadBean mBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null){
            mBean = (DownLoadBean) arguments.getSerializable(KEY_DOWNLOAD_BEAN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_updater, container, false);
        bindEvents(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void bindEvents(View view) {
        TextView title = view.findViewById(R.id.title);
        TextView content =  view.findViewById(R.id.content);
        TextView update = view.findViewById(R.id.update);

        title.setText(mBean.title);
        content.setText(mBean.content);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                // 下载
                File file = new File(getActivity().getCacheDir(),"target.apk");
                AppUpdater.getInstance().getNetManager().download(mBean.url, file, new NetDownLoadCallBack() {
                    @Override
                    public void success(File apkFile) {
                        // 安装代码
                        v.setEnabled(true);
                        dismiss();
                        //TODO check md5
                        String fileMd5 = SystemUtil.getFileMd5(file);
                        if (fileMd5 != null && fileMd5.equals(mBean.md5)){
                            SystemUtil.installAPK(getActivity(),apkFile);
                        }else {
                            MToast.showToast("md5检测失败");
                        }
                    }

                    @Override
                    public void progress(int progress) {
                        // 更新界面
                        update.setText(progress + "%");
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        MToast.showToast("下载失败");
                        v.setEnabled(true);
                    }
                },UpdateVersionShowDialog.this);

            }
        });

    }

    public static void show(FragmentActivity activity, DownLoadBean bean){
        Bundle bundle = new Bundle();

        bundle.putSerializable(KEY_DOWNLOAD_BEAN,bean);

        UpdateVersionShowDialog dialog = new UpdateVersionShowDialog();

        dialog.setArguments(bundle);

        dialog.show(activity.getSupportFragmentManager(),"UpdateVersionShowDialog");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        AppUpdater.getInstance().getNetManager().cancel(this);
    }
}
