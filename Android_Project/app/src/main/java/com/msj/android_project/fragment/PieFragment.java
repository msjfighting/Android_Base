package com.msj.android_project.fragment;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.msj.android_project.R;
import com.msj.android_project.bean.BillBean;
import com.msj.android_project.bean.BillDetailBean;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PieFragment extends Fragment implements OnChartValueSelectedListener, View.OnClickListener {

    private static final String DATA_KEY = "data_key";
    private BillBean mData;

    private PieChart mPieChart;

    private Button id_present,id_puman,id_x,id_y,id_xy,id_centertitle,id_save,id_animtion;


    public static PieFragment newInstance(BillBean data) {
        Bundle args = new Bundle();
        args.putSerializable(DATA_KEY,data);
        PieFragment fragment = new PieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null){
            mData = (BillBean) bundle.getSerializable(DATA_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie,container,false);
        mPieChart = view.findViewById(R.id.mPieChart);
        id_present = view.findViewById(R.id.id_present);
        id_puman = view.findViewById(R.id.id_puman);
        id_x = view.findViewById(R.id.id_x);
        id_y = view.findViewById(R.id.id_y);

        id_xy = view.findViewById(R.id.id_xy);
        id_centertitle = view.findViewById(R.id.id_centertitle);
        id_save = view.findViewById(R.id.id_save);
        id_animtion = view.findViewById(R.id.id_animtion);

        initView();

        return view;
    }

    private void initView() {
        id_present.setOnClickListener(this);
        id_puman.setOnClickListener(this);
        id_x.setOnClickListener(this);
        id_y.setOnClickListener(this);
        id_xy.setOnClickListener(this);
        id_centertitle.setOnClickListener(this);
        id_save.setOnClickListener(this);
        id_animtion.setOnClickListener(this);



        // 取消描述
        mPieChart.getDescription().setEnabled(false);
        // 设置饼状图距离上下左右的偏移量
        mPieChart.setExtraOffsets(5, 10, 5, 5);
        // 设置阻尼系数,范围在[0,1]之间,越小饼状图转动越困难
        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
        mPieChart.setCenterText("月账单");
        // 设置是否启用空洞，默认为true 填充饼状图（默认时环状）
        mPieChart.setDrawHoleEnabled(true);
        // 设置空洞颜色
        mPieChart.setHoleColor(Color.WHITE);
        // 设置半透明圆颜色，默认为白色
        mPieChart.setTransparentCircleColor(Color.WHITE);
        // 设置半透明圆透明度, 默认为105
        mPieChart.setTransparentCircleAlpha(110);

        // 设置空洞半径,
        mPieChart.setHoleRadius(40f);
        // 设置半透明圆半径占饼图半径百分比，默认为55%
        mPieChart.setTransparentCircleRadius(30f);
        // 设置是否绘制中间文本，默认为true
        mPieChart.setDrawCenterText(true);
        // 设置饼状图旋转的角度
        mPieChart.setRotationAngle(90);
        // 触摸旋转
        mPieChart.setRotationEnabled(false);
        // 设置旋转的时候点中的每一项是否高亮 (默认为true)
        mPieChart.setHighlightPerTapEnabled(true);

        //变化监听
        mPieChart.setOnChartValueSelectedListener(this);

        // 设置是否使用百分值，默认为false
        mPieChart.setUsePercentValues(true);

        setData();

        mPieChart.animateY(1400,Easing.EaseInOutQuad);


        // //设置隐藏饼图上文字，只显示百分比
        mPieChart.setDrawEntryLabels(false);



        //设置比例模块图
        Legend l = mPieChart.getLegend();
        // 设置比例模块是否显示
        //l.setEnabled(false);
        //设置比例块换行
       // l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f); //设置距离饼图的距离，防止与饼图重合
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.BLACK);
        // 设置最大角度，值区间[90,360],默认360
        mPieChart.setEntryLabelTextSize(12f);

    }

    private void setData() {
        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<>();
        float total = 0;
        for (int i = 0; i < mData.lists.size() ; i++) {
            BillDetailBean bean = mData.lists.get(i);
            total = total + Float.parseFloat(bean.value);
        }

        for (int i = 0; i < mData.lists.size() ; i++) {
            BillDetailBean bean = mData.lists.get(i);

            entries.add(new PieEntry((Float.parseFloat(bean.value)/total) *100,bean.title));
        }

        PieDataSet dataSet = new PieDataSet(entries, mData.date);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<>();

        for (int i = 0; i < mData.lists.size() ; i++) {
            BillDetailBean bean = mData.lists.get(i);
            if (bean.title.equals("外卖")){
                colors.add(Color.rgb(0,255,0));
            }
            if (bean.title.equals("娱乐")){
                colors.add(Color.rgb(243,131,10));
            }else{
                colors.add(Color.rgb(255,0,0));
            }
        }
        dataSet.setColors(colors);

        //设置数据线距离图像内部园心的距离，以百分比来计算
        dataSet.setValueLinePart1OffsetPercentage(100.f);
        //当valuePosition在外部时，表示行前半部分的长度(即折线靠近圆的那端长度)
        dataSet.setValueLinePart1Length(0.5f);
        //当valuePosition位于外部时，表示行后半部分的长度*(即折线靠近百分比那端的长度)
        dataSet.setValueLinePart2Length(0.4f);
        //设置Y值的位置在圆外
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);

        // 绘制内容value，设置字体颜色大小
        data.setDrawValues(true);
        //格式化数据,显示百分号
        data.setValueFormatter(new PiePercentFormatter(mPieChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        mPieChart.setData(data);
        // 撤销所有的亮点
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_present:
                mPieChart.setUsePercentValues(true);
                for (IDataSet<?> set : mPieChart.getData().getDataSets()){
                    set.setDrawValues(!set.isDrawValuesEnabled());
                }
                break;
            case R.id.id_puman:
                if (mPieChart.isDrawHoleEnabled())
                    mPieChart.setDrawHoleEnabled(false);
                else
                    mPieChart.setDrawHoleEnabled(true);
                break;
            case R.id.id_x:
                mPieChart.animateX(1400);
                break;
            case R.id.id_y:
                mPieChart.animateY(1400);
                break;
            case R.id.id_xy:
                mPieChart.animateXY(1400, 1400);
                break;
            case R.id.id_centertitle:
                if (mPieChart.isDrawCenterTextEnabled())
                    mPieChart.setDrawCenterText(false);
                else
                    mPieChart.setDrawCenterText(true);
                mPieChart.invalidate();
                break;
            case R.id.id_save:
                mPieChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            case R.id.id_animtion:
                mPieChart.spin(1000, mPieChart.getRotationAngle(), mPieChart.getRotationAngle() + 360, Easing.EaseInCirc);
                break;
        }
        mPieChart.invalidate();
    }


    private class PiePercentFormatter extends ValueFormatter
    {

        public DecimalFormat mFormat;
        private PieChart pieChart;

        public PiePercentFormatter() {
            mFormat = new DecimalFormat("###,###,##0.0");
        }

        public PiePercentFormatter(PieChart pieChart) {
            this();
            this.pieChart = pieChart;
        }

        public String getFormattedValue(PieEntry pieEntry,float value) {

            return pieEntry.getLabel() +"\n"+mFormat.format(value) + " %";
        }

        @Override
        public String getPieLabel(float value, PieEntry pieEntry) {
            if (pieChart != null && pieChart.isUsePercentValuesEnabled()) {
                return getFormattedValue(pieEntry,value);
            } else {
                // raw value, skip percent sign
                return mFormat.format(value);
            }
        }

    }

}
