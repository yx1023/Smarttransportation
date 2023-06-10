package com.example.smarttransportation.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity8 extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private ImageView mCaidan;
    private TextView mTime;
    private TextView mCar;
    private TextView mTv1;
    private Switch mSw1;
    private TextView mTv2;
    private Switch mSw2;
    private TextView mTv3;
    private Switch mSw3;
    private ImageView mTrafficSignal;
    private int year;
    private int month;
    private int day;
    int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        initView();
        getTime();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(i==1){
                    mTrafficSignal.setImageResource(R.drawable.hong);
                    i++;
                } else if (i == 2) {
                    mTrafficSignal.setImageResource(R.drawable.lv);
                    i++;
                } else if (i==3) {
                    mTrafficSignal.setImageResource(R.drawable.huang);
                    i=1;
                }
            }
        },0,1000);

    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mTime = (TextView) findViewById(R.id.time);
        mCar = (TextView) findViewById(R.id.car);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mSw1 = (Switch) findViewById(R.id.sw1);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mSw2 = (Switch) findViewById(R.id.sw2);
        mTv3 = (TextView) findViewById(R.id.tv3);
        mSw3 = (Switch) findViewById(R.id.sw3);
        mTrafficSignal = (ImageView) findViewById(R.id.traffic_signal);
        mSw1.setOnClickListener(this);
        mSw2.setOnClickListener(this);
        mSw3.setOnClickListener(this);
        mTime.setOnClickListener(this);
    }
    public void getTime(){
        Calendar calendar=Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        setTime();
    }
    public void setTime(){
        mTime.setText(year +"年"+ month +"月"+ day +"日");
        if(day%2==0){
            mCar.setText("双号出行车辆：2");
            mSw2.setChecked(true);
            mSw1.setChecked(false);
            mSw3.setChecked(false);
            mSw1.setEnabled(false);
            mSw3.setEnabled(false);
            mSw2.setEnabled(true);
            mTv1.setText("停");
            mTv2.setText("开");
            mTv3.setText("停");
        }else {
            mCar.setText("单号出行车辆：1,3");
            mSw2.setChecked(false);
            mSw1.setChecked(true);
            mSw3.setChecked(true);
            mSw2.setEnabled(false);
            mSw1.setEnabled(true);
            mSw3.setEnabled(true);
            mTv1.setText("开");
            mTv2.setText("停");
            mTv3.setText("开");
        }
    }
    public void setSW(Switch sw,TextView tv){
        if(sw.isChecked()==true) {
            tv.setText("开");
        }else {
            tv.setText("停");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sw1:
                    setSW(mSw1,mTv1);
                break;
            case R.id.sw2:
                setSW(mSw2,mTv2);
                break;
            case R.id.sw3:
                setSW(mSw3,mTv3);
                break;
            case R.id.time:
                DatePickerDialog dialog=new DatePickerDialog(this,this,year,month,day);
                dialog.show();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int Year, int Month, int dayOfMonth) {
            year=Year;
            month=Month;
            day=dayOfMonth;
            setTime();
    }
}