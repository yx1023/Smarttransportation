package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity7 extends AppCompatActivity {

    private ImageView mCaidan;
    private TextView mKG;
    private Switch mSw;
    private EditText mEtWd;
    private EditText mEtSd;
    private EditText mEtGz;
    private EditText mEtCo2;
    private EditText mEtPm;
    private EditText mEtDlzt;
    private Button mBc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        initView();
        setEt();
        getThreshold();
    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mKG = (TextView) findViewById(R.id.KG);
        mSw = (Switch) findViewById(R.id.sw);
        mEtWd = (EditText) findViewById(R.id.et_wd);
        mEtSd = (EditText) findViewById(R.id.et_sd);
        mEtGz = (EditText) findViewById(R.id.et_gz);
        mEtCo2 = (EditText) findViewById(R.id.et_co2);
        mEtPm = (EditText) findViewById(R.id.et_pm);
        mEtDlzt = (EditText) findViewById(R.id.et_dlzt);
        mBc = (Button) findViewById(R.id.bc);
        mBc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEtCo2.getText().toString().equals("") && !mEtDlzt.getText().toString().equals("")  &&
                        !mEtPm.getText().toString().equals("")  &&  !mEtSd.getText().toString().equals("")&&
                        !mEtWd.getText().toString().equals("")  &&  !mEtGz.getText().toString().equals("")){

                    setThreshold();

                }else {
                    Toast.makeText(MainActivity7.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mSw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEt();
            }
        });
    }
    public void setEt(){
        if(mSw.isChecked()){
            mEtCo2.setBackgroundResource(R.drawable.bg2);
            mEtWd.setBackgroundResource(R.drawable.bg2);
            mEtDlzt.setBackgroundResource(R.drawable.bg2);
            mEtPm.setBackgroundResource(R.drawable.bg2);
            mEtSd.setBackgroundResource(R.drawable.bg2);
            mEtGz.setBackgroundResource(R.drawable.bg2);
            mEtCo2.setEnabled(false);
            mEtWd.setEnabled(false);
            mEtDlzt.setEnabled(false);
            mEtPm.setEnabled(false);
            mEtSd.setEnabled(false);
            mEtGz.setEnabled(false);
            mKG.setText("开");

        }else {
            mEtCo2.setBackgroundResource(R.drawable.bg);
            mEtWd.setBackgroundResource(R.drawable.bg);
            mEtDlzt.setBackgroundResource(R.drawable.bg);
            mEtPm.setBackgroundResource(R.drawable.bg);
            mEtSd.setBackgroundResource(R.drawable.bg);
            mEtGz.setBackgroundResource(R.drawable.bg);
            mEtCo2.setEnabled(true);
            mEtWd.setEnabled(true);
            mEtDlzt.setEnabled(true);
            mEtPm.setEnabled(true);
            mEtSd.setEnabled(true);
            mEtGz.setEnabled(true);
            mKG.setText("关");
        }
    }

    public void getThreshold(){
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
                    int temperature=jsonObject2.getInt("temperature");
                    int humidity=jsonObject2.getInt("humidity");
                    int illumination=jsonObject2.getInt("illumination");
                    int co2=jsonObject2.getInt("co2");
                    int pm25=jsonObject2.getInt("pm25");
                    int path=jsonObject2.getInt("path");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mEtCo2.setText(co2+"");
                            mEtWd.setText(temperature+"");
                            mEtDlzt.setText(path+"");
                            mEtPm.setText(pm25+"");
                            mEtSd.setText(humidity+"");
                            mEtGz.setText(illumination+"");
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void setThreshold(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("temperature",Integer.parseInt(mEtWd.getText().toString()));
            jsonObject.put("humidity",Integer.parseInt(mEtSd.getText().toString()));
            jsonObject.put("illumination",Integer.parseInt(mEtGz.getText().toString()));
            jsonObject.put("co2",Integer.parseInt(mEtCo2.getText().toString()));
            jsonObject.put("pm25",Integer.parseInt(mEtPm.getText().toString()));
            jsonObject.put("path",Integer.parseInt(mEtDlzt.getText().toString()));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("set_threshold", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    String RESULT=jsonObject1.getString("RESULT");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(RESULT.equals("S")){
                                Toast.makeText(MainActivity7.this, "保存成功", Toast.LENGTH_SHORT).show();
                                getThreshold();
                            }else {
                                Toast.makeText(MainActivity7.this, "保存失败", Toast.LENGTH_SHORT).show();
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