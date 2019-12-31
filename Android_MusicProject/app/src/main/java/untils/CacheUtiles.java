package untils;

import android.content.Context;
import android.content.SharedPreferences;

/*
 作用: 缓存软件的一些参数和数据
 */
public class CacheUtiles {
    /*
        获取缓存软件数据
     */
    public static boolean getBoolean(Context content, String key) {
        SharedPreferences sp = content.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }

    /*
       保存软件参数
     */
    public static void setBoolean(Context content, String key, boolean value) {
        SharedPreferences sp = content.getSharedPreferences("atguigu",Context.MODE_PRIVATE);

        sp.edit().putBoolean(key,value).commit();
    }
}
