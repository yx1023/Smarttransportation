package com.example.smarttransportation.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.TCC_Adapter;
import com.example.smarttransportation.Been.TCC;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity41 extends AppCompatActivity implements View.OnClickListener {

    private TextView mEt1;
    private TextView mEt2;
    private TextView mEt3;
    private TextView mEt4;
    private ListView mLvTcc;
    private Button mB1;
    private Button mB2;
    List<TCC>list=new ArrayList<>();
    List<TCC>lists=new ArrayList<>();
    TCC_Adapter adapter;
    TimePickerDialog dialog;
    private int mHour, mMinute, mSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main41);
        initView();
        getData();
    }

    private void initView() {
        mEt1 = (TextView) findViewById(R.id.et1);
        mEt2 = (TextView) findViewById(R.id.et2);
        mEt3 = (TextView) findViewById(R.id.et3);
        mEt4 = (TextView) findViewById(R.id.et4);
        mLvTcc = (ListView) findViewById(R.id.lv_tcc);
        mB1 = (Button) findViewById(R.id.b1);
        mB2 = (Button) findViewById(R.id.b2);
        mEt1.setOnClickListener(this);
        mEt2.setOnClickListener(this);
        mEt3.setOnClickListener(this);
        mEt4.setOnClickListener(this);
        mB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mEt1.getText().toString().equals("")){
                    Toast.makeText(MainActivity41.this, "入场日期不能为空", Toast.LENGTH_SHORT).show();

                }
                if(mEt2.getText().toString().equals("")){
                    Toast.makeText(MainActivity41.this, "入场时间不能为空", Toast.LENGTH_SHORT).show();

                }
                if(mEt3.getText().toString().equals("")){
                    Toast.makeText(MainActivity41.this, "出场日期不能为空", Toast.LENGTH_SHORT).show();

                }
                if(mEt4.getText().toString().equals("")){
                    Toast.makeText(MainActivity41.this, "出场时间不能为空", Toast.LENGTH_SHORT).show();

                }


                lists.clear();
                String RC=mEt1.getText()+" "+mEt2.getText();
                String CC=mEt3.getText()+" "+mEt4.getText();
                SimpleDateFormat format=new SimpleDateFormat("yyyy-M-d HH:mm:ss");

                try {
                    Date date=format.parse(RC);
                    Date date1=format.parse(CC);
                    for (int i = 0; i < list.size(); i++) {
                        TCC tcc=list.get(i);
                        Date date2=format.parse(tcc.getExit());
                        if(date2.after(date)){
                            if(date2.before(date1)){
                                lists.add(tcc);

                            }
                        }
                    }

                        adapter=new TCC_Adapter(MainActivity41.this,lists);
                        mLvTcc.setAdapter(adapter);





                } catch (ParseException e) {
                    Toast.makeText(MainActivity41.this, "请输入正确格式", Toast.LENGTH_SHORT).show();
                }


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
        new H().sendResutil("get_park_record", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<TCC>>(){}.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new TCC_Adapter(MainActivity41.this,list);
                            mLvTcc.setAdapter(adapter);
                        }
                    });


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    public void showDateDialog(TextView textView) {
        Calendar d = Calendar.getInstance(Locale.CHINA);
        // 创建一个日历引用d，通过静态方法getInstance() 从指定时区 Locale.CHINA 获得一个日期实例
        Date myDate = new Date();
        // 创建一个Date实例
        d.setTime(myDate);
        // 设置日历的时间，把一个新建Date实例myDate传入
        int year = d.get(Calendar.YEAR);
        int month = d.get(Calendar.MONTH);
        int day = d.get(Calendar.DAY_OF_MONTH);
        //初始化默认日期year, month, day
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            /**
             * 点击确定后，在这个方法中获取年月日
             */
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String date =year + "-" + monthOfYear + "-" + dayOfMonth;
                textView.setText(date);
            }
        },year, month, day);
        datePickerDialog.setMessage("请选择日期");
        datePickerDialog.show();
    }
    public void HmsDialog(TextView textView){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSecond = c.get(Calendar.SECOND);

        TimePickerDialog timePickerDialog=new TimePickerDialog(MainActivity41.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                updateTime(textView);
            }
        },mHour,mMinute,true);
        timePickerDialog.show();
    }
    private void updateTime(TextView textView) {
        String time = String.format("%02d:%02d:%02d", mHour, mMinute, mSecond);
        textView.setText(time);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et1:
                showDateDialog(mEt1);
                break;
            case R.id.et2:
                HmsDialog(mEt2);
                break;
            case R.id.et3:
                showDateDialog(mEt3);
                break;
            case R.id.et4:
                HmsDialog(mEt4);
                break;

        }
    }
}