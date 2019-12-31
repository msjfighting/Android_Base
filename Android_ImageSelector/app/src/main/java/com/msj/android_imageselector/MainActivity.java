package com.msj.android_imageselector;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.msj.android_imageselector.bean.FolderBean;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private TextView mDirName,mDirCount;
    private RelativeLayout mBottomLy;
    private GridView mGridView;
    private ImageAdapter mImageAdapter;

    private List<String> mImgs;
    private File mCurrentDir;
    private  int mMaxCount;

    private List<FolderBean> mFodlers = new ArrayList<>();

    private ProgressDialog mProgressDialog;

    private ListImageDirPopupWindow mDirPopupWindow;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 0;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x110){
                mProgressDialog.dismiss();

                data2View();

                initDirPopupWindow();
            }
        }
    };

    protected void initDirPopupWindow() {
        mDirPopupWindow = new ListImageDirPopupWindow(this,mFodlers);
        mDirPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });

        mDirPopupWindow.setmListener(new ListImageDirPopupWindow.OnDirSelectListener() {
            @Override
            public void OnDirSelectListener(FolderBean bean) {
                // 更新文件夹
                mCurrentDir = new File(bean.getDir());
                // 更新图片
              mImgs = Arrays.asList(mCurrentDir.list(new FilenameFilter() {
                  @Override
                  public boolean accept(File file, String filename) {
                      if (filename.endsWith("jpg") || filename.endsWith(".jpeg") || filename.endsWith("png")) {
                          return true;
                      }
                      return false;
                  }
              }));

              mImageAdapter = new ImageAdapter(MainActivity.this,mImgs,mCurrentDir.getAbsolutePath());
              mGridView.setAdapter(mImageAdapter);
              mDirCount.setText(mImgs.size()+"");
              mDirName.setText(bean.getName());
              // 隐藏
              mDirPopupWindow.dismiss();
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       initView();

       initData();

       initEvent();
    }

    private void data2View() {
        if (mCurrentDir == null){
            Toast.makeText(this,"未扫描到任何图片!",Toast.LENGTH_SHORT).show();
            return;
        }

        mImgs = Arrays.asList(mCurrentDir.list());
        mImageAdapter = new ImageAdapter(this,mImgs,mCurrentDir.getAbsolutePath());
        mGridView.setAdapter(mImageAdapter);


        mDirCount.setText(mMaxCount+"");
        mDirName.setText(mCurrentDir.getName());

    }

    private void initView() {
        mGridView = findViewById(R.id.id_gridView);

        mDirName = findViewById(R.id.id_dir_name);

        mDirCount = findViewById(R.id.id_dir_count);

        mBottomLy = findViewById(R.id.id_bottom_ly);
    }

    /**
     * 利用ContentProvide扫描手机中的图片
     */
    private void initData(){
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this,"当前存储卡不可用!",Toast.LENGTH_SHORT).show();
            return;
        }
        mProgressDialog = ProgressDialog.show(this,null,"正在加载...");

        new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {

                int hasWriteContactsPermisson = checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(hasWriteContactsPermisson != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
                    return;
                }
                // 此操作会访问外部相册 涉及到权限问题
                Uri mImgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                ContentResolver cr = MainActivity.this.getContentResolver();

                Cursor cursor = cr.query(mImgUri, null, MediaStore.Images.Media.MIME_TYPE
                        + " = ? "+" or " + MediaStore.Images.Media.MIME_TYPE
                        + " = ?", new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED);

                Set<String> mDirPaths = new HashSet<>();
            while (cursor.moveToNext()){
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                File parentFile = new File(path).getParentFile();
                if (parentFile == null){
                    continue;
                }
                String dirPath = parentFile.getAbsolutePath();
                FolderBean folderBean = null;
                if (mDirPaths.contains(dirPath)){
                    continue;
                }else{
                    mDirPaths.add(dirPath);
                    folderBean = new FolderBean();
                    folderBean.setDir(dirPath);
                    folderBean.setFirstImgPath(path);
                }

                if (parentFile.list() == null)
                     continue;

                int picSize = parentFile.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File file, String filename) {
                        if (filename.endsWith("jpg") || filename.endsWith(".jpeg")|| filename.endsWith("png")){
                            return true;
                        }
                        return false;
                    }
                }).length;
                folderBean.setCount(picSize);
                mFodlers.add(folderBean);

                if (picSize > mMaxCount){
                    mMaxCount = picSize;
                    mCurrentDir = parentFile;
                }
            }
            cursor.close();

            // 通知handle扫描图片完成
            mHandler.sendEmptyMessage(0x110);
            }
        }.start();

    }

    private void initEvent(){
        mBottomLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDirPopupWindow.setAnimationStyle(R.style.dir_popupwindow_anim);
                mDirPopupWindow.showAsDropDown(mBottomLy,0,0);
                lightOff();
            }
        });
    }

    /**
     * 内容区域变量
     */
    private void lightOn() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }

    /**
     * 内容区域变暗
     */
    private void lightOff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = .3f;
        getWindow().setAttributes(lp);
    }


}
