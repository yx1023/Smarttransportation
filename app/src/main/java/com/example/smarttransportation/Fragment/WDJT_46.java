package com.example.smarttransportation.Fragment;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.smarttransportation.Been.DL;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WDJT_46 extends Fragment {

    private TextView mPM;
    private TextView mTishi1;
    private VideoView mVV;
    private TextView mGuangzhao;
    private TextView mMax;
    private TextView SS;
    private TextView mMin;
    private TextView mTishi2;
    private TextView mT1;
    private TextView mT2;
    private TextView mT3;
    private TextView mT4;
    private TextView mT5;
    private int illumination;
    private int pm25;
    List<DL>list=new ArrayList<>();

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
                            switch (msg.what){
                                case 1:
                                    mGuangzhao.setText("光照强度当前值："+illumination);
                                    mPM.setText("PM2.5当前值："+pm25);
                                    if(pm25>200){
                                        TZ("PM2.5大于200，不适合出行",1);
                                        mVV.setVisibility(View.VISIBLE);
                                        playVideoRaw();
                                        mTishi1.setText("不适合出行");
                                    }else {
                                        mVV.setVisibility(View.INVISIBLE);
                                        mTishi1.setText("适合出行");
                                    }

                                    if(illumination<1110){
                                        TZ("光照较弱，出行请开灯",2);
                                    } else if (illumination > 3190) {
                                        TZ("光照较强，出行请戴墨镜",3);
                                    }

                                    mT4.setPadding((illumination/5),0,0,0);
                                    SS.setText(illumination+"");
                                    SS.setPadding((illumination/5)-50,0,0,0);

                                    break;
                                case 2:
                                    for (int i = 0; i < list.size(); i++) {
                                        if(list.get(i).getState()>3){
                                            TZ(list.get(i).getRoadId()+"号路口处于拥挤堵塞状态，请选择合适的线路",4);
                                        }
                                    }
                                    break;



                            }



            return false;
        }
    });


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wdjt_46, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getData();
                getR();
            }
        },0,3000);

    }

    private void initView(View view) {
        mPM = view. findViewById(R.id.PM);
        mTishi1 = view. findViewById(R.id.tishi1);
        mVV = view. findViewById(R.id.VV);
        mGuangzhao = view. findViewById(R.id.guangzhao);
        mMax = view. findViewById(R.id.max);
        mMin = view. findViewById(R.id.min);
        mTishi2 = view. findViewById(R.id.tishi2);
        mT1 = view. findViewById(R.id.t1);
        mT2 = view. findViewById(R.id.t2);
        mT3 = view. findViewById(R.id.t3);
        mT4 = view. findViewById(R.id.t4);
        mT5 = view. findViewById(R.id.t5);
        SS = view. findViewById(R.id.ss);
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
                    JSONObject  jsonObject1=new JSONObject(s);
                    illumination = jsonObject1.getInt("illumination");
                    pm25 = jsonObject1.getInt("pm25");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            handler.sendEmptyMessage(1);
                        }
                    }).start();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void playVideoRaw() {
        //获取raw.mp4的uri地址
        String uri = "android.resource://" +getActivity(). getPackageName() + "/" + R.raw.car1;
        mVV.setVideoURI(Uri.parse(uri));
        //让video获取焦点
        mVV.requestFocus();
        //监听播放完成，
        mVV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //重新开始播放
                mVV.start();
            }
        });
        mVV.start();
    }

    public void TZ (String s,int id){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            String channelID="01";
            String channelName="通知消息";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel=new NotificationChannel(channelID,channelName,importance);
            NotificationManager notificationManager=(NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            //调用要通知的消息
            Intent intent= new Intent(getActivity(),WDJT_46.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(getActivity(),0,intent,0);
            NotificationManager manager=(NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            Notification notification=new NotificationCompat.Builder(getActivity(),"01")
                    .setContentTitle("")
                    .setContentText(s)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                    .setAutoCancel(false)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .build();
            manager.notify(id,notification);
        }
    }

    public void getR(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("RoadId",0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_road_status", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s) ;
                    list=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<DL>>(){}.getType());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(2);
                        }
                    }).start();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }



}
