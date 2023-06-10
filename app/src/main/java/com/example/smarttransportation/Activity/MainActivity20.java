package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.GRZX_Adapter;
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

public class MainActivity20 extends AppCompatActivity {

    private ImageView mMune17;
    private ImageView mTx;
    private TextView mUsername;
    private TextView mName;
    private TextView mSex;
    private TextView mTel;
    private TextView mIdnumber;
    private TextView mTime;
    private ListView mLv;

    List<String>list=new ArrayList<>();
    List<String>list2=new ArrayList<>();
    GRZX_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main20);
        initView();
        getData();
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mTx = (ImageView) findViewById(R.id.tx);
        mUsername = (TextView) findViewById(R.id.username);
        mName = (TextView) findViewById(R.id.name);
        mSex = (TextView) findViewById(R.id.sex);
        mTel = (TextView) findViewById(R.id.tel);
        mIdnumber = (TextView) findViewById(R.id.idnumber);
        mTime = (TextView) findViewById(R.id.time);
        mLv = (ListView) findViewById(R.id.lv);
    }
    public void getData(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("Password",1234);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_root", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    String name=jsonObject1.getString("name");
                    String sex=jsonObject1.getString("sex");
                    String idnumber=jsonObject1.getString("idnumber");
                    String registered_time=jsonObject1.getString("registered_time");
                    String tel=jsonObject1.getString("tel");
                    String username=jsonObject1.getString("username");
                    list=new Gson().fromJson(jsonObject1.getJSONArray("plate").toString(),
                            new TypeToken<List<String>>(){}.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mName.setText("姓名："+name);
                            mTime.setText("注册时间"+registered_time);
                            mIdnumber.setText("身份证号："+idnumber);
                            mSex.setText("性别："+sex);
                            mTel.setText("手机："+tel);
                            mUsername.setText("用户名："+username);
                            if (sex.equals("男")){
                                mTx.setImageResource(R.drawable.touxiang_2);
                            }else {
                                mTx.setImageResource(R.drawable.touxiang_1);
                            }
                            for (int i=0;i<list.size();i++){
                                getMoney(i);
                            }


                        }
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void getMoney(int i){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("plate",list.get(i));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H() .sendResutil("get_balance_c", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                   String balance = jsonObject1.getString("balance");
                    list2.add(balance);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new GRZX_Adapter(MainActivity20.this,list,list2);
                            mLv.setAdapter(adapter);
                        }
                    });



                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}