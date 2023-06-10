package com.example.smarttransportation.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarttransportation.Been.SJFX;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class T_26_2 extends Fragment {
    private HorizontalBarChart hbc;
    List<SJFX> list=new ArrayList<>();
    private int a=0;
    private int b=0;
    private int c=0;
    private Map<String,Integer> map;
    private List<Map.Entry<String,Integer>>mapList;

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            loadData();
            return false;
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hbarchart,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hbc=view.findViewById(R.id.HBC);
        getData();
    }

    public void getData(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_peccancy", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {

                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<SJFX>>(){}.getType());



                    for (int i = 0; i < list.size(); i++) {
                        for (int j = 0; j < list.size(); j++) {
                            if(list.get(j).getDatetime().equals("")){
                                list.remove(j);
                            }
                        }
                    }


                    map=new HashMap<>();
                    for (int i = 0; i < list.size(); i++) {
                        String plate=list.get(i).getCarnumber();
                        Integer count=map.get(plate);
                        map.put(plate,(count==null) ? 1 : count+1);
                    }
                    mapList=new ArrayList<>(map.entrySet());

                    for (int i = 0; i < mapList.size(); i++) {
                        if(mapList.get(i).getValue() == 1||mapList.get(i).getValue() == 2){
                            a++;
                        }
                        else if (mapList.get(i).getValue() == 3||mapList.get(i).getValue() == 4||mapList.get(i).getValue() == 5){
                            b++;
                        }
                        else if (mapList.get(i).getValue() >5) {
                            c++;
                        }
                    }
                    System.out.println(a+"--"+b+"--"+c);

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
    //设置y轴
    private void setYAxis() {
        //两边的y轴都要设置y轴的最小值才能在柱状图上面显示数值
        //不然是看不到效果的
        YAxis tepAxis = hbc.getAxisLeft();
        tepAxis.setAxisMaximum(100f);
        tepAxis.setAxisMinimum(0f);
        tepAxis.setEnabled(false);

        YAxis yAxis = hbc.getAxisRight();
        yAxis.setTextSize(12f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        //自定义样式
        yAxis.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                return value + "0%";
            }
        });
    }
    private void setXAxis() {
        XAxis xAxis = hbc.getXAxis();
        xAxis.setTextSize(16);

        xAxis.setDrawGridLines(false);
        //将x轴显示在左侧
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(3);
        //自定义样式
        String[] s={"1——2条违章","3——5条违章","5条以上违章"};

        xAxis.setValueFormatter(new IndexAxisValueFormatter(s));
        //设置x轴的偏移量
        xAxis.setXOffset(15f);
    }
    private void loadData(){
        setYAxis();
        setXAxis();
        //不绘制图例
        hbc.getLegend().setEnabled(false);
        //自动对齐
        hbc.setFitBars(true);
        hbc.setExtraOffsets(20,50,30,30);
        //将文本绘制在柱块上还是柱块里面
        hbc.setDrawValueAboveBar(true);

        //从下往上绘制
        Float f1= Float.valueOf(a);
        Float f2= Float.valueOf(b);
        Float f3= Float.valueOf(c);

        final List<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(0,f1/(f1+f2+f3)*100));
        entries.add(new BarEntry(1,f2/(f1+f2+f3)*100));
        entries.add(new BarEntry(2,f3/(f1+f2+f3)*100));

        BarDataSet barDataSet = new BarDataSet(entries,"");
        barDataSet.setValueTextSize(16f);//柱块上的字体大小
        barDataSet.setValueTextColor(Color.RED);//柱块上的字体颜色
        barDataSet.setValueTypeface(Typeface.DEFAULT_BOLD);//加粗
        //自自定义样式
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.2f",value) + "%";//保留两位小数
            }

        });
        //依次设置每次柱块的颜色
        barDataSet.setColors(getResources().getColor(R.color.CT),getResources().getColor(R.color.pc_lan),getResources().getColor(R.color.pc_hong));
        BarData barData = new BarData(barDataSet);
        //设置柱块的宽度
        barData.setBarWidth(0.4f);

        hbc.setData(barData);

    }
}
