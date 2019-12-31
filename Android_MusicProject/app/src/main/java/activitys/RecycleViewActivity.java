package activitys;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.*;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.classtwo.R;
import adapter.MyRecyclerViewAdapter;

import java.util.ArrayList;

public class RecycleViewActivity extends Activity implements View.OnClickListener {
    private Button add,delete,list,grid,flow;
    private RecyclerView reclycler;

    private TextView text;
    private ArrayList<String> array;
    private MyRecyclerViewAdapter adapter;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        initView();
        initData();

        // 设置recyclerview的适配器
        adapter = new MyRecyclerViewAdapter(this,array);
        reclycler.setAdapter(adapter);

        // layoutmanager
        // 参数一 上下文 参数二: 显示方向 参数三: 是否倒序
        reclycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        // 滚动到10
        reclycler.scrollToPosition(10);

        // 添加分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(new ColorDrawable(ContextCompat.getColor(this, android.R.color.holo_red_light)));

        reclycler.addItemDecoration(dividerItemDecoration);

        // 自定义点击事件
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Toast.makeText(RecycleViewActivity.this,"data=="+data, Toast.LENGTH_SHORT).show();
            }
        });
           // 设置动画
        reclycler.setItemAnimator(new DefaultItemAnimator());
    }

    private void initView() {
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        list = findViewById(R.id.list);
        grid = findViewById(R.id.grid);
        flow = findViewById(R.id.flow);

        reclycler = findViewById(R.id.recyclerview);

        text = findViewById(R.id.tv_title);
        text.setText("进阶学习之RecyclerView");

        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        list.setOnClickListener(this);
        grid.setOnClickListener(this);
        flow.setOnClickListener(this);
    }

    private void initData() {
        // 准备数据集合
        array = new ArrayList<String>();
        for (int i =0; i < 30; i++){
            array.add("Content_"+i);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                adapter.addData(0,"new_content");
                reclycler.scrollToPosition(0);
                break;
            case R.id.delete:
                adapter.removeData(0);
                break;
            case R.id.list:
                reclycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
                break;
            case R.id.grid:
                // 参数一 上下文  参数二: 列数  参数三: 显示方向 参数四: 是否倒序
                reclycler.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
                break;
            case R.id.flow:
                // 参数一: 列数  参数三: 显示方向
                reclycler.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                break;
        }
    }
}
