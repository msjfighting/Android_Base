package helps;

import android.content.Context;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import migration.Migration;
import models.AlbumModel;
import models.MusicModel;
import models.MusicSourceModel;
import models.UserModel;
import untils.DataUtils;

import java.io.FileNotFoundException;
import java.util.List;

public class RealmHelp {

    private Realm mRealm;

     public RealmHelp () {
            mRealm = Realm.getDefaultInstance();
     }


    /**
     * 告诉Realm数据需要迁移,并且为Realm设置最新的配置
     */
     public static void migration(){
         RealmConfiguration cof =  getRealmConf();
         // Realm设置最新的配置
         Realm.setDefaultConfiguration(cof);
         try {
             Realm.migrateRealm(cof);
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }
     }

    /**
     * Realm数据库发生结构性变化(模型或模型中的字段发生了新增,修改,删除),我们需要对数据库进行迁移
     */
    private static RealmConfiguration getRealmConf(){

        return new RealmConfiguration.Builder()
                    .schemaVersion(1)
                    .migration(new Migration())
                    .build();
    }

    /**
     * 关闭Realm
     */
     public void close () {
         if (mRealm != null && !mRealm.isClosed()){
             mRealm.close();
         }
     }


    /**
     * 保存用户信息
     * @param usermodel
     */
     public void saveUser(UserModel usermodel){
         mRealm.beginTransaction();
//         mRealm.insert(usermodel);
         // 如果主键已经存在 则更新
         mRealm.insertOrUpdate(usermodel);
         mRealm.commitTransaction();

     }

    /**
     * @return 返回所有用户
     */
    public List<UserModel> getAllUser(){
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        RealmResults<UserModel> results = query.findAll();
        return  results;
    }

    /**
     * 判断用户是否存在
     * @param phone
     * @param pwd
     * @return
     */
    public boolean validateUser(String phone, String pwd){
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        query = query.equalTo("phone",phone).equalTo("password",pwd);
        UserModel userModel = query.findFirst();
        if (userModel != null){
            return true;
        }
        return false;
    }


    /**
     * @return 获取当前用户
     */
    public UserModel getUser(){
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        UserModel userModel = query.equalTo("phone", UserHelper.getInstance().getPhone()).findFirst();
       return userModel;
    }

    /**
     * 修改密码
     */
    public void changePassword(String pwd){
         UserModel userModel = getUser();
         mRealm.beginTransaction();
         userModel.setPassword(pwd);
         mRealm.commitTransaction();
    }

    /**
     * 1. 用户登录 存放数据
     * 2. 用户退出 删除数据
     */

    /**
     * 保存音乐源数据
     */
    public void setMusicSource (Context context){
       String musicSource = DataUtils.getJsonFromAssets(context,"DataSource.json");
       mRealm.beginTransaction();
       mRealm.createObjectFromJson(MusicSourceModel.class,musicSource);
       mRealm.commitTransaction();
    }

    /**
     * 删除音乐源数据
     */

    public void removeMusicSource (Context context){
        mRealm.beginTransaction();
        mRealm.delete(MusicSourceModel.class);
        mRealm.delete(MusicModel.class);
        mRealm.delete(AlbumModel.class);
        mRealm.commitTransaction();
    }

    /**
     * 返回音乐源数据
     */

    public MusicSourceModel getMusicSource(){
        return mRealm.where(MusicSourceModel.class).findFirst();
    }

    /**
     * 返回歌单
     */
    public AlbumModel getAlbum(String albumid){
        return mRealm.where(AlbumModel.class).equalTo("albumId",albumid).findFirst();

    }

    /**
     * 返回音乐
     * @param musicid
     * @return
     */
    public  MusicModel getMusic(String musicid){
        return mRealm.where(MusicModel.class).equalTo("musicId",musicid).findFirst();
    }

}
