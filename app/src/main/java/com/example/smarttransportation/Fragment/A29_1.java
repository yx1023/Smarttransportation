package com.example.smarttransportation.Fragment;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;


import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class A29_1 extends Fragment {

   private LineChart lc;
   private TextView tv;
    private int pm;
    private List<Float>list=new ArrayList<>();
    private List<String> TIME=new ArrayList<>();

    private List<Integer>list1=new ArrayList<>();

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            setLC();
            return false;
        }
    });


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.t_29,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lc=view.findViewById(R.id.lc1);
        tv=view.findViewById(R.id.text);
        tv.setText("PM2.5指数");
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getData();
            }
        },3000,3000);

    }

    public void getData(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_all_sense", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    pm = jsonObject1.getInt("pm25");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(0);
                        }
                    }).start();



                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setLC(){
        list.add((float) pm);
        if (list.size() == 20) {
            list.remove(0);
        }
        List<Entry>entries=new ArrayList<>();
        for (int i = 0; i < list.size()  ; i++) {
            entries.add(new Entry(i,list.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "");
        LineData lineData = new LineData(lineDataSet);
        //设置右下角标题
        Description description=new Description();
        description.setText("(秒)");
        description.setTextSize(30);
        description.setTextColor(R.color.black);
        lc.setDescription(description);


        //设置不显示左下角标题
        lc.getLegend().setEnabled(false);
        lc.setExtraTopOffset(20);
        lc.setExtraBottomOffset(30);

        lineDataSet.setCircleRadius(5);//设置圆点半径大小
        lineDataSet.setLineWidth(2);//设置折线的宽度
        lineDataSet.setColor(Color.BLACK);
        lineDataSet.setDrawCircleHole(false);//设置是否空心

        if(pm>200){
            list1.add(Color.RED);
            String s="当前PM2.5值："+pm;
            TZ(s);
        }else {
            list1.add(Color.GREEN);
        }
        if(list1.size()>=20){
            list1.remove(0);
        }


        lineDataSet.setCircleColors(list1);//依次设置每个圆点的颜色

        lineDataSet.setDrawValues(false);  //设置不显示折线上的数据

        lineDataSet.setMode(LineDataSet.Mode.LINEAR); // 设置折线类型
        lc.setData(lineData);
        setYAxis();
        setXAxis();
        lc.invalidate();//自动更新折线

    }
    public void setYAxis() {

        lc.getAxisRight().setEnabled(false); //不显示右侧Y轴
        YAxis yAxis1 = lc.getAxisLeft();
        yAxis1.setTextSize(20);

        yAxis1.setAxisLineWidth(2);
        yAxis1.setAxisLineColor(R.color.black);
       yAxis1.setDrawGridLines(false);
       yAxis1.setAxisMaximum(0);
        yAxis1.setAxisMaximum(600);
    }
    public void setXAxis() {

        SimpleDateFormat format = new SimpleDateFormat("ss");
        String str = format.format(new Date());
        TIME.add(str);
        if (TIME.size() > 20) {
            TIME.remove(0);
        }
        XAxis xAxis = lc.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(TIME));
        xAxis.setAxisLineWidth(2);
        xAxis.setAxisLineColor(R.color.black);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(TIME.size());
        xAxis.setGranularity(1f);
        xAxis.setTextSize(15);


    }
    public void TZ (String s){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                String channelID="01";
                String channelName="通知消息";
                int importance= NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel=new NotificationChannel(channelID,channelName,importance);
                NotificationManager notificationManager=(NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(channel);
                //调用要通知的消息
                Intent intent= new Intent(getActivity(),A29_1.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(getActivity(),0,intent,0);
                NotificationManager manager=(NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                Notification notification=new NotificationCompat.Builder(getActivity(),"01")
                        .setContentTitle("PM2.5")
                        .setContentText(s)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setAutoCancel(false)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .build();
                manager.notify(1,notification);
            }
    }


}
