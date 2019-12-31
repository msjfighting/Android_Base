package com.msj.android_project.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.Vibrator;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.widget.Toast;


import com.msj.android_project.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.DecimalFormat;

/**
 * @author lam
 * @date 2019/02/27
 */
public class DownApkService extends Service {
    private final int NotificationID = 0x10000;
    private NotificationManager mNotificationManager = null;
    private NotificationCompat.Builder builder;

    // 文件保存路径(如果有SD卡就保存SD卡,如果没有SD卡就保存到手机包名下的路径)
    private String APK_dir = "";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAPKDir();// 创建保存路径
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        // 接收Intent传来的参数:
        // 文件下载路径
        String APK_URL = intent.getStringExtra("apk_url");
        Log.i("DownApkService: ","DownApkService:url=" + APK_URL);

        DownFile(APK_URL, APK_dir + "zlhjhelpapp.apk");

        return super.onStartCommand(intent, flags, startId);
    }

    private void initAPKDir() {
        /**
         * 创建路径的时候一定要用[/],不能使用[\],但是创建文件夹加文件的时候可以使用[\].
         * [/]符号是Linux系统路径分隔符,而[\]是windows系统路径分隔符 Android内核是Linux.
         */
        if (isHasSdcard())// 判断是否插入SD卡
        {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                APK_dir = getExternalFilesDir("VersionChecker").getAbsolutePath() + "/";
            }else{
                APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/VersionChecker/"; // 保存到SD卡路径下
            }
        } else {
            APK_dir = getApplicationContext().getFilesDir().getAbsolutePath() + "/VersionChecker/"; // 保存到app的包名路径下
        }
        File destDir = new File(APK_dir);
        if (!destDir.exists()) {// 判断文件夹是否存在
            destDir.mkdirs();
        }
    }

    /**
     * @Description 判断是否插入SD卡
     */
    private boolean isHasSdcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * @param file_url    下载链接
     * @param target_name 保存路径
     */
    private void DownFile(String file_url, String target_name) {
        RequestParams params = new RequestParams(file_url);
        params.setSaveFilePath(target_name);  //设置下载后的文件保存的位置
        params.setAutoResume(true);  //设置是否在下载是自动断点续传
        params.setAutoRename(true);  //设置是否根据头信息自动命名文件
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                System.out.println("文件下载完成");
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                System.out.println(result.getPath());

                Uri uri ;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    // 配置 FileProvider
                    installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    uri = FileProvider.getUriForFile(DownApkService.this,getPackageName()+".fileprovider",new File(result.getPath()));

                }else{
                    uri = Uri.fromFile(new File(result.getPath()));
                }
                installIntent.setDataAndType(uri, "application/vnd.android.package-archive");


                PendingIntent mPendingIntent = PendingIntent.getActivity(DownApkService.this, 0, installIntent, 0);
                builder.setContentText("下载完成,请点击安装");
                builder.setContentIntent(mPendingIntent);
                mNotificationManager.notify(NotificationID, builder.build());
                // 震动提示
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                assert vibrator != null;
                vibrator.vibrate(250L);// 参数是震动时间(long类型)
                stopSelf();
                startActivity(installIntent);// 下载完成之后自动弹出安装界面
                mNotificationManager.cancel(NotificationID);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("文件下载失败");
                mNotificationManager.cancel(NotificationID);
                Toast.makeText(getApplicationContext(), "下载失败，请检查网络！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                System.out.println("文件下载结束，停止下载器");
            }

            @Override
            public void onFinished() {
                System.out.println("文件下载完成");
              //  EventBus.getDefault().post(new DownLoanEvent(DownLoanEvent.ACTION_Finish));
            }

            @Override
            public void onWaiting() {
                System.out.println("文件下载处于等待状态");
            }

            @Override
            public void onStarted() {
                Toast.makeText(getApplicationContext(), "开始后台下载更新文件...", Toast.LENGTH_SHORT).show();
                System.out.println("开始下载文件");
                String id = getPackageName();
                String name = "app";
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // 针对Android 8.0版本对于消息栏的限制，需要加入channel渠道这一概念
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  //Android 8.0以上
                    NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
                    Log.i("DownApkService", mChannel.toString());
                    mNotificationManager.createNotificationChannel(mChannel);
                    builder = new NotificationCompat.Builder(getApplicationContext());
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setTicker("正在下载新版本");
                    builder.setContentTitle(getApplicationName());
                    builder.setContentText("正在下载,请稍后...");
                    builder.setNumber(0);
                    builder.setChannelId(id);
                    builder.setAutoCancel(true);
                } else {    //Android 8.0以下
                    builder = new NotificationCompat.Builder(getApplicationContext());
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setTicker("正在下载新版本");
                    builder.setContentTitle(getApplicationName());
                    builder.setContentText("正在下载,请稍后...");
                    builder.setNumber(0);
                    builder.setAutoCancel(true);
                }

                mNotificationManager.notify(NotificationID, builder.build());
              //  EventBus.getDefault().post(new DownLoanEvent(DownLoanEvent.ACTION_Loding));
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                System.out.println("文件下载中");
                int x = Long.valueOf(current).intValue();
                int totalS = Long.valueOf(total).intValue();
                builder.setProgress(totalS, x, false);
                builder.setContentInfo(getPercent(x, totalS));
                mNotificationManager.notify(NotificationID, builder.build());
                //当前进度和文件总大小
                Log.i("DownApkService", "current：" + current + "，total：" + total);
            }
        });

    }

    /**
     * @param x     当前值
     * @param total 总值
     *              [url=home.php?mod=space&uid=7300]@return[/url] 当前百分比
     * @Description 返回百分之值
     */
    private String getPercent(int x, int total) {
        String result = "";// 接受百分比的值
        double x_double = x * 1.0;
        double tempresult = x_double / total;
        // 百分比格式，后面不足2位的用0补齐 ##.00%
        DecimalFormat df1 = new DecimalFormat("0.00%");
        result = df1.format(tempresult);
        return result;
    }

    /**
     * @return
     * @Description 获取当前应用的名称
     */
    private String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}