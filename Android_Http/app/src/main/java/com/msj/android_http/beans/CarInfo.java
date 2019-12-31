package com.msj.android_http.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarInfo implements Serializable {
    public String id;

    public String question;
    public String answer;
    public String item1;
    public String item2;
    public String item3;
    public String item4;
    public String explains;
    public String url;
    public List<String> options = new ArrayList<>();
}
