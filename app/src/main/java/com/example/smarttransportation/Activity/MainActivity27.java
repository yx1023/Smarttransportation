package com.example.smarttransportation.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity27 extends AppCompatActivity {

    private ImageView mMune17;
    private TextView mPM;
    private TextView mWD;
    private TextView mSD;
    private TextView mTv1;
    private TextView mText1;
    private TextView mTv2;
    private TextView mText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main27);
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
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mPM = (TextView) findViewById(R.id.PM);
        mWD = (TextView) findViewById(R.id.WD);
        mSD = (TextView) findViewById(R.id.SD);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mText1 = (TextView) findViewById(R.id.text1);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mText2 = (TextView) findViewById(R.id.text2);
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
                    int temperature=jsonObject1.getInt("temperature");
                    int humidity=jsonObject1.getInt("humidity");
                    int illumination=jsonObject1.getInt("illumination");
                    int pm25=jsonObject1.getInt("pm25");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPM.setText(pm25+"");
                            mWD.setText(temperature+"");
                            mSD.setText(humidity+"");

                            if(pm25>=0 && pm25<100){
                                mTv2.setText("良好");
                                mText2.setText("气象条件会对晨练影响不大");
                                mTv2.setTextColor(Color.BLACK);
                            } else if (pm25>=100 && pm25<200) {
                                mTv2.setText("轻度");
                                mText2.setText("受天气影响，较不宜晨练");
                                mTv2.setTextColor(Color.BLACK);
                            }else if (pm25>=200 && pm25<300) {
                                mTv2.setText("重度");
                                mText2.setText("减少外出，出行注意戴口罩");
                                mTv2.setTextColor(Color.RED);
                            }else {
                                mTv2.setText("爆表");
                                mText2.setText("停止一切外出活动");
                                mTv2.setTextColor(Color.RED);
                            }

                            if(illumination>0 && illumination<1500){
                                mTv1.setText("非常弱");
                                mText1.setText("您无须担心紫外线");
                                mTv1.setTextColor(Color.BLACK);
                            } else if (illumination>=1500 && illumination<=3000) {
                                mTv1.setText("弱");
                                mText1.setText("外出适当涂抹低倍数防晒霜");
                                mTv1.setTextColor(Color.BLACK);
                            }else if (illumination>3000) {
                                mTv1.setText("强");
                                mText1.setText("外出需要涂抹中倍数防晒霜");
                                mTv1.setTextColor(Color.RED);
                            }


                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}