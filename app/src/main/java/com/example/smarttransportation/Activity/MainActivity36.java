package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.GV_Adapter;
import com.example.smarttransportation.Been.Weather;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity36 extends AppCompatActivity {

    private ImageView mMune36;
    private ImageView mShuaxin;
    private ImageView mTq;
    private TextView mTime;
    private TextView mWd;
    private GridView mGv;
    List<Weather>list=new ArrayList<>();
    private String weather;
    private int temperature;
    private String arr[] = {"周天", "周一", "周二", "周三", "周四", "周五", "周六"};
    private String arr1[] = {"今天", "明天", "后天"};
    private List<String> time=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main36);
        initView();
        Calendar calendar=Calendar.getInstance();
        for (int i = 0; i < 6; i++) {
            if (i < 3) {
                time.add(calendar.get(Calendar.DAY_OF_MONTH)+"日("+arr1[i]+")");
            }else {
                time.add(calendar.get(Calendar.DAY_OF_MONTH)+"日("+arr[calendar.get(Calendar.DAY_OF_WEEK) -1]+")");
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        getData();
    }

    private void initView() {
        mMune36 = (ImageView) findViewById(R.id.mune36);
        mShuaxin = (ImageView) findViewById(R.id.shuaxin);
        mTq = (ImageView) findViewById(R.id.tq);
        mTime = (TextView) findViewById(R.id.time);
        mWd = (TextView) findViewById(R.id.wd);
        mGv = (GridView) findViewById(R.id.gv);
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
        new H().sendResutil("get_weather_info", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<Weather>>(){}.getType());
                    weather = jsonObject1.getString("weather");
                    temperature = jsonObject1.getInt("temperature");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWd.setText("北京"+temperature+"度");
                            switch (weather){
                                case "晴":
                                    mTq.setImageResource(R.drawable.qingtian);
                                    break;
                                case "小雨":
                                    mTq.setImageResource(R.drawable.xiaoyu);
                                    break;
                                case "阴":
                                    mTq.setImageResource(R.drawable.yin);
                                    break;
                            }
                            SimpleDateFormat format=new SimpleDateFormat("yyyy年M月dd日  EE");
                            String s=format.format(new Date());
                            mTime.setText(s);
                            GV_Adapter adapter=new GV_Adapter(MainActivity36.this,list,time);
                            mGv.setAdapter(adapter);
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}