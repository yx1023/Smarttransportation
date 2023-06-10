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

public class MainActivity17 extends AppCompatActivity {

    private ImageView mMune17;
    private LinearLayout mZwx;
    private TextView mZWX1;
    private TextView mZWX2;
    private TextView mZWX3;
    private LinearLayout mGm;
    private TextView mGMZS1;
    private TextView mGMZS2;
    private TextView mGMZS3;
    private LinearLayout mCy;
    private TextView mCYZS1;
    private TextView mCYZS2;
    private TextView mCYZS3;
    private LinearLayout mYd;
    private TextView mYDZS1;
    private TextView mYDZS2;
    private TextView mYDZS3;
    private LinearLayout mKq;
    private TextView mKQWRZS1;
    private TextView mKQWRZS2;
    private TextView mKQWRZS3;
    private int temperature;
    private int illumination;
    private int co2;
    private int pm25;
    private int wd;
    private int pm;
    private int co;
    private int gz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main17);
        initView();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getData();
                getThreshold();
            }
        }, 0, 3000);

    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mZwx = (LinearLayout) findViewById(R.id.zwx);
        mZWX1 = (TextView) findViewById(R.id.ZWX_1);
        mZWX2 = (TextView) findViewById(R.id.ZWX_2);
        mZWX3 = (TextView) findViewById(R.id.ZWX_3);
        mGm = (LinearLayout) findViewById(R.id.gm);
        mGMZS1 = (TextView) findViewById(R.id.GMZS_1);
        mGMZS2 = (TextView) findViewById(R.id.GMZS_2);
        mGMZS3 = (TextView) findViewById(R.id.GMZS_3);
        mCy = (LinearLayout) findViewById(R.id.cy);
        mCYZS1 = (TextView) findViewById(R.id.CYZS_1);
        mCYZS2 = (TextView) findViewById(R.id.CYZS_2);
        mCYZS3 = (TextView) findViewById(R.id.CYZS_3);
        mYd = (LinearLayout) findViewById(R.id.yd);
        mYDZS1 = (TextView) findViewById(R.id.YDZS_1);
        mYDZS2 = (TextView) findViewById(R.id.YDZS_2);
        mYDZS3 = (TextView) findViewById(R.id.YDZS_3);
        mKq = (LinearLayout) findViewById(R.id.kq);
        mKQWRZS1 = (TextView) findViewById(R.id.KQWRZS_1);
        mKQWRZS2 = (TextView) findViewById(R.id.KQWRZS_2);
        mKQWRZS3 = (TextView) findViewById(R.id.KQWRZS_3);
    }


    public void setText() {
        mZWX2.setText("(" + illumination + ")");
        if (illumination > 0 && illumination < 1000) {
            mZWX1.setText("弱");

            mZWX3.setText("辐射较弱，涂擦SPF12~15、PA+护肤品");
        } else if (illumination >= 1000 && illumination <= 3000) {
            mZWX1.setText("中等");

            mZWX3.setText("涂擦SPF大于15、PA+防晒护肤品");
        } else if (illumination > 3000) {
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
            mCYZS3.setText("建议穿短袖衬衫、单裤等服装 ");
        } else if (temperature > 21) {
            mCYZS1.setText("热");
            mCYZS3.setText("适合穿T恤、短薄外套等夏季服装 ");
        }

        mYDZS2.setText("(" + co2 + ")");
        if (co2 < 300) {
            mYDZS1.setText("适宜");
            mYDZS3.setText("气候适宜，推荐您进行户外运动");
        } else if (co2 >= 300 && co2 <= 6000) {
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

    public void getData() {
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
                            setText();
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void getThreshold() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_threshold", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    JSONArray jsonArray = jsonObject1.getJSONArray("ROWS_DETAIL");
                    JSONObject jsonObject2 = new JSONObject(jsonArray.getJSONObject(0).toString());
                    wd = jsonObject2.getInt("temperature");
                    pm = jsonObject2.getInt("pm25");
                    co = jsonObject2.getInt("co2");
                    gz = jsonObject2.getInt("illumination");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (illumination > gz) {
                                mZwx.setBackgroundResource(R.color.w);
                            }else {
                                mZwx.setBackgroundResource(R.color.q);
                            }

                            if (temperature > wd) {
                                mGm.setBackgroundResource(R.color.w);
                                mCy.setBackgroundResource(R.color.q);
                            }else {
                                mGm.setBackgroundResource(R.color.q);
                                mCy.setBackgroundResource(R.color.w);
                            }

                            if (co2 > co) {
                                mYd.setBackgroundResource(R.color.w);
                            }else {
                                mYd.setBackgroundResource(R.color.q);
                            }

                            if (pm25 > pm) {
                                mKq.setBackgroundResource(R.color.w);
                            }else {
                                mKq.setBackgroundResource(R.color.q);
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