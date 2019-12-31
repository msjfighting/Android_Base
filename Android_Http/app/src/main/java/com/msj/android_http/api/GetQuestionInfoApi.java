package com.msj.android_http.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.http.api.ApiUtil;
import com.msj.android_http.CardContants;
import com.msj.android_http.beans.QuestionBeanInfo;

import org.json.JSONObject;

public class GetQuestionInfoApi  extends ApiUtil {
    public QuestionBeanInfo mInfo = new QuestionBeanInfo();

    @Override
    protected String getUrl() {
        return CardContants.URL+"/getQuestion";
    }

    @Override
    protected void parseData(JSONObject jsonObject) throws Exception {
        try {
            JSONObject data = jsonObject.optJSONObject("data");
            JSONObject info = data.optJSONObject("info");
            mInfo = new Gson().fromJson(info.toString(),QuestionBeanInfo.class);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
