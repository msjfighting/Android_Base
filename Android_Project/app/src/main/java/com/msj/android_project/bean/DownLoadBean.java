package com.msj.android_project.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DownLoadBean implements Serializable {
    public String title;
    public String content;
    public String url;
    public String md5;
    public String versionCode;

    public static DownLoadBean parse(String response) {
        try {
            JSONObject reJson = new JSONObject(response);
          String title = reJson.optString("title");
            String content = reJson.optString("content");
            String url = reJson.optString("url");
            String md5 = reJson.optString("md5");
            String versionCode = reJson.optString("versionCode");
            DownLoadBean bean = new DownLoadBean();
            bean.title = title;
            bean.content = content;
            bean.url = url;
            bean.md5 = md5;
            bean.versionCode = versionCode;
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
