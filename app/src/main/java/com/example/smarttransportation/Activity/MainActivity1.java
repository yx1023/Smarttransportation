package com.example.smarttransportation.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Utility.DBmarager;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity1 extends AppCompatActivity {

    private ImageView mCaidan;
    private TextView mMoney;
    private Spinner mSp;
    private EditText mEt;
    private Button mCx;
    private Button mCz;
    private String balance;
    private String plate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initView();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, R.layout.spinne,new String[]{"1","2","3"});
        mSp.setAdapter(adapter);

        getMoney(1);
    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mMoney = (TextView) findViewById(R.id.money);
        mSp = (Spinner) findViewById(R.id.sp);
        mEt = (EditText) findViewById(R.id.et);
        mCx = (Button) findViewById(R.id.cx);
        mCz = (Button) findViewById(R.id.cz);
        mCaidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu=new PopupMenu(MainActivity1.this,mCaidan);
                menu.inflate(R.menu.menu);
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent=new Intent();
                        switch (item.getItemId()){
                            case R.id.ZDGL:
                                intent.setClass(MainActivity1.this, MainActivity3.class);
                                startActivity(intent);
                                break;
                        }


                        return false;
                    }
                });
            }
        });
        mCx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number= Integer.parseInt((String) mSp.getSelectedItem());
                getMoney(number);
            }
        });
        mCz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number= Integer.parseInt((String) mSp.getSelectedItem());
                getPlate(number);
                SimpleDateFormat format=new SimpleDateFormat("yyyy.MM.dd hh:mm");
                String time=format.format(new Date());
                setSQL(time, Integer.parseInt(mEt.getText().toString()), number);

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
        new H() .sendResutil("get_balance_b", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    balance = jsonObject1.getString("balance");


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMoney.setText(balance+"元");
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void getPlate(int number){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("number",number);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new   H().sendResutil("get_plate", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    plate = jsonObject1.getString("plate");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!mEt.getText().toString().equals("")){
                                int money= Integer.parseInt(mEt.getText().toString());

                                if(money>=1 && money<999){
                                    setBalance(plate,money);
                                }else {
                                    Toast.makeText(MainActivity1.this, "请输入1~999范围的金额", Toast.LENGTH_SHORT).show();
                                }



                            }else {
                                Toast.makeText(MainActivity1.this, "请输入充值金额", Toast.LENGTH_SHORT).show();
                            }



                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void setBalance(String plate,int balance){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("plate",plate);
            jsonObject.put("balance",balance);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("set_balance", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    String r=jsonObject1.getString("RESULT");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (r.equals("S")){
                                Toast.makeText(MainActivity1.this, "充值成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity1.this, "充值失败", Toast.LENGTH_SHORT).show();
                            }
                            mEt.setText("");
                            int number= Integer.parseInt((String) mSp.getSelectedItem());
                            getMoney(number);
                        }
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setSQL(String time,int  money,int number){
        DBmarager dBmarager = new DBmarager(MainActivity1.this);
        boolean result = dBmarager.isExist("tabless");
        if (result == false) {
            String sql = "create table tabless (" +
                    "id integer primary key autoincrement," +
                    "                           name varchar," +  //操作人
                    "                           number integer," + //车辆号
                    "                           time varchar," +//时间
                    "                           money integer);";//充值金额
            dBmarager.createtable(sql);
        }
        ContentValues cv = new ContentValues();
        cv.put("time", time);
        cv.put("money", money);
        cv.put("number", number);
        cv.put("name","admin");
        dBmarager.insertDB("tabless", cv);
    }
}