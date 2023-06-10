package com.example.smarttransportation.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.DBmarager;

public class MainActivity38_5 extends AppCompatActivity {

    private ImageView mFanhui;
    private TextView mXianlu;
    private TextView mName;
    private TextView mTel;
    private TextView mDd;
    private TextView mYmd;
    private Button mBT3;
    private String pj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main385);
        initView();
        Intent intent=getIntent();
        mDd.setText(intent.getStringExtra("dd"));
        mXianlu.setText(intent.getStringExtra("xianlu"));
        mName.setText(intent.getStringExtra("name"));
        mYmd.setText(intent.getStringExtra("time"));
        mTel.setText(intent.getStringExtra("tel"));
        pj=intent.getStringExtra("pj");


    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mXianlu = (TextView) findViewById(R.id.xianlu);
        mName = (TextView) findViewById(R.id.name);
        mTel = (TextView) findViewById(R.id.tel);
        mDd = (TextView) findViewById(R.id.dd);
        mYmd = (TextView) findViewById(R.id.ymd);
        mBT3 = (Button) findViewById(R.id.BT3);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBmarager dBmarager=new DBmarager(MainActivity38_5.this);
                boolean b= dBmarager.isExist("dingdan");
                if(b==false){
                    String sql = "create table dingdan (" +
                            "id integer primary key autoincrement," +
                            "                           pj varchar," +
                            "                           time varchar," +
                            "                           xianlu varchar);";
                    dBmarager.createtable(sql);
                }
                ContentValues cv=new ContentValues();
                cv.put("xianlu",mXianlu.getText().toString());
                cv.put("time",mYmd.getText().toString());
                cv.put("pj",pj);
                dBmarager.insertDB("dingdan",cv);

                Toast.makeText(MainActivity38_5.this, "提交成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}