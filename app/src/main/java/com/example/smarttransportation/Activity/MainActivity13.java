package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity13 extends AppCompatActivity {
    int q=1;
    private ImageView mCaidan;
    private TextView mHCKSL1;
    private TextView mHCKSL2;
    private TextView mHCKSL3;
    private TextView mHCGS;
    private TextView mXYL;
    private TextView mLXL;
    private TextView mYYL;
    private TextView mXFL;
    private TextView mTCC;
    private TextView mNYR;
    private TextView mXQ;
    private ImageView mShaxin;
    private TextView mWD;
    private TextView mSD;
    private TextView mPM25;
    private ImageView mJJ1;
    private ImageView mJJ2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main13);
        initView();
        getTime();
        getKQZL();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setImage();
            }
        },1000,1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getDLYJ(1,mXYL);
                getDLYJ(2,mLXL);
                getDLYJ(3,mYYL);
                getDLYJ(4,mXFL);
                getDLYJ(5,mHCKSL1);
                getDLYJ(5,mHCKSL2);
                getDLYJ(5,mHCKSL3);
                getDLYJ(6,mHCGS);
                getDLYJ(7,mTCC);
            }
        },0,3000);
    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mHCKSL1 = (TextView) findViewById(R.id.HCKSL_1);
        mHCKSL2 = (TextView) findViewById(R.id.HCKSL_2);
        mHCKSL3 = (TextView) findViewById(R.id.HCKSL_3);
        mHCGS = (TextView) findViewById(R.id.HCGS);
        mXYL = (TextView) findViewById(R.id.XYL);
        mLXL = (TextView) findViewById(R.id.LXL);
        mYYL = (TextView) findViewById(R.id.YYL);
        mXFL = (TextView) findViewById(R.id.XFL);
        mTCC = (TextView) findViewById(R.id.TCC);
        mNYR = (TextView) findViewById(R.id.NYR);
        mXQ = (TextView) findViewById(R.id.XQ);
        mShaxin = (ImageView) findViewById(R.id.shaxin);
        mWD = (TextView) findViewById(R.id.WD);
        mSD = (TextView) findViewById(R.id.SD);
        mPM25 = (TextView) findViewById(R.id.PM25);
        mJJ1 = (ImageView) findViewById(R.id.JJ1);
        mJJ2 = (ImageView) findViewById(R.id.JJ2);
        mShaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getKQZL();
            }
        });
    }

    public void getKQZL(){
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
                    int  temperature=jsonObject1.getInt("temperature");
                    int  humidity=jsonObject1.getInt("humidity");
                    int pm25=jsonObject1.getInt("pm25");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWD.setText("温度："+temperature+"℃");
                            mSD.setText("相对湿度："+humidity+"%");
                            mPM25.setText("PM2.5："+pm25+"µg/m3");
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }
    public void getDLYJ(int id,TextView view){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("RoadId",id);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_road_status", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    JSONArray jsonArray=jsonObject1.getJSONArray("ROWS_DETAIL");
                    JSONObject jsonObject2=new JSONObject(jsonArray.getJSONObject(0).toString());
                    int state=jsonObject2.getInt("state");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(state==1){
                                view.setBackgroundResource(R.color.CT);
                            } else if (state==2) {
                                view.setBackgroundResource(R.color.HX);
                            } else if (state==3) {
                                view.setBackgroundResource(R.color.YB);
                            }else if (state==4) {
                                view.setBackgroundResource(R.color.ZD);
                            }else if (state==5) {
                                view.setBackgroundResource(R.color.YZ);
                            }
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void getTime(){
        final Calendar c = Calendar.getInstance();
        String  mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String  mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String  mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mNYR.setText(mYear+"-"+mMonth+"-"+mDay);
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mXQ.setText("星期日");
        }else if("2".equals(mWay)){
            mXQ.setText("星期一");
        }else if("3".equals(mWay)){
            mXQ.setText("星期二");
        }else if("4".equals(mWay)){
            mXQ.setText("星期三");
        }else if("5".equals(mWay)){
            mXQ.setText("星期四");
        }else if("6".equals(mWay)){
            mXQ.setText("星期五");
        }else if("7".equals(mWay)){
            mXQ.setText("星期六");
        }
    }
    public void setImage(){
        if(q==1){
            mJJ1.setImageResource(R.drawable.jiaojing1_2);
            mJJ2.setImageResource(R.drawable.jiaojing2_2);
            q=2;
        } else if (q==2) {
            mJJ1.setImageResource(R.drawable.jiaojing1_1);
            mJJ2.setImageResource(R.drawable.jiaojing2_1);
            q=1;
        }
    }
}