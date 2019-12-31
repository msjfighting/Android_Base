package com.msj.android_project.activity;

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

public class EventBusSendActivity extends BaseActivity {
    @BindView(R.id.id_result1)
    TextView result;
    private boolean isFirstSticky = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_send);
        ButterKnife.bind(this);
        initNavBar(true,"Eventbus发送数据");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解注册
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.id_eventbus,R.id.id_other})
    public void eventbus(View view){
        switch (view.getId()){
            case R.id.id_eventbus:
                // 发送消息
                EventBus.getDefault().post(new MessageEvent("主线程发送过来的数据"));
                finish();
                break;
            case R.id.id_other:
                // 注册广播
                if (isFirstSticky){
                    isFirstSticky = false;
                    EventBus.getDefault().register(this);
                }
                break;
        }
    }

    // 接收粘性事件消息
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void stickyeventBus(StickyEvent event){
        result.setText(event.name);
    }

}
