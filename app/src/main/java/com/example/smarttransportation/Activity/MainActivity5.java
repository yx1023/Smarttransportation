package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity5 extends AppCompatActivity {

    private ImageView mMune5;
    private LinearLayout mLl1;
    private TextView mTv1;
    private LinearLayout mLl2;
    private TextView mTv2;
    private LinearLayout mLl3;
    private TextView mTv3;
    private LinearLayout mLl4;
    private TextView mTv4;
    private LinearLayout mLl5;
    private TextView mTv5;
    private LinearLayout mLl6;
    private TextView mTv6;
    private int state;
    private int temperature;
    private int humidity;
    private int illumination;
    private int co2;
    private int pm25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        initView();

        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getData();
            }
        },0,3000);
    }

    private void initView() {
        mMune5 = (ImageView) findViewById(R.id.mune5);
        mLl1 = (LinearLayout) findViewById(R.id.ll1);
        mTv1 = (TextView) findViewById(R.id.tv_1);
        mLl2 = (LinearLayout) findViewById(R.id.ll2);
        mTv2 = (TextView) findViewById(R.id.tv_2);
        mLl3 = (LinearLayout) findViewById(R.id.ll3);
        mTv3 = (TextView) findViewById(R.id.tv_3);
        mLl4 = (LinearLayout) findViewById(R.id.ll4);
        mTv4 = (TextView) findViewById(R.id.tv_4);
        mLl5 = (LinearLayout) findViewById(R.id.ll5);
        mTv5 = (TextView) findViewById(R.id.tv_5);
        mLl6 = (LinearLayout) findViewById(R.id.ll6);
        mTv6 = (TextView) findViewById(R.id.tv_6);
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
                    humidity = jsonObject1.getInt("humidity");
                    illumination = jsonObject1.getInt("illumination");
                    co2 = jsonObject1.getInt("co2");
                    pm25 = jsonObject1.getInt("pm25");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTv1.setText(temperature+"");
                            mTv2.setText(humidity+"");
                            mTv3.setText(illumination+"");
                            mTv4.setText(co2+"");
                            mTv5.setText(pm25+"");
                            getPath();
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void getPath(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("RoadId",1);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new  H().sendResutil("get_road_status", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    JSONArray jsonArray=jsonObject1.getJSONArray("ROWS_DETAIL");
                    JSONObject jsonObject2=new JSONObject(jsonArray.get(0).toString());
                    state = jsonObject2.getInt("state");
                    mTv6.setText(state+"");
                        getYZ();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void getYZ(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_threshold", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    JSONArray jsonArray=jsonObject1.getJSONArray("ROWS_DETAIL");
                    JSONObject jsonObject2=new JSONObject(jsonArray.get(0).toString());
                    int wd=jsonObject2.getInt("temperature");
                    int sd=jsonObject2.getInt("humidity");
                    int gz=jsonObject2.getInt("illumination");
                    int co=jsonObject2.getInt("co2");
                    int pm=jsonObject2.getInt("pm25");
                    int paths=jsonObject2.getInt("path");

                    if(temperature>wd){
                        mLl1.setBackgroundResource(R.drawable.qqqq);
                    }else {
                        mLl1.setBackgroundResource(R.drawable.wwww);
                    }

                    if(humidity>sd){
                        mLl2.setBackgroundResource(R.drawable.qqqq);
                    }else {
                        mLl2.setBackgroundResource(R.drawable.wwww);
                    }

                    if(illumination>gz){
                        mLl3.setBackgroundResource(R.drawable.qqqq);
                    }else {
                        mLl3.setBackgroundResource(R.drawable.wwww);
                    }

                    if(co2>co){
                        mLl4.setBackgroundResource(R.drawable.qqqq);
                    }else {
                        mLl4.setBackgroundResource(R.drawable.wwww);
                    }

                    if(pm25>pm){
                        mLl5.setBackgroundResource(R.drawable.qqqq);
                    }else {
                        mLl5.setBackgroundResource(R.drawable.wwww);
                    }

                    if(state>paths){
                        mLl6.setBackgroundResource(R.drawable.qqqq);
                    }else {
                        mLl6.setBackgroundResource(R.drawable.wwww);
                    }




                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}