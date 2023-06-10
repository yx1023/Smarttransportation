package com.example.smarttransportation.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.smarttransportation.Been.Weather;
import com.example.smarttransportation.Fragment.My_14T_BarChart;
import com.example.smarttransportation.Fragment.My_14T_Fragment1;
import com.example.smarttransportation.Fragment.My_14T_Fragment2;
import com.example.smarttransportation.Fragment.My_14T_Fragment3;
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
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity14 extends AppCompatActivity implements View.OnClickListener {

    private ImageView mCaidan;
    private TextView mTvWd;
    private TextView mFanwei;
    private ImageView mShuaxin;
    private LineChart mZST;
    private TextView mZWX1;
    private TextView mZWX2;
    private TextView mZWX3;
    private TextView mGMZS1;
    private TextView mGMZS2;
    private TextView mGMZS3;
    private TextView mCYZS1;
    private TextView mCYZS2;
    private TextView mCYZS3;
    private TextView mYDZS1;
    private TextView mYDZS2;
    private TextView mYDZS3;
    private TextView mKQWRZS1;
    private TextView mKQWRZS2;
    private TextView mKQWRZS3;
    private ViewPager mVP;
    private TextView mTvKqzl;
    private TextView mTvSd;
    private TextView mWD;
    private TextView mTvCo2;
    private int temperature;
    private int illumination;
    private int co2;
    private int pm25;
    List<Weather> list = new ArrayList<>();
    private List<Float> max;
    private List<Float> min;
    private List<Entry> entries1;
    private List<Entry> entries2;
    My_14T_BarChart barChart = new My_14T_BarChart();
    My_14T_Fragment1 fragment1 = new My_14T_Fragment1();
    My_14T_Fragment2 fragment2 = new My_14T_Fragment2();
    My_14T_Fragment3 fragment3 = new My_14T_Fragment3();

    private List<Fragment> fragmentsList = new ArrayList<>();


    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main14);
        initView();

        adapter = new MyAdapter(this.getSupportFragmentManager(), fragmentsList);
        mVP.setOffscreenPageLimit(4);
        mVP.setAdapter(adapter);
        getWeather();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getCGQ();
            }
        }, 0, 3000);
    }

    private void initView() {
        mWD=(TextView) findViewById(R.id.tv_wd_);
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mTvWd = (TextView) findViewById(R.id.tv_wd);
        mFanwei = (TextView) findViewById(R.id.fanwei);
        mShuaxin = (ImageView) findViewById(R.id.shuaxin);
        mZST = (LineChart) findViewById(R.id.ZST);
        mZWX1 = (TextView) findViewById(R.id.ZWX_1);
        mZWX2 = (TextView) findViewById(R.id.ZWX_2);
        mZWX3 = (TextView) findViewById(R.id.ZWX_3);
        mGMZS1 = (TextView) findViewById(R.id.GMZS_1);
        mGMZS2 = (TextView) findViewById(R.id.GMZS_2);
        mGMZS3 = (TextView) findViewById(R.id.GMZS_3);
        mCYZS1 = (TextView) findViewById(R.id.CYZS_1);
        mCYZS2 = (TextView) findViewById(R.id.CYZS_2);
        mCYZS3 = (TextView) findViewById(R.id.CYZS_3);
        mYDZS1 = (TextView) findViewById(R.id.YDZS_1);
        mYDZS2 = (TextView) findViewById(R.id.YDZS_2);
        mYDZS3 = (TextView) findViewById(R.id.YDZS_3);
        mKQWRZS1 = (TextView) findViewById(R.id.KQWRZS_1);
        mKQWRZS2 = (TextView) findViewById(R.id.KQWRZS_2);
        mKQWRZS3 = (TextView) findViewById(R.id.KQWRZS_3);
        mVP = (ViewPager) findViewById(R.id.VP);
        mTvKqzl = (TextView) findViewById(R.id.tv_kqzl);
        mTvSd = (TextView) findViewById(R.id.tv_sd);
        mTvCo2 = (TextView) findViewById(R.id.tv_co2);
        mShuaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeather();
            }
        });

        mVP.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setText(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        fragmentsList.add(barChart);
        fragmentsList.add(fragment1);
        fragmentsList.add(fragment2);
        fragmentsList.add(fragment3);
    }
    public void setText(int i){
        if(i==0){

            mTvCo2.setBackground(null);
            mTvSd.setBackground(null);
            mWD.setBackground(null);
            mTvKqzl.setBackgroundResource(R.drawable.bg);


        }else if(i==1){
            mWD.setBackgroundResource(R.drawable.bg);
            mTvSd.setBackground(null);
            mTvKqzl.setBackground(null);
            mTvCo2.setBackground(null);


        }
        else if(i==2){
            mTvSd.setBackgroundResource(R.drawable.bg);
            mWD.setBackground(null);
            mTvCo2.setBackground(null);
            mTvKqzl.setBackground(null);


        }
        else if(i==3){
            mTvCo2.setBackgroundResource(R.drawable.bg);
            mTvSd.setBackground(null);
            mWD.setBackground(null);
            mTvKqzl.setBackground(null);
        }
    }

    public void getCGQ() {
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
                    illumination = jsonObject1.getInt("illumination");
                    co2 = jsonObject1.getInt("co2");
                    pm25 = jsonObject1.getInt("pm25");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setSHZS();
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setSHZS() {

        mZWX2.setText("(" + illumination + ")");
        if (illumination > 0 && illumination < 10000) {
            mZWX1.setText("弱");
            mZWX3.setText("辐射较弱，涂擦SPF12~15、PA+护肤品");
        } else if (illumination >= 10000 && illumination <= 30000) {
            mZWX1.setText("中等");
            mZWX3.setText("涂擦SPF大于15、PA+防晒护肤品");
        } else if (illumination > 30000) {
            mZWX1.setText("强");
            mZWX3.setText("尽量减少外出，需要涂抹高倍数防晒霜");
        }

        mGMZS2.setText("(" + temperature + ")");
        if (temperature < 8) {
            mGMZS1.setText("较易发");
            mGMZS3.setText("温度低，风较大，较易发生感冒，注意防护");
        } else if (temperature >= 8) {
            mGMZS1.setText("少发");
            mGMZS3.setText("无明显降温，感冒机率较低");
        }

        mCYZS2.setText("(" + temperature + ")");
        if (temperature < 12) {
            mCYZS1.setText("冷");
            mCYZS3.setText("建议穿长袖衬衫、单裤等服装");
        } else if (temperature >= 12 && temperature <= 21) {
            mCYZS1.setText("舒适");
            mCYZS3.setText("建议穿短袖衬衫、单裤等服装");
        } else if (temperature > 21) {
            mCYZS1.setText("热");
            mCYZS3.setText("适合穿T恤、短薄外套等夏季服装");
        }

        mYDZS2.setText("(" + co2 + ")");
        if (co2 > 0 && co2 < 3000) {
            mYDZS1.setText("适宜");
            mYDZS3.setText("气候适宜，推荐您进行户外运动");
        } else if (co2 >= 3000 && co2 <= 6000) {
            mYDZS1.setText("中");
            mYDZS3.setText("易感人群应适当减少室外活动");
        } else if (co2 > 6000) {
            mYDZS1.setText("较不宜");
            mYDZS3.setText("空气氧气含量低，请在室内进行休闲运动");
        }

        mKQWRZS2.setText("(" + pm25 + ")");
        if (pm25 > 0 && pm25 < 30) {
            mKQWRZS1.setText("优");
            mKQWRZS3.setText("空气质量非常好，非常适合户外活动，趁机出去多呼吸新鲜空气");
        } else if (pm25 >= 30 && pm25 <= 100) {
            mKQWRZS1.setText("良");
            mKQWRZS3.setText("易感人群应适当减少室外活动");
        } else if (pm25 > 100) {
            mKQWRZS1.setText("污染");
            mKQWRZS3.setText("空气质量差，不适合户外活动");

        }
    }

    public void getWeather() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_weather_info", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    int temperatures = jsonObject1.getInt("temperature");
                    JSONArray jsonArray = jsonObject1.getJSONArray("ROWS_DETAIL");
                    JSONObject jsonObject2 = new JSONObject(jsonArray.get(1).toString());
                    String interval = jsonObject2.getString("interval");
                    list = new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<Weather>>() {
                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTvWd.setText(temperatures + "°");
                            String s1 = interval.replace("~", "-");
                            mFanwei.setText("今天：" + s1 + "℃");
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
        mZST.setData(lineData1);
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

    @Override
    public void onClick(View v) {

    }

    class MyAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public MyAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}