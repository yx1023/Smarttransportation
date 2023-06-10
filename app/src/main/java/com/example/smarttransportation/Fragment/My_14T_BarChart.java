package com.example.smarttransportation.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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

public class My_14T_BarChart extends Fragment {
    private TextView tv;
    private List<BarEntry> entries;
    private List<String> TIME=new ArrayList<>();
    private List<Float>list=new ArrayList<>();
    private int PM25;
    private Timer timer;
    private BarChart chart;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.barchart_14,null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chart=view.findViewById(R.id.bc1111);
        tv=view.findViewById(R.id.TV14);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setData();
            }
        },0,3000);
    }





    private void setData() {
        getDate();
        if (list.size() == 20) {
            list.remove(0);
        }
        list.add((float)PM25 );
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
        tv.setText("过去一分钟空气质量最差值："+(max-min));

        entries=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entries.add(new BarEntry(i,list.get(i)));
        }
        BarDataSet barDataSet=new BarDataSet(entries,"");
        barDataSet.setDrawValues(false);
        barDataSet.setColor(Color.GRAY);
        BarData barData=new BarData(barDataSet);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setData(barData);
        setXAxis();
        setYAxis();

        chart.invalidate();

    }
    //设置X轴
    private void setXAxis() {


        SimpleDateFormat format = new SimpleDateFormat("ss");
        String str = format.format(new Date());
        TIME.add(str);
        System.out.println(TIME);
        if (TIME.size() > 20) {
            TIME.remove(0);
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(TIME));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(16f);
        xAxis.setTypeface(Typeface.DEFAULT_BOLD);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(TIME.size());



    }
    //设置Y轴
    private void setYAxis() {
        chart.getAxisRight().setEnabled(false);
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setTextSize(14f);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setXOffset(15);
        yAxis.setGranularity(200f);
        yAxis.setLabelCount(7);
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
                    PM25 = jsonObject1.getInt("pm25");

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
    }
}
