package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class MainActivity23 extends AppCompatActivity {

    private ImageView mMune17;
    private TextView mTv1;
    private EditText mEt;
    private Button mB1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main23);
        initView();
        getYZ();
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mEt = (EditText) findViewById(R.id.et);

        mB1 = (Button) findViewById(R.id.b1);
        mB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEt.getText().toString().equals("")){
                    Toast.makeText(MainActivity23.this, "请输入阈值", Toast.LENGTH_SHORT).show();
                }else {
                    int i= Integer.parseInt(mEt.getText().toString());
                    setYZ(i);
                    getYZ();
                }

            }
        });
    }

    private void setYZ(int i) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
            jsonObject.put("threshold", i);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("set_car_threshold", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    String RESULT  =jsonObject1.getString("RESULT");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(RESULT.equals("S")){
                                Toast.makeText(MainActivity23.this, "设置成功", Toast.LENGTH_SHORT).show();
                                mEt.setText("");
                            }else {
                                Toast.makeText(MainActivity23.this, "设置失败", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    public void getYZ() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_car_threshold", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    JSONArray jsonArray = jsonObject1.getJSONArray("ROWS_DETAIL");
                    JSONObject jsonObject2 = new JSONObject(jsonArray.get(0).toString());
                    int threshold = jsonObject2.getInt("threshold");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTv1.setText("我的1-4号车账户余额告警阈值为  " + threshold + "  元");
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}