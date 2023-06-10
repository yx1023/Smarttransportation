package com.example.smarttransportation.Activity;

import android.os.Bundle;
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
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity25 extends AppCompatActivity {

    private ImageView mMune17;
    private TextView mPM;
    private TextView mWD;
    private TextView mSD;
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mIv1;
    private TextView mIv2;
    private TextView mIv3;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main25);
        initView();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getData();
                for (int i = 1; i <4 ; i++) {
                    getRoad(i);
                }
            }
        },0,3000);
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mPM = (TextView) findViewById(R.id.PM);
        mWD = (TextView) findViewById(R.id.WD);
        mSD = (TextView) findViewById(R.id.SD);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mTv3 = (TextView) findViewById(R.id.tv3);
        mIv1 = (TextView) findViewById(R.id.iv1);
        mIv2 = (TextView) findViewById(R.id.iv2);
        mIv3 = (TextView) findViewById(R.id.iv3);
    }

    public void getData(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H() .sendResutil("get_all_sense", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {

                    JSONObject jsonObject1=new JSONObject(s);
                    int pm25=jsonObject1.getInt("pm25");
                    int temperature=jsonObject1.getInt("temperature");
                    int  humidity=jsonObject1.getInt("humidity");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPM.setText(pm25+"");
                            mWD.setText(temperature+"");
                            mSD.setText(humidity+"");
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void getRoad(int i){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("RoadId",i);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new     H().sendResutil("get_road_status", jsonObject.toString(), "POST", new Callback() {
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(i==1){
                                compare(i,mTv1,mIv1);
                            } else if (i == 2) {
                                compare(i,mTv2,mIv2);
                            } else if (i == 3) {
                                compare(i,mTv3,mIv3);
                            }

                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void compare(int number,TextView tv1,TextView tv2){
        switch (state){
            case 1:
                tv1.setText(number+"号道路：通畅");
                tv2.setBackgroundResource(R.color.q1);
                break;

            case 2:
                tv1.setText(number+"号道路：较通畅");
                tv2.setBackgroundResource(R.color.q2);
                break;

            case 3:
                tv1.setText(number+"号道路：拥挤");
                tv2.setBackgroundResource(R.color.q3);
                break;

            case 4:
                tv1.setText(number+"号道路：堵塞");
                tv2.setBackgroundResource(R.color.q4);
                break;

            case 5:
                tv1.setText(number+"号道路：爆表");
                tv2.setBackgroundResource(R.color.q5);
                break;
        }
    }
}