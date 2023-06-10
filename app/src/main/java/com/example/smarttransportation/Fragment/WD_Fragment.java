package com.example.smarttransportation.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class WD_Fragment extends Fragment {
     LineChart lc;
    private int temperature;
    private List<Entry> entries;
    private List<String> TIME=new ArrayList<>();
    private List<Float>list=new ArrayList<>();
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            setData();
            return false;
        }
    });
    Timer timer=new Timer();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lc=view.findViewById(R.id.lc);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getData();
            }
        },0,3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    public void getData(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_all_sense", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    temperature = jsonObject1.getInt("temperature");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(0);
                        }
                    }).start();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void setData() {

        if (list.size() == 20) {
            list.remove(0);
        }
        list.add((float) temperature);

        entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entries.add(new Entry(i, list.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "第一条数据");
        LineData lineData = new LineData(lineDataSet);

        //设置不显示右下角标题
        lc.getDescription().setEnabled(false);

        //设置不显示左下角标题
        lc.getLegend().setEnabled(false);

        lineDataSet.setCircleRadius(5);//设置圆点半径大小
        lineDataSet.setLineWidth(5);//设置折线的宽度
        lineDataSet.setColor(Color.GRAY);
        lineDataSet.setDrawCircleHole(false);//设置是否空心
        lineDataSet.setCircleColors(Color.GRAY);//依次设置每个圆点的颜色
        lineDataSet.setDrawValues(false);  //设置不显示折线上的数据

        lineDataSet.setMode(LineDataSet.Mode.LINEAR); // 设置折线类型


        lc.setData(lineData);
        setYAxis();
        setXAxis();
        lc.invalidate();//自动更新折线

    }

    public void setYAxis() {

        lc.getAxisRight().setEnabled(false); //不显示右侧Y轴
        YAxis yAxis1 = lc.getAxisLeft();
        yAxis1.setAxisMinimum(0);
        yAxis1.setAxisMaximum(45);
        yAxis1.setTextSize(20);
        yAxis1.setDrawAxisLine(false);

    }

    public void setXAxis() {

        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        String str = format.format(new Date());
        TIME.add(str);

        if (TIME.size() > 20) {
            TIME.remove(0);
        }
        XAxis xAxis = lc.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(TIME));

        xAxis.setLabelRotationAngle(90);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(TIME.size());
        xAxis.setGranularity(1f);
        xAxis.setTextSize(15);


    }
}
