package com.msj.android_http.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionBeanInfo implements Serializable {
    public String question_id;

    public String title;

    public List<String> options = new ArrayList<>();

    public String answer;

    public int type;

    public String option;

    public String explain;


}
