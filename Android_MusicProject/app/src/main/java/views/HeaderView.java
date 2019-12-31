package views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.classtwo.R;

public class HeaderView extends FrameLayout {

    private String title;
    private View view;
    private TextView tv_title;


    public HeaderView(Context context) {
        super(context);
        init(context,null);
    }

    public HeaderView( Context context,AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public HeaderView( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context content,AttributeSet attrs) {
        if (attrs == null) return;
        // 获取自定义属性
        TypedArray typesArray = content.obtainStyledAttributes(attrs, R.styleable.headerView);

        title = typesArray.getString(R.styleable.headerView_header_title);
        typesArray.recycle();

        // 自定义xml转view 绑定layout布局
        view = LayoutInflater.from(content).inflate(R.layout.header,HeaderView.this,false);
        tv_title = view.findViewById(R.id.header_title);

        // 布局关联属性
        tv_title.setText(title);

        addView(view);

    }
}
