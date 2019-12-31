package com.msj.android_http.api;

import com.google.gson.Gson;
import com.http.api.ApiUtil;
import com.msj.android_http.CardContants;
import com.msj.android_http.beans.CarInfo;
import com.msj.android_http.beans.QuestionBeanInfo;
import com.msj.android_http.beans.RankInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionSaveApi extends ApiUtil {
    public List<String> options = new ArrayList<>();

//    public QuestionSaveApi(String question_id,String answer) {
//        addParam("question_id",question_id);
//        addParam("answer",answer);
//    }

    @Override
    protected String getUrl() {
        return CardContants.URL+"/answers?key="+CardContants.APP_KEY;
    }

    @Override
    protected void parseData(JSONObject jsonObject) throws Exception {
        try {
//            JSONObject data = jsonObject.optJSONObject("result");
//            JSONObject info = data.optJSONObject("rank_info");
//            mCarInfo.is_correct = info.optString("is_correct");
//            mRankInfo.total_count = info.optString("total_count");
//            mRankInfo.correct_count = info.optString("correct_count");

            JSONObject data = jsonObject.optJSONObject("result");
            options.add("");
            options.add(data.getString("1"));
            options.add(data.getString("2"));
            options.add(data.getString("3"));
            options.add(data.getString("4"));
            options.add("");
            options.add("");
            options.add(data.getString("7"));
            options.add(data.getString("8"));
            options.add(data.getString("9"));
            options.add(data.getString("10"));
            options.add(data.getString("11"));
            options.add(data.getString("12"));
            options.add(data.getString("13"));
            options.add(data.getString("14"));
            options.add(data.getString("15"));
            options.add(data.getString("16"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    protected boolean isBackInMainThread(){
        return true;
    }
}
