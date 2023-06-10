package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.Tc_Adapter;
import com.example.smarttransportation.Been.TC;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity42_2 extends AppCompatActivity {

    private ImageView mMune36;
    private TextView mName;
    private TextView mMoney;
    private TextView mCw;
    private Button mBT;
    List<TC> list=new ArrayList<>();
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main422);
        initView();
        Intent intent=getIntent();
        name = intent.getStringExtra("name");
        System.out.println(name);
        getData();

    }

    private void initView() {
        mMune36 = (ImageView) findViewById(R.id.mune36);
        mName = (TextView) findViewById(R.id.name);
        mMoney = (TextView) findViewById(R.id.money);
        mCw = (TextView) findViewById(R.id.cw);
        mBT = (Button) findViewById(R.id.BT);
        mMune36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBT.setOnClickListener(new View.OnClickListener() {
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
        new H() .sendResutil("get_park_data", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<TC>>(){}.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           TC tc=new TC();
                           for (int i=0;i<list.size();i++){
                               if(list.get(i).getParking_name().equals(name)){
                                   tc=list.get(i);
                               }
                           }
                           mName.setText(tc.getParking_name());
                           mMoney.setText("收费标准"+tc.getReference());
                           mCw.setText("剩余车位："+tc.getCarport()+"/"+tc.getDistance());
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}