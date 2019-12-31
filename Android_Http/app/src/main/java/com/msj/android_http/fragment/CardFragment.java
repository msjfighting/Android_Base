package com.msj.android_http.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.http.api.ApiListener;
import com.http.api.ApiUtil;
import com.msj.android_http.MainActivity;
import com.msj.android_http.R;
import com.msj.android_http.api.QuestionSaveApi;
import com.msj.android_http.beans.CarInfo;
import com.msj.android_http.beans.QuestionBeanInfo;
import com.msj.android_http.view.ButtonSelectView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CardFragment  extends Fragment {

    private View mRootView;
    private TextView contentTitle;
    private TextView tv_bottom_text;
    private ImageView contentImg;
    private ButtonSelectView option1,option2,option3,option4;

    private QuestionBeanInfo mCurrentInfo;

    private LinearLayout mLinearLayout;
    private TextView layout_text;

    private MainActivity mMainActivity;

    private CarInfo mCarInfo;
    private List<String> mAnswers;

    private List<String> options = new ArrayList<>();
    private boolean isSelect;

    public static CardFragment newInstance(CarInfo info, List<String> answers){
        CardFragment fragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info",info);
        bundle.putSerializable("answers", (Serializable) answers);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frgment_layout,container,false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        mCarInfo = (CarInfo) getArguments().getSerializable("info");
        mAnswers = (List<String>) getArguments().getSerializable("answers");
        contentTitle.setText(mCarInfo.question);


//        int type = mCurrentInfo.type;
//        int answer = Integer.valueOf(mCarInfo.answer);
//        int useroption = 1;
//
//        if (!TextUtils.isEmpty(mCurrentInfo.option)){
//            useroption = Integer.valueOf(mCurrentInfo.option);
//        }
//
//        if (type == 1){
//            mLinearLayout.setVisibility(View.VISIBLE);
//            layout_text.setText(mCurrentInfo.explain);
//        }else {
//            mLinearLayout.setVisibility(View.GONE);
//        }
        mLinearLayout.setVisibility(View.GONE);
        contentImg.setVisibility(View.GONE);

        if (TextUtils.isEmpty(mCarInfo.item3) && TextUtils.isEmpty(mCarInfo.item4)){
            option1.setText("正确");
            option2.setText("错误");
            option3.setVisibility(View.GONE);
            option4.setVisibility(View.GONE);
            isSelect = true;
        }else{
            isSelect = false;
            int count = 0;
            if (TextUtils.isEmpty(mCarInfo.item1)){
                count ++;
            }else if (TextUtils.isEmpty(mCarInfo.item2)){
                count ++;
            }
            if (count ==1){
                option4.setVisibility(View.GONE);
            }else if (count == 2){
                option3.setVisibility(View.GONE);
                option4.setVisibility(View.GONE);
            }else{
                option3.setVisibility(View.VISIBLE);
                option4.setVisibility(View.VISIBLE);
            }

            option1.setText(mCarInfo.item1);
            option2.setText(mCarInfo.item2);
            option3.setText(mCarInfo.item3);
            option4.setText(mCarInfo.item4);
        }

        if (!TextUtils.isEmpty(mCarInfo.url)){
            contentImg.setVisibility(View.VISIBLE);
            Glide.with(mMainActivity).load(mCarInfo.url).into(contentImg);
        }

        option1.setListener(new ButtonSelectView.onButtonSelectClickListener() {
            @Override
            public void onClick() {
                answerInfo("A");
            }
        });
        option2.setListener(new ButtonSelectView.onButtonSelectClickListener() {
            @Override
            public void onClick() {
                answerInfo("B");
            }
        });
        option3.setListener(new ButtonSelectView.onButtonSelectClickListener() {
            @Override
            public void onClick() {
                answerInfo("C");
            }
        });
        option4.setListener(new ButtonSelectView.onButtonSelectClickListener() {
            @Override
            public void onClick() {
                answerInfo("D");
            }
        });


//        if (type == 1){
//            if (answer == 1){
//                option1.setIcon(R.mipmap.img_test_right);
//                option2.setIcon(R.mipmap.img_test_worn);
//            }else{
//                option2.setIcon(R.mipmap.img_test_right);
//                option1.setIcon(R.mipmap.img_test_worn);
//            }
//            if (useroption == 1){
//                option1.setSelect(true);
//                option2.setSelect(false);
//            }else {
//                option2.setSelect(true);
//                option1.setSelect(false);
//            }
//        }
    }

    private void saveOptionInfo(final int option){
//        new QuestionSaveApi(mCurrentInfo.question_id,String.valueOf(option)).post(new ApiListener() {
//            @Override
//            public void success(ApiUtil api) {
//                QuestionSaveApi api1 = (QuestionSaveApi)api;
//                boolean iscorrect = api1.mRankInfo.is_correct.equals("1");
//                handleButtonSelectView(option,iscorrect);
//                mLinearLayout.setVisibility(View.VISIBLE);
//                layout_text.setText(mCurrentInfo.explain);
//
//                mMainActivity.setBottomtipView(api1.mRankInfo.correct_count);
//
//            }
//
//            @Override
//            public void failure(ApiUtil api) {
//
//            }
//        });
    }

    private void answerInfo(String answer){
        //todo 判断是否选择正确
       if (isSelect){
           // 选择题
           if (answer.equals("A") && mCarInfo.answer.equals("1")){
               option1.setSelect(true);
               option1.setIcon(R.mipmap.img_test_right);
               mMainActivity.startRain();
           }else if (answer.equals("B") && mCarInfo.answer.equals("2")){
               option2.setSelect(true);
               option2.setIcon(R.mipmap.img_test_right);
               mMainActivity.startRain();
           }else{
               if (answer.equals("A")){
                   option1.setSelect(false);
                   option1.setIcon(R.mipmap.img_test_worn);

                   option2.setSelect(true);
                   option2.setIcon(R.mipmap.img_test_right);
               }else{
                   option1.setSelect(true);
                   option1.setIcon(R.mipmap.img_test_right);

                   option2.setSelect(false);
                   option2.setIcon(R.mipmap.img_test_worn);
               }
               mLinearLayout.setVisibility(View.VISIBLE);
               layout_text.setText(mCarInfo.explains);
           }
       }else{
           // 多选题
           options.add(answer);
           corrct();
       }


    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }

    private void corrct(){
        String correct = mAnswers.get(Integer.valueOf(mCarInfo.answer));
        for (int i = 0; i < options.size(); i++) {
            String str = options.get(i);
            if (correct.contains(str)){
                mMainActivity.startRain();
                if (str.equals("A")){
                    option1.setSelect(true);
                    option1.setIcon(R.mipmap.img_test_right);
                }else if (str.equals("B")){
                    option2.setSelect(true);
                    option2.setIcon(R.mipmap.img_test_right);
                }else if (str.equals("C")){
                    option3.setSelect(true);
                    option3.setIcon(R.mipmap.img_test_right);
                }else if (str.equals("D")){
                    option4.setSelect(true);
                    option4.setIcon(R.mipmap.img_test_right);
                }

            }else{
                if (str.equals("A")){
                    option1.setSelect(false);
                    option1.setIcon(R.mipmap.img_test_worn);
                    if (correct.contains("B")){
                        option2.setSelect(true);
                        option2.setIcon(R.mipmap.img_test_right);
                    }else if (correct.contains("C")){
                        option3.setSelect(true);
                        option3.setIcon(R.mipmap.img_test_right);
                    }else if (correct.contains("D")){
                        option4.setSelect(true);
                        option4.setIcon(R.mipmap.img_test_right);
                    }

                }else if (str.equals("B")){
                    option2.setSelect(false);
                    option2.setIcon(R.mipmap.img_test_worn);

                    if (correct.contains("A")){
                        option1.setSelect(true);
                        option1.setIcon(R.mipmap.img_test_right);
                    }else if (correct.contains("C")){
                        option3.setSelect(true);
                        option3.setIcon(R.mipmap.img_test_right);
                    }else if (correct.contains("D")){
                        option4.setSelect(true);
                        option4.setIcon(R.mipmap.img_test_right);
                    }

                }else if (str.equals("C")){
                    option3.setSelect(false);
                    option3.setIcon(R.mipmap.img_test_worn);
                    if (correct.contains("A")){
                        option1.setSelect(true);
                        option1.setIcon(R.mipmap.img_test_right);
                    }else if (correct.contains("B")){
                        option2.setSelect(true);
                        option2.setIcon(R.mipmap.img_test_right);
                    }else if (correct.contains("D")){
                        option4.setSelect(true);
                        option4.setIcon(R.mipmap.img_test_right);
                    }
                }else if (str.equals("D")){
                    option4.setSelect(false);
                    option4.setIcon(R.mipmap.img_test_worn);
                    if (correct.contains("A")){
                        option1.setSelect(true);
                        option1.setIcon(R.mipmap.img_test_right);
                    }else if (correct.contains("B")){
                        option2.setSelect(true);
                        option2.setIcon(R.mipmap.img_test_right);
                    }else if (correct.contains("C")){
                        option3.setSelect(true);
                        option3.setIcon(R.mipmap.img_test_right);
                    }
                }
                mLinearLayout.setVisibility(View.VISIBLE);
                layout_text.setText(mCarInfo.explains);
            }
        }

    }

    private void handleButtonSelectView(int option, boolean isCorrect){
        int rightOption;
        if (option == 1){
            if (isCorrect){
                rightOption = 1;
            }else{
                rightOption = 2;
            }
            option1.setSelect(true);
            option2.setSelect(false);
        }else{
            if (isCorrect){
                rightOption = 2;
            }else{
                rightOption = 1;
            }
            option2.setSelect(true);
            option1.setSelect(false);
        }

        if (rightOption == 1){
            option1.setIcon(R.mipmap.img_test_right);
            option2.setIcon(R.mipmap.img_test_worn);
        }else{
            option2.setIcon(R.mipmap.img_test_right);
            option1.setIcon(R.mipmap.img_test_worn);
        }
    }

    private void initView(){
        contentTitle = mRootView.findViewById(R.id.contentTitle);
        option1 = mRootView.findViewById(R.id.first_option_layout);
        option2 = mRootView.findViewById(R.id.second_option_layout);

        option3 = mRootView.findViewById(R.id.thrid_option_layout);
        option4 = mRootView.findViewById(R.id.four_option_layout);

        mLinearLayout = mRootView.findViewById(R.id.tip_layout);
        layout_text = mRootView.findViewById(R.id.tip_text);

        contentImg = mRootView.findViewById(R.id.contentImg);
//        tv_bottom_text = mRootView.findViewById(R.id.tv_bottom_text);
    }
}
