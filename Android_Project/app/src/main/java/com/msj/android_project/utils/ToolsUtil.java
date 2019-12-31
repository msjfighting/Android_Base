package com.msj.android_project.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import androidx.core.app.ActivityCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.msj.android_project.view.DownLoadLog;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static com.lxj.xpopup.enums.PopupAnimation.ScaleAlphaFromCenter;
import static com.msj.android_project.activity.MainActivity.REQUEST_READ_PHONE_STATE;

public class ToolsUtil {
    private static KeyStore keyStore;
    private static Cipher cipher;
    private static final String DEFAULT_KEY_NAME = "default_key";
    public interface RequestPermissionListenter {
        void requestPermissionSuccess();

        // void requestPermissionError(Permission permission);
    }
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    /**
     * 获取设备号
     * @return
     */
    public static String getIMEI(Context context) {
        String result = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        if (null != tm) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
            }else {
                result = tm.getSubscriberId();
                if (result == null || result.length() == 0)
                    result = tm.getDeviceId();
                if (result == null || result.length() == 0)
                    result = tm.getLine1Number();
                if (result == null || result.length() == 0)
                    result = tm.getSimSerialNumber();
                if (result == null || result.length() == 0) {
                    result = "";
                }
            }

        }
        return result;
    }


    /**
     *判断是否支持指纹识别
     */
    public static boolean supportFingerprint(Context mContext) {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(mContext, "您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            KeyguardManager keyguardManager = mContext.getSystemService(KeyguardManager.class);
            FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(mContext);
            if (!fingerprintManager.isHardwareDetected()) {
                Toast.makeText(mContext, "您的手机不支持指纹功能", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!keyguardManager.isKeyguardSecure()) {
                Toast.makeText(mContext, "您还未设置锁屏，请先设置锁屏并添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(mContext, "您至少需要在系统设置中添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @TargetApi(23)
    public static void initKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyGenerator keyGenerator = KeyGenerator
                    .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(DEFAULT_KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @TargetApi(23)
    public static Cipher initCipher() {
        try {
            SecretKey key = (SecretKey) keyStore.getKey(DEFAULT_KEY_NAME, null);
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cipher;
    }

    public static void setVibrate(Context context) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(300);
    }

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 访问assets文件下的图片资源
     * @param path  文件名
     */
    public static List<Bitmap> deepFile(Context context, String path) {
        List<Bitmap> emojidata = new ArrayList<>();
        try {
            //获取指定目录下的所有文件名称
            String[] str = context.getAssets().list(path);
            //emoji文件夹下面有两个文件，所有该数组的长度为1，str[0]是default文件夹，str[1]是xml文件；
            if (str.length > 0) {//如果是目录
                for (String string : str) {
                    Bitmap bitmap = getImageFromAssetsFile(context,path + "/" + string);
                    emojidata.add(bitmap);
                }
            } else {//如果是文件
                if (!path.endsWith(".png")) {
                    return emojidata;
                }
                emojidata.add(getImageFromAssetsFile(context,path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emojidata;
    }

    /**
     * 从Assets中读取图片
     * @param fileName
     * @return
     */
    private static Bitmap getImageFromAssetsFile(Context context,String fileName)
    {
        Bitmap image = null;
        try
        {
            InputStream is = context.getAssets().open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return image;

    }

    /**
     * 更新应用
     * @param activity
     */
    public static void updateAPP(Context activity,String message) {
        DownLoadLog pop = new DownLoadLog(activity,message);
        new XPopup.Builder(activity)
                .popupAnimation(ScaleAlphaFromCenter)
                .autoOpenSoftInput(false)
                .dismissOnTouchOutside(false)
                .dismissOnBackPressed(false)
                .asCustom(pop)
                .show();
    }


    public static void requestStoragePermission(Context context, RequestPermissionListenter requestListenter) {
        RxPermissions.getInstance(context)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        if (requestListenter != null) {
                            requestListenter.requestPermissionSuccess();
                        }
                    } else {
                        ToolsUtil.goSystem(context);
                    }

                });
    }
    public static void goSystem(Context context) {
        new XPopup.Builder(context)
                .autoOpenSoftInput(false)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .asConfirm("提示", "由于您拒绝授权,将会暂停后续使用,请前去设置页面开启权限",
                        "取消", "确定", new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                //设置去向意图
                                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                                intent.setData(uri);
                                //发起跳转
                                context.startActivity(intent);
                            }
                        }, null, true)
                .show();
    }

    public static int getScreenWidth(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

}
