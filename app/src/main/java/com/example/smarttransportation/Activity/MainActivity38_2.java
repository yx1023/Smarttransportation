package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Been.Bus;
import com.example.smarttransportation.R;

import java.util.List;

public class MainActivity38_2 extends AppCompatActivity {

    private ImageView mFanhui;
    private ImageView mDitu;
    private TextView mXianlu;
    private TextView mMoney;
    private TextView mKm;
    private Button mBT1;
    List<Bus>list=MainActivity38.getList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main382);
        initView();
        Intent intent=getIntent();
        int id=intent.getIntExtra("id",0);
        Bus bus=list.get(id);
        mXianlu.setText(bus.getBusline().get(0)+"————"+bus.getBusline().get(bus.getBusline().size()-1));
        mKm.setText("里程："+bus.getMileage()+"km");
        mMoney.setText("票价：￥"+bus.getFares());
        if(id==1){
            mDitu.setImageResource(R.drawable.ditu2);
        }else {
            mDitu.setImageResource(R.drawable.ditu);
        }

    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mDitu = (ImageView) findViewById(R.id.ditu);
        mXianlu = (TextView) findViewById(R.id.xianlu);
        mMoney = (TextView) findViewById(R.id.money);
        mKm = (TextView) findViewById(R.id.km);
        mBT1 = (Button) findViewById(R.id.BT1);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity38_2.this,MainActivity38_3.class);
                intent.putExtra("xianlu",mXianlu.getText().toString());
                intent.putExtra("pj",mMoney.getText().toString());
                startActivity(intent);
                System.out.println(mXianlu.getText().toString()+"-------------");
            }
        });
    }
}