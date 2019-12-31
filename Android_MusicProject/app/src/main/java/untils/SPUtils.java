package untils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import constants.SPConstants;
import helps.UserHelper;

public class SPUtils {

    /**
     * 当用户登陆时 利用SharedPerences 保存用户的用户标记(手机号)
     * @param content
     * @param phone
     * @return
     */
    public static boolean saveUser (Context content,String phone){
       SharedPreferences sp = content.getSharedPreferences(SPConstants.SP_NAME_USER,Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sp.edit();
       editor.putString(SPConstants.SP_KEY_PHONE,phone);

       boolean result = editor.commit();

       return result;
    }

    /**
     * 验证是否存在已登录用户
     * @param content
     * @return
     */
    public static boolean isLoginUser (Context content){
        boolean result = false;
        SharedPreferences sp = content.getSharedPreferences(SPConstants.SP_NAME_USER,Context.MODE_PRIVATE);
         String phone = sp.getString(SPConstants.SP_KEY_PHONE,"");
         if (!TextUtils.isEmpty(phone)){
             result = true;
             UserHelper.getInstance().setPhone(phone);
         }
        return result;
    }


    public static boolean removeUser(Context context){
        SharedPreferences sp = context.getSharedPreferences(SPConstants.SP_NAME_USER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(SPConstants.SP_KEY_PHONE);
        return editor.commit();
    }

}
