package com.msj.android_project.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.msj.android_project.R;
import com.msj.android_project.bean.DataModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HandlerActivity extends BaseActivity {

    /**
     * Handler负责发送消息,Looper负责接收Handler发送的消息,并直接把消息回传给Handler自己.
     * MessageQueue是一个存储消息的容器
     */

    @BindView(R.id.textv)
    TextView textv;


    @BindView(R.id.image)
    ImageView image;

    private Handler handler = new Handler();


    private int images[] = {R.drawable.a, R.drawable.b, R.drawable.c};

    private int index;

    private MyRunnable myRunnable = new MyRunnable();


    class MyRunnable implements Runnable {
        @Override
        public void run() {
            index++;
            index = index % 3;
            image.setImageResource(images[index]);
            handler.postDelayed(myRunnable, 1000);
        }
    }

    private  Handler handlermess = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // textv.setText("handleMessage" + msg.arg1);
           // DataModel dataModel = (DataModel) msg.obj;
           // textv.setText(dataModel.name);
            Log.i("TAG","UI-------------->>>>> currentThread:" + Thread.currentThread());
        }
    };

    private Handler handlerCallback = new Handler(new Handler.Callback() {
        @SuppressLint("WrongConstant")
        @Override
        public boolean handleMessage(Message message) {
            Toast.makeText(HandlerActivity.this,""+1,1).show();
            return true;
        }
    }){
        @SuppressLint("WrongConstant")
        @Override
        public void handleMessage(Message message) {
            Toast.makeText(HandlerActivity.this,""+2,1).show();
        }
    };



    class TwoThread extends Thread{
        public Handler handler;

        @Override
        public void run() {
            Looper.prepare();

            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    Log.i("TAG","currentThread:" + Thread.currentThread());
                }
            };
            Looper.loop();
        }
    }

    private TwoThread thread;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        ButterKnife.bind(this);
        initNavBar(true, "Handler");
//        handler();

       thread = new TwoThread();
       thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.handler.sendEmptyMessage(1);
        handlermess.sendEmptyMessage(1);

    }

    private void handler(){

        handler.postDelayed(myRunnable, 1000);

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
//                    textv.setText("update");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textv.setText("update");
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();


        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    // Message message = new Message();
                    Message message = handlermess.obtainMessage();
                    message.arg1 = 888888;
                    DataModel dataModel = new DataModel();
                    dataModel.name = "测试测试";
                    message.obj = dataModel;
//                    handlermess.sendMessage(message);

                    message.sendToTarget();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        handler.removeCallbacks(myRunnable);
        handlerCallback.sendEmptyMessage(1);
    }
}
