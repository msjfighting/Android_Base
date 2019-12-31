package com.msj.android_http.api;

import com.google.gson.Gson;
import com.http.api.ApiUtil;
import com.msj.android_http.CardContants;
import com.msj.android_http.beans.CarInfo;
import com.msj.android_http.beans.QuestionBeanInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryQuestionGetApi extends ApiUtil {
    public List<CarInfo> list = new ArrayList<>();

    @Override
    protected String getUrl() {
        return CardContants.URL+"/query?subject=1&model=c1&key="+CardContants.APP_KEY;
    }

    @Override
    protected void parseData(JSONObject jsonObject) throws Exception {
        try {
            JSONArray array = jsonObject.optJSONArray("result");
            if (list != null){
                list.clear();
            }
            for (int i = 0; i < array.length(); i++) {
                CarInfo questionBeanInfo = new Gson().fromJson(array.getString(i),CarInfo.class);
                list.add(questionBeanInfo);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
