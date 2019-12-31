package com.example.zlhj.classone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
  /********** 添加点击监听方法--3 ***************/
//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
 public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    /********** 添加点击监听方法--1 ***************/
/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button);
        // 通过id找到布局中的view控件
        Button btn = (Button) findViewById(R.id.btn);
        // 给button按钮添加点击的监听
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("msg","点击了");
            }
        });
    }*/

    /********** 添加点击监听方法--2 ***************/
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.button);
    }
    public void skip(View v){
        Log.v("msg","点击了");
    }
*/

      /********** 添加点击监听方法--3 ***************/
/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);

    }
    public void onClick(View v) {
        Log.v("msg","hhahah");
        Toast.makeText(this, "点击按钮", Toast.LENGTH_SHORT).show();
    }*/

      /********** 添加点击监听方法--4 ***************/
/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button);
        Button btn = (Button) findViewById(R.id.btn);
        myLister lister = new myLister();
        btn.setOnClickListener(lister);

        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(lister);
    }
    // 定义一个类来实现OnClickListener接口
    class myLister implements View.OnClickListener{
        // 参数V 就是触发点击的view控件,在这里就被点击的Button按钮
        public void onClick(View v){
         // 拿到控件id
         // v.getId()
            switch (v.getId()){
                case(R.id.btn):
                    Log.v("msg","点击了1");
                    break;
                case(R.id.btn2):
                    Log.v("msg","点击了2");
                    break;
                    default:
                        break;
            }
        }
    }*/

      /********** CheckBox  RadioButton  ***************/
   /* private Button check;
    private CheckBox playball,swim,run,travel,sleep;
    private RadioButton nan,nv;
    private RadioGroup group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkbox);
        check = findViewById(R.id.btn);
        playball = findViewById(R.id.playball);
        run = findViewById(R.id.run);
        travel = findViewById(R.id.travel);
        sleep = findViewById(R.id.sleep);
        swim = findViewById(R.id.swim);

        nan = findViewById(R.id.nan);
        nv = findViewById(R.id.nv);

        group = findViewById(R.id.group);

        check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showBox();
            }
        });

       *//* nan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(MainActivity.this,"选中的是:"+nan.getText(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        nv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(MainActivity.this,"选中的是:"+nv.getText(),Toast.LENGTH_SHORT).show();
                }
            }
        });*//*
        // 给RadioGroup 设置选中的监听,如果当这个RadioGroup中单选按钮选中的状态发生变化的时候就会执行onCheckedChanged
       group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId){
                   case R.id.nv:
                       Toast.makeText(MainActivity.this,"选中的是:"+nv.getText(),Toast.LENGTH_SHORT).show();
                       break;
                   case R.id.nan:
                       Toast.makeText(MainActivity.this,"选中的是:"+nan.getText(),Toast.LENGTH_SHORT).show();
                       break;
               }
           }
       });
    }*/

/*    public void showBox(){
        String str = "";
        if (swim.isChecked()){
            str += swim.getText().toString();
        }
        if (playball.isChecked()){
            str += playball.getText().toString();
        }
        if (run.isChecked()){
            str += run.getText().toString();
        }
        if (travel.isChecked()){
            str += travel.getText().toString();
        }
        if (sleep.isChecked()){
            str += sleep.getText().toString();
        }
        Log.v("TAG","选中的内容有:"+str);
    }*/

    /*public void showBox(){
        StringBuffer sbf = new StringBuffer();

        if (swim.isChecked()){
            sbf.append(swim.getText().toString() + ",");
        }
        if (playball.isChecked()){
            sbf.append(playball.getText().toString() + ",");
        }
        if (run.isChecked()){
            sbf.append(run.getText().toString() + ",");
        }
        if (travel.isChecked()){
            sbf.append(travel.getText().toString() + ",");
        }
        if (sleep.isChecked()){
            sbf.append(sleep.getText().toString() + ",");
        }

        if (sbf.length() > 0){
            String str = sbf.substring(0,sbf.length() - 1);
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"请选择你的爱好",Toast.LENGTH_LONG).show();
        }
     }*/

      /********** EditText 明密文切换  ***************/
      /*private EditText edit;
      private CheckBox box;
      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.edittext);

          edit = findViewById(R.id.edit);
          box = findViewById(R.id.box);
          // 设置光标不可见
          edit.setCursorVisible(false);
          box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  if (isChecked){
                      // 明文  设置转化方式的实例对象
                      edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                  }else{
                      edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                  }
                  // 将光标移动到文字末尾
                  edit.setSelection(edit.getText().length());
              }
          });
      }*/
      /********** EditText 内容监听  ***************/
 /*     private EditText input;
      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.edittext);

          input = findViewById(R.id.input);

          input.addTextChangedListener(new TextWatcher() {
              // start, 开始的位置 count. 被改变的援用内容的个人 after 改变之后的内容数据
              // s: 表示改变之前的内容 通常start和count组合,可以在s中读取本次改变字段中被改变的内容,而after表示改变后新的内容的数量

              @Override
              public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                  // 文本发生变化以前
                  Log.i("TAG","beforeTextChanged方法被调用"+s+"-------"+start+"-------"+count);
              }

              // count 表示新增的数量
              @Override
              public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 文本发生变化的时候
                  Log.i("TAG","onTextChanged方法被调用"+s+"-------"+start+"---------"+before+"-------"+count);
              }

              // s: 表示最终的内容
              @Override
              public void afterTextChanged(Editable s) {
                // 文本发生变化之后
//                  Log.i("TAG","afterTextChanged方法被调用"+s);
                  if (s.length() == 11){
                      Toast.makeText(MainActivity.this,"联通手机号",Toast.LENGTH_SHORT).show();
                  }
              }
          });
      }*/

      private ImageView image;
      private Button pre,next;
      // 图片在R文件中呈现的id值组成的int类型的数组
      private int[] images = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d};
      private  int position = 0; // 默认选中的
      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.imageview);
            image = findViewById(R.id.image);
            pre = findViewById(R.id.pre);
            next = findViewById(R.id.next);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"点击了图片",Toast.LENGTH_SHORT).show();
                }
            });


            // 设置按钮的点击监听

          pre.setOnClickListener(this);
          next.setOnClickListener(this);
      }

      @Override
      public void onClick(View v) {
          switch (v.getId()){
              case R.id.pre:
                  position--;
                  if (position < 0){
                      position = images.length - 1;
                  }
                  image.setImageResource(images[position]);
                  break;
              case R.id.next:
                  position++;
                  if (position >= images.length){
                      position = 0;
                  }
                  image.setImageResource(images[position]);
              break;
          }
      }
  }
