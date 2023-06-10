package com.example.smarttransportation.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarttransportation.Adapter.BJ_Adapter;
import com.example.smarttransportation.Been.XX;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;

import org.json.JSONArray;
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

public class XXCX extends Fragment {

    Spinner sp;
    ListView lv;
    TextView ts;
    private int temperature;
    private int humidity;
    private int illumination;
    private int co2;
    private int pm25;
    private int temperatures;
    private int humiditys;
    private int illuminations;
    private int co2s;
    private int pm25s;
     List<XX>list=new ArrayList<>();
    List<XX>list1=new ArrayList<>();
     List<XX>list2=new ArrayList<>();
     List<XX>list3=new ArrayList<>();
     List<XX>list4=new ArrayList<>();
    List<XX>list5=new ArrayList<>();

    static List<Integer>WD=new ArrayList<>();
    static List<Integer>SD=new ArrayList<>();
    static List<Integer>PM=new ArrayList<>();
    static List<Integer>CO=new ArrayList<>();
    static List<Integer>GZ=new ArrayList<>();

    BJ_Adapter adapter;
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if(list.size()!=0){
                ts.setVisibility(View.GONE);
            }
            lv.setAdapter(adapter);
            return false;
        }
    });
    private Timer timer=new Timer();

    public static List<Integer> getWD() {
        return WD;
    }

    public static List<Integer> getSD() {
        return SD;
    }
    public static List<Integer> getCO() {
        return CO;
    }
    public static List<Integer> getPM() {
        return PM;
    }
    public static List<Integer> getGZ() {
        return GZ;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xxcx,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sp=view.findViewById(R.id.sp);
        lv=view.findViewById(R.id.LV);
        ts=view.findViewById(R.id.TS);

        ArrayAdapter<String>adapter1=new ArrayAdapter<>(getContext(),R.layout.spinne,new String[]{"全部","湿度","温度","CO2","光照","PM2.5"});
        sp.setAdapter(adapter1);



        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s=sp.getItemAtPosition(position).toString();

                switch (s) {
                    case "全部":
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                setData();
                                goTo(list);
                            }
                        }, 0, 3000);

                        break;
                    case "温度":
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                setData();
                                goTo(list1);
                            }
                        }, 0, 3000);
                        break;
                    case "湿度":
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                setData();
                                goTo(list2);
                            }
                        }, 0, 3000);
                        break;
                    case "光照":
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                setData();
                                goTo(list3);
                            }
                        }, 0, 3000);
                        break;
                    case "CO2":
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                setData();
                                goTo(list4);
                            }
                        }, 0, 3000);
                        break;
                    case "PM2.5":
                        timer.cancel();
                        timer = new Timer();

                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                               setData();
                                goTo(list5);
                            }
                        }, 0, 3000);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        new H().sendResutil("get_all_sense", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    temperature = jsonObject1.getInt("temperature");
                    humidity = jsonObject1.getInt("humidity");
                    illumination = jsonObject1.getInt("illumination");
                    co2 = jsonObject1.getInt("co2");
                    pm25 = jsonObject1.getInt("pm25");
                    System.out.println(temperature+"************");





                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void getYZ(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_threshold", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    JSONArray jsonArray=jsonObject1.getJSONArray("ROWS_DETAIL");
                    JSONObject jsonObject2=new JSONObject(jsonArray.get(0).toString());
                    temperatures = jsonObject2.getInt("temperature");
                    humiditys = jsonObject2.getInt("humidity");
                    illuminations = jsonObject2.getInt("illumination");
                    co2s = jsonObject2.getInt("co2");
                    pm25s = jsonObject2.getInt("pm25");





                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void setData(){

        getData();
        getYZ();
        if(temperature>temperatures){
            XX xx=new XX();
            xx.setYz(temperatures);
            xx.setDqz(temperature);
            xx.setBj("【温度】报警");
            WD.add(1);
            list.add(xx);
            list1.add(xx);
        }
        if(humidity>humiditys){
            XX xx=new XX();
            xx.setYz(humiditys);
            xx.setDqz(humidity);
            xx.setBj("【湿度】报警");
            SD.add(1);
            list.add(xx);
            list2.add(xx);
        }
        if(illumination>illuminations){
            XX xx=new XX();
            xx.setYz(illuminations);
            xx.setDqz(illumination);
            xx.setBj("【光照】报警");
            GZ.add(1);
            list.add(xx);

            list3.add(xx);
        }
        if(co2>co2s){
            XX xx=new XX();
            xx.setYz(co2s);
            xx.setDqz(co2);
            xx.setBj("【CO2】报警");
            CO.add(1);
            list.add(xx);
            list4.add(xx);
        }
        if(pm25>pm25s){
            XX xx=new XX();
            xx.setYz(pm25s);
            xx.setDqz(pm25);
            xx.setBj("【PM2.5】报警");
            PM.add(1);
            list.add(xx);
            list5.add(xx);
        }

    }
    public void goTo(List<XX> list){


                adapter=null;

                adapter=new BJ_Adapter(getContext(),list);
                handler.sendEmptyMessage(0);



    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
    }
}
