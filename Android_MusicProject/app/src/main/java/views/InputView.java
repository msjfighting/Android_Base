package views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.classtwo.R;

/*
  input_icon : 输入框前面的图标
  input_hint : 输入框提示内容
  input_pwd : 输入内容是否以密文形式展示
  input_type : 输入内容真是方式
 */
public class InputView extends FrameLayout {
    private int inputIcon;
    private String inputHint;
    private boolean isPwd;
    private int inputType;

    private View mView;
    private ImageView mIcon;
    private EditText mEdit;

    public InputView( Context context) {
        super(context);
        init(context,null);
    }

    public InputView(Context context,AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public InputView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InputView(Context context,AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }


    private void init(Context content,AttributeSet attrs){
        if (attrs == null) return;

        // 获取自定义属性
       TypedArray typesArray = content.obtainStyledAttributes(attrs, R.styleable.inputView);
       inputIcon = typesArray.getResourceId(R.styleable.inputView_input_icon,R.mipmap.logo);
       inputHint = typesArray.getString(R.styleable.inputView_input_hint);
       isPwd = typesArray.getBoolean(R.styleable.inputView_ispassword,false);
       inputType = typesArray.getInt(R.styleable.inputView_input_type,InputType.TYPE_CLASS_TEXT );
       // 释放
       typesArray.recycle();

       // 自定义xml转view 绑定layout布局
       mView = LayoutInflater.from(content).inflate(R.layout.input_view,InputView.this,false);

       mIcon =  mView.findViewById(R.id.login_icon);
       mEdit =  mView.findViewById(R.id.login_num);

       // 布局关联属性
        mIcon.setImageResource(inputIcon);
        mEdit.setHint(inputHint);
        mEdit.setInputType(isPwd ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_PHONE);
//        mEdit.setInputType(inputType);
        addView(mView);
    }

    /**
     * 返回输入的内容
     * @return
     */
    public  String getInputStr(){
        return mEdit.getText().toString().trim();
    }
}
