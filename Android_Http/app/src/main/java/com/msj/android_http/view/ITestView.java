package com.msj.android_http.view;

import com.msj.android_http.beans.CarInfo;
import com.msj.android_http.beans.QuestionBeanInfo;

import java.util.List;

public interface ITestView {
    void updateUI (List<CarInfo> list,List<String> answers);

//    void setBottomtipView (String count);
}
