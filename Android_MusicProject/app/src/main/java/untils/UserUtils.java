package untils;

import activitys.LoginActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.example.classtwo.R;
import helps.RealmHelp;
import helps.UserHelper;
import models.UserModel;

import java.util.List;

public class UserUtils {

    public static boolean validateLogin(Context content, String phone,String pwd){
        // 精确手机验证
        if (!RegexUtils.isMobileExact(phone)){
            Toast.makeText(content,"无效的手机号",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(pwd)){
            Toast.makeText(content,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }

        // 手机号码是否存在 密码是否正确
        if (!userExistFromPhone(phone)){
            Toast.makeText(content,"当前手机号未注册,请前去注册",Toast.LENGTH_SHORT).show();
            return false;
        }

        RealmHelp realmH = new RealmHelp();
        boolean result = realmH.validateUser(phone,EncryptUtils.encryptMD5ToString(pwd));

        if (!result){
            Toast.makeText(content,"手机号或密码错误",Toast.LENGTH_SHORT).show();
            return false;
        }


        // 保存用户登录标记
         boolean isSave = SPUtils.saveUser(content,phone);
        if (!isSave){
            Toast.makeText(content,"系统错误,请稍后重试",Toast.LENGTH_SHORT).show();
            return false;
        }
        // 保存用户标记,在全局单例类中
        UserHelper.getInstance().setPhone(phone);
        realmH.setMusicSource(content);
        realmH.close();

        return true;
    }

    public static boolean validateRegister(Context content, String phone,String pwd1,String pwd2){
        // 精确手机验证
        if (!RegexUtils.isMobileExact(phone)){
            Toast.makeText(content,"无效的手机号",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(pwd1) || TextUtils.isEmpty(pwd2)){
            Toast.makeText(content,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!pwd1.equals(pwd2)){
            Toast.makeText(content,"请两次输入相同密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        // 用户当前输入用户号是否已经被注册
        if (userExistFromPhone(phone)){
            Toast.makeText(content,"手机号已注册",Toast.LENGTH_SHORT).show();
            return false;
        }
        UserModel userModel = new UserModel();
        userModel.setPhone(phone);
        userModel.setPassword(EncryptUtils.encryptMD5ToString(pwd1));
        saveUser(userModel);
        return true;
    }


    /** 保存用户到数据库
     * @param userModel
     */
    public static void saveUser(UserModel userModel ){
        RealmHelp realmHelp = new RealmHelp();
        realmHelp.saveUser(userModel);
        realmHelp.close();

    }

    public static boolean userExistFromPhone(String phone){
        boolean result = false;
        RealmHelp realmH = new RealmHelp();
        List<UserModel> alls = realmH.getAllUser();
         for (UserModel userModel : alls){
             if (userModel.getPhone().equals(phone)){
                 result = true;
                 break;
             }
         }
        realmH.close();
        return result;
    }


    /**
     * 修改密码
     * @param content 上下文
     * @param oldPwd 旧密码
     * @param newPwd1 新密码
     * @param newPwd2 确认新密码
     * @return
     */
    public static boolean validateChangePassword(Context content, String oldPwd,String newPwd1,String newPwd2){

        if (TextUtils.isEmpty(oldPwd)){
            Toast.makeText(content,"请输入旧密码",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(newPwd1) || TextUtils.isEmpty(newPwd2)){
            Toast.makeText(content,"请输入新密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!newPwd1.equals(newPwd2)){
            Toast.makeText(content,"请两次输入相同密码",Toast.LENGTH_SHORT).show();
            return false;
        }

        // 旧密码是否输入正确
        RealmHelp rm = new RealmHelp();
        UserModel userModel = rm.getUser();
        if (!userModel.getPassword().equals(EncryptUtils.encryptMD5ToString(oldPwd))){
            Toast.makeText(content,"原密码不正确",Toast.LENGTH_SHORT).show();
            return false;
        }
        rm.changePassword(EncryptUtils.encryptMD5ToString(newPwd1));
        rm.close();
        return true;
    }

    /** 退出登录
     * @param content
     */
    public static void logout(Context content){

         boolean isRemove = SPUtils.removeUser(content);
         if (!isRemove){
             Toast.makeText(content,"系统错误,请稍后重试",Toast.LENGTH_SHORT).show();
             return;
         }

         RealmHelp rm = new RealmHelp();
         rm.removeMusicSource(content);
         rm.close();

        Intent i = new Intent(content, LoginActivity.class);

        // 添加Intent标识符 清理task栈,并新生成一个task栈
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        content.startActivity(i);

        // 定义Activity跳转动画
        ((Activity)content).overridePendingTransition(R.anim.open_enter,R.anim.close_exit);
    }

    /**
     * 验证是否存在已登录用户
     * @param content
     * @return
     */
    public  static boolean validateuserLogin (Context content){
        return SPUtils.isLoginUser(content);
    }

}
