package com.msj.android_project.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.msj.android_project.R;
import com.msj.android_project.eventbus.MessageEvent;
import com.msj.android_project.eventbus.StickyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventbusActivity extends BaseActivity {
    @BindView(R.id.id_result)
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        ButterKnife.bind(this);
        initNavBar(true,"Eventbus");

        // 注册广播
        EventBus.getDefault().register(this);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解注册
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    // 接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(MessageEvent event){
        result.setText(event.name);
    }

    @OnClick({R.id.id_eventbus,R.id.id_other})
    public void eventbus(View view){
        switch (view.getId()){
            case R.id.id_eventbus:
                Intent intent = new Intent(this, EventBusSendActivity.class);
                this.startActivity(intent);
                break;
            case R.id.id_other:
                // 发送粘性事件
                EventBus.getDefault().postSticky(new StickyEvent("我是粘性事件"));
                Intent intent1 = new Intent(this, EventBusSendActivity.class);
                this.startActivity(intent1);
                break;
        }
    }
}
