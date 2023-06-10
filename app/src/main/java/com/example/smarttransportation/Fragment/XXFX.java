package com.example.smarttransportation.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarttransportation.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class XXFX extends Fragment {

    private PieChart pc;

    private List<Integer> wd=XXCX.getWD();
    private List<Integer> sd=XXCX.getSD();
    private List<Integer> pm=XXCX.getPM();
    private List<Integer> gz=XXCX.getGZ();
    private List<Integer> co_2=XXCX.getCO();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xxfx,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pc=view.findViewById(R.id.pcc);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                loadData();
            }
        },0,3000);

    }
    public void loadData(){



        pc.setExtraBottomOffset(30);
        pc.setExtraTopOffset(30);
        //不显示描述/标题
        pc.getDescription().setEnabled(false);



        //不绘制空洞
        pc.setDrawHoleEnabled(false);
        //不可旋转
        pc.setRotationEnabled(false);


        List<PieEntry> entries=new ArrayList<>();
        entries.add(new PieEntry(wd.size(),"温度 "));
        entries.add(new PieEntry(sd.size(),"湿度 "));
        entries.add(new PieEntry(pm.size(),"PM2.5 "));
        entries.add(new PieEntry(gz.size(),"光照 "));
        entries.add(new PieEntry(co_2.size(),"CO2 "));

        PieDataSet pieDataSet=new PieDataSet(entries,"");
        //设置饼块之间的间隔
        pieDataSet.setSliceSpace(2f);
        //设置点击某一饼快多出来的距离
        pieDataSet.setSelectionShift(15f);

        pieDataSet.setDrawValues(false);
        Legend legend = pc.getLegend();
        legend.setTextSize(20f);
        legend.setFormSize(20f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setXOffset(150);

        pc.setDrawEntryLabels(false);


        pieDataSet.setColors(getResources().getColor(R.color.grey),getResources().getColor(R.color.black),getResources().getColor(R.color.purple_200),getResources().getColor(R.color.w),getResources().getColor(R.color.teal_200));
        PieData pieData=new PieData(pieDataSet);
        pc.setData(pieData);
        pc.invalidate();
    }
}
