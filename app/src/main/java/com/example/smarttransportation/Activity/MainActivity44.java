package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Been.Login;
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

public class MainActivity44 extends AppCompatActivity implements View.OnClickListener {

    private ImageView mShezhi;
    private EditText mEtNumber;
    private EditText mEtMima;
    private TextView mZc;
    private TextView mZh;
    private Button mDl;
    private List<Login>list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main44);
        initView();

    }

    private void initView() {
        mShezhi = (ImageView) findViewById(R.id.shezhi);
        mEtNumber = (EditText) findViewById(R.id.et_number);
        mEtMima = (EditText) findViewById(R.id.et_mima);
        mZc = (TextView) findViewById(R.id.zc);
        mZh = (TextView) findViewById(R.id.zh);
        mDl = (Button) findViewById(R.id.dl);
        mDl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEtNumber.getText().toString().equals("") && !mEtMima.getText().toString().equals("")){
                    login();
                }else {
                    Toast.makeText(MainActivity44.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mZc.setOnClickListener(this);
        mZh.setOnClickListener(this);
    }
    public void login() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_login", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<Login>>(){}.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String number=mEtNumber.getText().toString();
                            String mima=mEtMima.getText().toString();
                            for (int i = 0; i < list.size(); i++) {
                                if(list.get(i).getUsername().equals(number)){

                                        if(list.get(i).getPassword().equals(mima)){
                                            Toast.makeText(MainActivity44.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                            break;
                                        }else {
                                            Toast.makeText(MainActivity44.this, "密码错误", Toast.LENGTH_SHORT).show();
                                            break;
                                        }

                                }else {
                                    if(i==list.size()-1){
                                        Toast.makeText(MainActivity44.this, "用户名错误", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
            switch (v.getId()){
                case R.id.zc:
                    intent.setClass(this,MainActivity44_zc.class);
                    startActivity(intent);
                    break;
                case R.id.zh:
                    intent.setClass(this,MainActivity44_zh.class);
                    startActivity(intent);
                    break;
            }
    }
}