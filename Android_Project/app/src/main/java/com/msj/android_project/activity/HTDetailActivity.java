package com.msj.android_project.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.msj.android_project.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class HTDetailActivity extends BaseActivity implements DownloadFile.Listener {


    @BindView(R.id.remote_pdf_root)
    RelativeLayout remotePdfRoot;
    @BindView(R.id.pdfViewPager)
    PDFViewPager pdfViewPager;

    private RemotePDFViewPager remotePDFViewPager;
    private PDFPagerAdapter adapter;

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htdetail);
        initNavBar(true,"");
        ButterKnife.bind(this);
        mUrl = "http://10.215.85.11:8080/fileManage/getFile?fileCode=20768c5e26de423386a7316fc27f628c";
        setDownloadListener(mUrl);
    }

    /*设置监听*/
    protected void setDownloadListener(String url) {
        final DownloadFile.Listener listener = this;
        remotePDFViewPager = new RemotePDFViewPager(this,url, listener);
        remotePDFViewPager.setId(R.id.pdfViewPager);
    }

    /*加载成功调用*/
    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        updateLayout();
    }

    /*更新视图*/
    private void updateLayout() {
        remotePdfRoot.removeAllViewsInLayout();
        remotePdfRoot.addView(remotePDFViewPager, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    /*加载失败调用*/
    @Override
    public void onFailure(Exception e) {
        // 加载失败,跳转至浏览器加载
        openPDFInBrowser();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
    }


    private  void openPDFInBrowser() {
        Uri uri = Uri.parse(mUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.w("error", "Activity was not found for intent, " + intent.toString());
        }
    }


}
