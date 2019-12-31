package helps;


/**
 *  1, 用户登录
 *     1.1 当用户登陆时 利用SharedPerences 保存用户的用户标记
 *     1.2 利用全局单例类UserHelper 保存登陆用户信息
 *         1.2.1 用户登录之后保存用户登录信息 用户重新打开应用程序的时候,检测SharedPerences中是否存在用户标记,如果存在进入主页 否则进入登录页面
 *  2, 用户退出
 */
public class UserHelper {

    private  static UserHelper instance;

    private UserHelper () {}

    public static UserHelper getInstance(){
        if (instance == null){
            synchronized (UserHelper.class){
                if (instance == null){
                    instance = new UserHelper();
                }
            }
        }
        return instance;
    }

    private String phone;
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }





}
