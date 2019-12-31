package com.msj.android_project.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class BillBean implements Serializable {
    public String date;
    @SerializedName("obj")
    public ArrayList<BillDetailBean> lists;

    @Override
    public String toString() {
        return "BillBean{" +
                "date='" + date + '\'' +
                ", lists=" + lists.toString() +
                '}';
    }
}
