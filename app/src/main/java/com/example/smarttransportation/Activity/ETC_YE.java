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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ETC_YE extends AppCompatActivity {

    private ImageView mFanhui;
    private EditText mEtPlate;
    private TextView mTv;
    private Button mCX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc_ye);
        initView();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mEtPlate = (EditText) findViewById(R.id.et_plate);
        mTv = (TextView) findViewById(R.id.tv);
        mCX = (Button) findViewById(R.id.CX);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number= Integer.parseInt(mEtPlate.getText().toString());
                getMoney(number);
            }
        });

    }

    public void getMoney(int number){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("number",number);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_balance_b", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                if(s.equals("")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ETC_YE.this, "请输入正确的车辆编号", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    try {
                        JSONObject jsonObject1=new JSONObject(s);
                        String balance=jsonObject1.getString("balance");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mTv.setText(balance+"元");

                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });
    }
}