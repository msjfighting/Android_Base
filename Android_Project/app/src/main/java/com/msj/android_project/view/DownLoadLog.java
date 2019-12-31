package com.msj.android_project.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.lxj.xpopup.core.CenterPopupView;
import com.msj.android_project.R;
import com.msj.android_project.utils.ToolsUtil;


public class DownLoadLog extends CenterPopupView implements View.OnClickListener {

    private Context mContext;
    private String desc;
    private Button downLoad;
    public DownLoadLog(Context context, String desc) {
        super(context);
        this.mContext = context;
        this.desc = desc;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.downloadlog;
    }

    @Override
    protected void onShow() {
        super.onShow();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        downLoad = findViewById(R.id.id_update);
        downLoad.setOnClickListener(this);
        TextView title = findViewById(R.id.downLoad_title);
        TextView content = findViewById(R.id.id_download_content);

    }

    @Override
    public void onClick(View view) {
        request();
    }

    private void request(){
        ToolsUtil.requestStoragePermission(mContext, new ToolsUtil.RequestPermissionListenter() {
            @Override
            public void requestPermissionSuccess() {
                Intent intent = new Intent(mContext, DownApkService.class);
                intent.putExtra("apk_url", "");
                mContext.startService(intent);

            }
        });
    }

}
