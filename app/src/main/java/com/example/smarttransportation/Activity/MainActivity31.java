package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.YJ_Adapter;
import com.example.smarttransportation.Been.YJFK;
import com.example.smarttransportation.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity31 extends AppCompatActivity {

    private ImageView mMune17;
    private EditText mBT;
    private EditText mYJ;
    private EditText mTel;
    private Button mBTJ;
    static List<YJFK> list = new ArrayList<>();

    private TextView mWode;

    public static List<YJFK> getList() {

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main31);
        initView();
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mBT = (EditText) findViewById(R.id.BT);
        mYJ = (EditText) findViewById(R.id.YJ);
        mTel = (EditText) findViewById(R.id.tel);
        mBTJ = (Button) findViewById(R.id.B_TJ);

        mWode = (TextView) findViewById(R.id.wode);
        mWode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity31.this,WDYJ.class);
                startActivity(intent);
            }
        });
        mBTJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TJ();
            }
        });
    }

    public void TJ() {
        if (!mBT.getText().equals("") && !mTel.getText().equals("") && !mYJ.getText().equals("")) {
            YJFK yjfk = new YJFK();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
            String time = format.format(new Date());
            yjfk.setBT(mBT.getText().toString());
            yjfk.setTime(time);
            list.add(yjfk);
            mBT.setText("");
            mTel.setText("");
            mYJ.setText("");
            Toast.makeText(this, "已提交", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}