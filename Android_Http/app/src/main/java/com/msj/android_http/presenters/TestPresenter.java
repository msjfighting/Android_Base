package com.msj.android_http.presenters;

import android.content.Context;
import android.view.View;

import com.http.Util.Util;
import com.http.api.ApiListener;
import com.http.api.ApiUtil;
import com.msj.android_http.api.GetQuestionInfoApi;
import com.msj.android_http.api.HistoryQuestionGetApi;
import com.msj.android_http.api.QuestionSaveApi;
import com.msj.android_http.beans.CarInfo;
import com.msj.android_http.beans.QuestionBeanInfo;
import com.msj.android_http.view.ITestView;

import java.util.List;

public class TestPresenter {

    private ITestView mITestView;
    private Context mContext;

    private List<CarInfo> mHistoryList;
    private QuestionBeanInfo mCurrentInfo;

    private List<String> answers;


    public TestPresenter(ITestView iTestView) {
        mContext = (Context)iTestView;

        mITestView = iTestView;
    }

    public void getData() {
        if(Util.hasNetwork(mContext)) {
            getHistory();
        }else{
            refreshData();
        }
    }

    private void getHistory(){
        new HistoryQuestionGetApi().get(mContext, new ApiListener() {
            @Override
            public void success(ApiUtil api) {
               HistoryQuestionGetApi api1 = (HistoryQuestionGetApi) api;
                mHistoryList = api1.list;
//                getCurrent();
                getAnswers();
            }

            @Override
            public void failure(ApiUtil api) {

            }
        });
    }

    private void getAnswers() {
        new QuestionSaveApi().get(mContext, new ApiListener() {
            @Override
            public void success(ApiUtil api) {
                QuestionSaveApi api1 = (QuestionSaveApi)api;
                answers = api1.options;
                refreshData();

            }

            @Override
            public void failure(ApiUtil api) {

            }
        });
    }

//    private void getCurrent(){
//        new GetQuestionInfoApi().get(mContext, new ApiListener() {
//            @Override
//            public void success(ApiUtil api) {
//                GetQuestionInfoApi api1 = (GetQuestionInfoApi) api;
//                mCurrentInfo = api1.mInfo;
//                mHistoryList.add(0,mCurrentInfo);
//                refreshData();
//            }
//
//            @Override
//            public void failure(ApiUtil api) {
//
//            }
//        });
//    }

    private void refreshData(){
        if (mITestView != null)
        mITestView.updateUI(mHistoryList,answers);
    }
}
