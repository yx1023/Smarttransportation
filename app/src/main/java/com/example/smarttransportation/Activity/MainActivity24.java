package com.example.smarttransportation.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Been.Weather;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity24 extends AppCompatActivity {

    private ImageView mMune17;
    private TextView mTvWd;
    private TextView mFanwei;
    private ImageView mShuaxin;
    private LineChart mZST;
    List<Weather>list=new ArrayList<>();
    private int temperature;
    private String interval;
    private List<Float> max;
    private List<Float> min;
    private List<Entry> entries1;
    private List<Entry> entries2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main24);
        initView();
        getData();
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mTvWd = (TextView) findViewById(R.id.tv_wd);
        mFanwei = (TextView) findViewById(R.id.fanwei);
        mShuaxin = (ImageView) findViewById(R.id.shuaxin);
        mZST = (LineChart) findViewById(R.id.ZST);
        mShuaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    public void getData(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H() .sendResutil("get_weather_info", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    temperature = jsonObject1.getInt("temperature");
                    JSONArray jsonArray=jsonObject1.getJSONArray("ROWS_DETAIL");
                    JSONObject jsonObject2=new JSONObject(jsonArray.get(1).toString());
                    interval = jsonObject2.getString("interval");
                    list=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<Weather>>(){}.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTvWd.setText(temperature+"°");
                            mFanwei.setText("今天："+interval.replace("~","-")+"℃");
                            min = new ArrayList<>();
                            max = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                String[] a = list.get(i).getInterval().split("~");
                                min.add(Float.valueOf(a[0]));
                                max.add(Float.valueOf(a[1]));
                            }
                            setZX(min, max);
                        }
                    });


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void setZX(List<Float> min, List<Float> max) {

        entries1 = new ArrayList<>();
        entries2 = new ArrayList<>();
        for (int i = 0; i < min.size(); i++) {
            entries1.add(new Entry(i, min.get(i)));
        }
        for (int i = 0; i < max.size(); i++) {
            entries2.add(new Entry(i, max.get(i)));
        }

        LineDataSet lineDataSet1 = new LineDataSet(entries1, "1");
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "2");

        LineData lineData1 = new LineData(lineDataSet1, lineDataSet2);
        mZST.setData(lineData1);


        mZST.getDescription().setEnabled(false);
        mZST.getLegend().setEnabled(false);
        mZST.setExtraTopOffset(10);
        lineDataSet1.setCircleRadius(3);//设置圆点半径大小
        lineDataSet1.setLineWidth(2);//设置折线的宽度
        lineDataSet1.setColor(Color.BLUE);
        lineDataSet1.setDrawCircleHole(false);//设置是否空心
        lineDataSet1.setCircleColors(Color.BLUE);//依次设置每个圆点的颜色
        lineDataSet1.setMode(LineDataSet.Mode.LINEAR); // 设置折线类型
        lineDataSet2.setCircleRadius(3);//设置圆点半径大小
        lineDataSet2.setLineWidth(2);//设置折线的宽度
        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setDrawCircleHole(false);//设置是否空心
        lineDataSet2.setCircleColors(Color.RED);//依次设置每个圆点的颜色

        lineDataSet2.setMode(LineDataSet.Mode.LINEAR); // 设置折线类型

        mZST.invalidate();
        setYAxis();
        setXAxis();

    }

    public void setYAxis() {
        mZST.getAxisRight().setEnabled(false); //不显示右侧Y轴
        mZST.getAxisLeft().setEnabled(true);//不显示左轴
        YAxis yAxis1 = mZST.getAxisLeft();
        yAxis1.setDrawAxisLine(false);

        yAxis1.setTextColor(Color.TRANSPARENT);
        yAxis1.setGridColor(Color.BLACK);
        yAxis1.setGridLineWidth(2);
    }

    public void setXAxis() {

        String[] s = {"昨天", "今天", "明天", "周五", "周六", "周天"};

        XAxis xAxis = mZST.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(s));

        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(s.length);
        xAxis.setGranularity(1f);
        xAxis.setTextSize(15);


    }
}