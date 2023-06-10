package com.example.smarttransportation.Activity;

import android.content.ContentValues;
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
import com.example.smarttransportation.Utility.DBmarager;
import com.example.smarttransportation.Utility.H;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ETC_CZ extends AppCompatActivity {

    private ImageView mFanhui;
    private EditText mEtPlate;
    private TextView mTen;
    private TextView mFifty;
    private TextView mHundred;
    private EditText mEtMoney;
    private Button mTJ;
    int a=1;
    int b=1;
    int c=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc_cz);
        initView();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mEtPlate = (EditText) findViewById(R.id.et_plate);
        mTen = (TextView) findViewById(R.id.ten);
        mFifty = (TextView) findViewById(R.id.fifty);
        mHundred = (TextView) findViewById(R.id.hundred);
        mEtMoney = (EditText) findViewById(R.id.et_money);
        mTJ = (Button) findViewById(R.id.TJ);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a==1){
                    mTen.setBackgroundResource(R.drawable.heihuang);
                    mEtMoney.setText(10+"");
                    a=2;
                } else if (a == 2) {
                    mTen.setBackgroundResource(R.drawable.huikuang);
                    mEtMoney.setText("");
                    a=1;
                }
            }
        });
        mFifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b==1){
                    mFifty.setBackgroundResource(R.drawable.heihuang);
                    mEtMoney.setText(50+"");
                    b=2;
                } else if (b == 2) {
                    mFifty.setBackgroundResource(R.drawable.huikuang);
                    mEtMoney.setText("");
                    b=1;
                }
            }
        });
        mHundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c==1){
                    mHundred.setBackgroundResource(R.drawable.heihuang);
                    mEtMoney.setText(100+"");
                    c=2;
                } else if (c == 2) {
                    mHundred.setBackgroundResource(R.drawable.huikuang);
                    mEtMoney.setText("");
                    c=1;
                }
            }
        });
        mTJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEtPlate.getText().toString().equals("")  &&  !mEtMoney.getText().toString().equals("")){
                    String plate=mEtPlate.getText().toString();
                    int money= Integer.parseInt(mEtMoney.getText().toString());
                    CZ(plate,money);

                }else {
                    Toast.makeText(ETC_CZ.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    public void CZ(String plate,int money){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("plate",plate);
            jsonObject.put("balance",money);
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
                    String RESULT=jsonObject1.getString("RESULT");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(RESULT.equals("S")){
                                Toast.makeText(ETC_CZ.this, "充值成功", Toast.LENGTH_SHORT).show();
                                SimpleDateFormat format=new SimpleDateFormat("hh:mm");
                                String time=format.format(new Date());
                                inSQL(plate,time,money);
                            }else {
                                Toast.makeText(ETC_CZ.this, "充值失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void inSQL(String plate,String time,int money){
        DBmarager dBmarager=new DBmarager(this);
        boolean b=dBmarager.isExist("etc");
        if(b==false){
            String sql = "create table etc (" +
                    "id integer primary key autoincrement," +
                    "                           plate varchar," +  //车牌号
                    "                           number integer," + //车辆号
                    "                           time varchar," +//时间
                    "                           money integer);";//充值金额
            dBmarager.createtable(sql);
        }
        int number=0;
        if(plate.equals("鲁A10001")){
            number=1;
        } else if (plate.equals("鲁A10002")) {
            number=2;
        }else if (plate.equals("鲁A10003")) {
            number=3;
        }else if (plate.equals("鲁A10004")) {
            number=4;
        }else if (plate.equals("鲁A10005")) {
            number=5;
        }else if (plate.equals("鲁A10006")) {
            number=6;
        }
        ContentValues cv=new ContentValues();
        cv.put("plate",plate);
        cv.put("number",number);
        cv.put("time",time);
        cv.put("money",money);
        dBmarager.insertDB("etc",cv);
    }

}