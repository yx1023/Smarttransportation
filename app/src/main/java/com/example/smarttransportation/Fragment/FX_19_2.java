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
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FX_19_2 extends Fragment {

    private HorizontalBarChart bt;
    List<SJFX> list=new ArrayList<>();
    float i1=0;
    float i2=0;
    float i3=0;
    float i4=0;
    float i5=0;
    float i6=0;
    float i7=0;
    float i8=0;
    float i9=0;
    float i10=0;

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            setDes();

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
        bt=view.findViewById(R.id.HBC);
        getData();



    }

    //设置标题
    private void setDes() {
        bt.getDescription().setEnabled(false);

        setXAxis();

        setYAxis();

        loadData();

    }

    //设置y轴
    private void setYAxis() {
        //两边的y轴都要设置y轴的最小值才能在柱状图上面显示数值
        //不然是看不到效果的
        YAxis tepAxis = bt.getAxisLeft();
        tepAxis.setAxisMaximum(40f);
        tepAxis.setAxisMinimum(0f);
        tepAxis.setEnabled(false);

        YAxis yAxis = bt.getAxisRight();
        yAxis.setTextSize(12f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(40f);
        //自定义样式
        yAxis.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                return value + "0%";
            }
        });
    }

    //设置x轴
    private void setXAxis() {
        XAxis xAxis = bt.getXAxis();
        xAxis.setTextSize(16);

        xAxis.setDrawGridLines(false);
        //将x轴显示在左侧
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(10);
        //自定义样式
        String[] s={"超速行驶","违规驶入导向车道","违反禁令标志指示","不按规定系安全带",
                "机动车不走机动车道","违反信号灯规定","违反禁止标线指示","违规停车拒绝驶离","违规使用专用车道","机动车逆向行驶"};

        xAxis.setValueFormatter(new IndexAxisValueFormatter(s));
        //设置x轴的偏移量
        xAxis.setXOffset(15f);
    }

    //加载数据
    private void loadData() {

        //不绘制图例
        bt.getLegend().setEnabled(false);
        //自动对齐
        bt.setFitBars(true);
        bt.setExtraOffsets(20,50,30,30);
        //将文本绘制在柱块上还是柱块里面
        bt.setDrawValueAboveBar(true);

        //从下往上绘制

        final List<BarEntry> entries = new ArrayList<BarEntry>();

        float q=i1+i2+i3+i4+i5+i6+i7+i8+i9+i10;

        float da[] = {(i1/q)*100,(i2/q)*100,(i3/q)*100,(i4/q)*100,(i5/q)*100,(i6/q)*100,(i7/q)*100,(i8/q)*100,(i9/q)*100,(i10/q)*100};

        for(int i = 0; i < da.length-1; i++){
            for (int j=0;j<da.length-i-1;j++){
                if(da[j]>da[j+1]){
                    float temp=da[j];
                    da[j]=da[j+1];
                    da[j+1]=temp;
                }
            }
        }
        for(int i=0;i<da.length;i++){
            entries.add(new BarEntry(i,da[i]));
        }

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
        barDataSet.setColors(getResources().getColor(R.color.purple_200),getResources().getColor(R.color.purple_500),getResources().getColor(R.color.purple_700),
                getResources().getColor(R.color.grey),getResources().getColor(R.color.black),getResources().getColor(R.color.teal_700),getResources().getColor(R.color.pc_hong)
                ,getResources().getColor(R.color.pc_lan),getResources().getColor(R.color.b),getResources().getColor(R.color.teal_200));
        BarData barData = new BarData(barDataSet);
        //设置柱块的宽度
        barData.setBarWidth(0.4f);

        bt.setData(barData);
        bt.invalidate();

    }
    private void getData(){
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
                    list=new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString()
                            ,new TypeToken<List<SJFX>>(){}.getType());

                    for (int i=0;i<list.size();i++){
                        for (int j=0;j<list.size();j++){
                            if(list.get(i).getCarnumber().equals(list.get(j).getCarnumber()) && i!=j){
                                list.remove(j);
                            }
                        }
                    }



                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getPaddr().equals("机动车逆向行驶")){
                            i1+=1;
                        }
                        if(list.get(i).getPaddr().equals("违规使用专用车道")){
                            i2+=1;
                        }
                        if(list.get(i).getPaddr().equals("违规停车拒绝驶离")){
                            i3+=1;
                        }
                        if(list.get(i).getPaddr().equals("违反禁止标线指示")){
                            i4+=1;
                        }
                        if(list.get(i).getPaddr().equals("违反信号灯规定")){
                            i5+=1;
                        }
                        if(list.get(i).getPaddr().equals("机动车不走机动车道")){
                            i6+=1;
                        }
                        if(list.get(i).getPaddr().equals("不按规定系安全带")){
                            i7+=1;
                        }
                        if(list.get(i).getPaddr().equals("违反禁令标志指示")){
                            i8+=1;
                        }
                        if(list.get(i).getPaddr().equals("违规驶入导向车道")){
                            i9+=1;
                        }
                        if(list.get(i).getPaddr().equals("超速行驶")){
                            i10+=1;
                        }
                    }
                    System.out.println(i1+"--"+i2+"--"+i3+"--"+i4+"--"+i5+"--"+i6+"--"+i7+"--"+i8+"--"+i9+"--"+i10);

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

}
