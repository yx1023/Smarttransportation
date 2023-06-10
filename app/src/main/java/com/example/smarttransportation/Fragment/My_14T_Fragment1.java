package com.example.smarttransportation.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class My_14T_Fragment1 extends Fragment {
    private LineChart lineChart;
    private TextView tv;
    private List<Entry> entries;
    private List<String> TIME=new ArrayList<>();
    private List<Float>list=new ArrayList<>();
    private int temperature;
    private Timer timer;

    private void setData() {
        getDate();
        if (list.size() == 20) {
            list.remove(0);
        }
        list.add((float) temperature);
        Float max=list.get(0);
        Float min=list.get(0);
        for(int i=0;i<list.size();i++){
            if(max<list.get(i)){
                max=list.get(i);
            }
            if(min>list.get(i)){
                min=list.get(i);
            }
        }
        tv.setText("过去一分钟最高气温："+max+"℃，最低气温："+min+"℃");


        entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entries.add(new Entry(i, list.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "℃");
        LineData lineData = new LineData(lineDataSet);

        //设置右下角标题
        lineChart.getDescription().setText("(S)");
        lineChart.getDescription().setTextSize(20);

        //设置不显示左下角标题
        lineChart.getLegend().setEnabled(false);

        lineDataSet.setCircleRadius(8);//设置圆点半径大小
        lineDataSet.setLineWidth(5);//设置折线的宽度
        lineDataSet.setColor(Color.GRAY);
        lineDataSet.setDrawCircleHole(false);//设置是否空心
        lineDataSet.setCircleColors(Color.GRAY);//依次设置每个圆点的颜色
        lineDataSet.setDrawValues(false);  //设置不显示折线上的数据

        lineDataSet.setMode(LineDataSet.Mode.LINEAR); // 设置折线类型
        lineChart.setExtraBottomOffset(10);

        lineChart.setData(lineData);
        setYAxis();
        setXAxis();
        lineChart.invalidate();//自动更新折线

    }

    public void setYAxis() {

        lineChart.getAxisRight().setEnabled(false); //不显示右侧Y轴
        YAxis yAxis1 = lineChart.getAxisLeft();
        yAxis1.setAxisMinimum(0);
        yAxis1.setAxisMaximum(45);
        yAxis1.setTextSize(20);
        yAxis1.setDrawAxisLine(false);
        yAxis1.setGridColor(Color.BLACK);
        yAxis1.setGridLineWidth(1.5f);


    }

    public void setXAxis() {

        SimpleDateFormat format = new SimpleDateFormat("ss");
        String str = format.format(new Date());
        TIME.add(str);

        if (TIME.size() > 20) {
            TIME.remove(0);
        }
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(TIME));

        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(TIME.size());
        xAxis.setGranularity(1f);
        xAxis.setTextSize(15);


    }

    public void getDate() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_all_sense", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    temperature = jsonObject1.getInt("temperature");

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.act_14,null);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setData();
            }
        },0,3000);
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
    }

    private void initView() {
        lineChart = getView().findViewById(R.id.line_chart_);
        tv=getView().findViewById(R.id.TV14);
    }
}
