package com.msj.android_project.utils;

import android.view.Gravity;
import android.widget.Toast;
import com.msj.android_project.MyApplication;

/**
 * @author：PengJunShan.
 * 描述：吐司
 */
public class MToast {

    /**
     * 吐司
     * @param content
     */
    public static void showToast(String content){
        if(content!=null&&content!="") {
            Toast toast = Toast.makeText(MyApplication.getContext(), content, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.setText(content);
            toast.show();
        }else {
        }
    }


    /**
     * 吐司
     * @param content
     */
    public static void showToastLong(String content){
        if(content!=null&&content!="") {
            Toast toast = Toast.makeText(MyApplication.getContext(), content, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.setText(content);
            toast.show();
        }else {
        }
    }

    /**
     * 防止多次吐司
     * @param content
     */
    public static void singleToast(String content){

        if(isFastClick()) {
            if(content!=null&&content!="") {
                Toast toast = Toast.makeText(MyApplication.getContext(), content, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.setText(content);
                toast.show();
            }else {
            }

        }

    }


    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

}
