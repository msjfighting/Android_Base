package com.msj.android_project.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.msj.android_project.R;
import com.msj.android_project.adapter.NormalFilePickAdapter;
import com.msj.android_project.bean.PDFFile;
import com.msj.android_project.utils.Constants;
import com.msj.android_project.utils.MToast;
import com.msj.android_project.utils.SystemUtil;
import com.msj.android_project.view.DividerListItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent Woo
 * Date: 2016/10/26
 * Time: 10:14
 */

public class NormalFilePickActivity extends BaseActivity {
    private int mMaxNumber = 9;
    private int mCurrentNumber = 0;
    private RecyclerView mRecyclerView;
    private NormalFilePickAdapter mAdapter;
    private List<PDFFile> mList =  new ArrayList<>();
    private ArrayList<PDFFile> mSelectedList = new ArrayList<>();

    private TextView tv_count;
    private RelativeLayout rl_done;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vw_activity_file_pick);

        tv_count =  findViewById(R.id.tv_count);
        tv_count.setText(mCurrentNumber + "/" + mMaxNumber);
        mRecyclerView =  findViewById(R.id.rv_file_pick);
        rl_done =  findViewById(R.id.rl_done);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerListItemDecoration(this, LinearLayoutManager.VERTICAL, R.drawable.vw_divider_rv_file));


        SystemUtil.requestStoragePermission(this, new SystemUtil.RequestPermissionListenter() {
            @Override
            public void requestPermissionSuccess() {

                getAllFiles(new File("/mnt/sdcard/"));
                initView();

            }
        });
    }

    private void initView() {
        mAdapter = new NormalFilePickAdapter(this,mList ,mMaxNumber);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnSelectStateListener(new NormalFilePickAdapter.OnSelectStateListener() {
            @Override
            public void OnSelectStateChanged(boolean state, PDFFile file) {
                if (state) {
                    mSelectedList.add(file);
                    mCurrentNumber++;
                } else {
                    mSelectedList.remove(file);
                    mCurrentNumber--;
                }
                tv_count.setText(mCurrentNumber + "/" + mMaxNumber);
            }
        });

        rl_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedList.size() <= 0){
                    MToast.showToast("请选择文件");
                    return;
                }
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(Constants.RESULT_PICK_FILE, mSelectedList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void onBackClick(View view) {
        finish();
    }

    private void getAllFiles(File root){
        File[] files = root.listFiles();
        if(files != null){
            for (File f : files){
                if(f.isDirectory()){
                    getAllFiles(f);
                }
                else{
                    PDFFile myFile = new PDFFile();
                    if(f.getName().endsWith(".pdf")){
                        myFile.setSelected(false);
                        myFile.setFileName(f.getName());
                        myFile.setFilePath(f.getPath());
                        mList.add(myFile);
                    }
                }
            }
        }
    }
}
