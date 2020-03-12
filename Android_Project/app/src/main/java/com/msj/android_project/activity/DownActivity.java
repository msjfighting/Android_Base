package com.msj.android_project.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.msj.android_project.R;
import com.msj.android_project.bean.DownLoadBean;
import com.msj.android_project.updater.AppUpdater;
import com.msj.android_project.updater.net.NetCallback;
import com.msj.android_project.updater.net.NetDownLoadCallBack;
import com.msj.android_project.utils.MToast;
import com.msj.android_project.utils.SystemUtil;
import com.msj.android_project.utils.ToolsUtil;
import com.msj.android_project.view.DownApkService;
import com.msj.android_project.view.UpdateVersionShowDialog;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class DownActivity extends AppCompatActivity {


    /**
     * 断点续下 使用Http的range 设置起始点与终止点 分线程下载
     * 然后合并 使用RandomAccessFile 的seek方法合并
     *
     * 增量更新
     * bsdiff 算法
     *
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.down)
    public void onViewClicked() {
        downloadApkTwo();
    }

    /**
     * 方法一
     */
    private void downloadApkOne(){
        RxPermissions.getInstance(DownActivity.this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            String downUrl = "http://59.110.162.30/v450_imooc_updater.apk";
                            Intent intent = new Intent(DownActivity.this, DownApkService.class);
                            intent.putExtra("apk_url", downUrl);
                            startService(intent);
                        }
                    }
                });
    }


    /**
     * 方法二
     */
    private void downloadApkTwo(){
        AppUpdater.getInstance().getNetManager().get("http://59.110.162.30/app_updater_version.json", new NetCallback() {
            @Override
            public void success(String response) {

                // 解析json
                DownLoadBean bean = DownLoadBean.parse(response);
                if (bean == null){
                    MToast.showToast("下载失败");
                    return;
                }
                // 做版本匹配
                try {
                    long versionCode = Long.parseLong(bean.versionCode);

                    if (versionCode <= SystemUtil.getVersionCode(DownActivity.this)){
                        MToast.showToast("已是最新版本,无需更新!");
                        return;
                    }
                }catch (Exception e){
                    MToast.showToast("下载失败");
                    return;
                }
                // 如果需要更新
                // 弹框
                UpdateVersionShowDialog.show(DownActivity.this,bean);

            }

            @Override
            public void failed(Throwable throwable) {
                MToast.showToast("更新失败");
            }
        },DownActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUpdater.getInstance().getNetManager().cancel(this);
    }
}
