package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.HLD_Adapter;
import com.example.smarttransportation.Been.HLD;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity21 extends AppCompatActivity {

    private ImageView mMune17;
    private ImageView mLight;
    private Spinner mSp;
    private ListView mLv;
    HLD_Adapter adapter;
    int temp=1;
    List<HLD>list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main21);
        initView();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setLight();
            }
        },0,1000);

        ArrayAdapter<String>adapter1=new ArrayAdapter<>(this,R.layout.spinne,new String[]{"路口升序","路口降序","红灯升序","红灯降序","黄灯升序","黄灯降序","绿灯升序","绿灯降序"});
        mSp.setAdapter(adapter1);



    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mLight = (ImageView) findViewById(R.id.light);
        mSp = (Spinner) findViewById(R.id.sp);
        mLv = (ListView) findViewById(R.id.lv);

        mSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                goTo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void goTo(){
        list.clear();
        for (int i = 1; i < 6; i++) {
            getData(i);
        }
    }

    public void setLight(){
        if(temp==1){
            mLight.setImageResource(R.drawable.hong1);
            temp=2;
        } else if (temp==2) {
            mLight.setImageResource(R.drawable.huang1);
            temp=3;
        } else if (temp == 3) {
            mLight.setImageResource(R.drawable.lv1);
            temp=1;
        }
    }

    public void getData(int i){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("number",i);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_traffic_light", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {

                    JSONObject jsonObject1=new JSONObject(s);
                    list.add(new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").optJSONObject(0).toString(),HLD.class));


                    if(list.size()==5){
                        sort();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                if(adapter==null){
                                    adapter=new HLD_Adapter(MainActivity21.this,list);
                                    mLv.setAdapter(adapter);
                                }else {
                                    adapter.notifyDataSetChanged();
                                }



                            }
                        });

                    }




                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void sort(){
        int i= (int) mSp.getSelectedItemId();
        switch (i){
            case 0:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o1.getId()-o2.getId();
                    }
                });
                break;
            case 1:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o2.getId()-o1.getId();
                    }
                });
                break;
            case 2:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o1.getRed()-o2.getRed();
                    }
                });
                break;
            case 3:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o2.getRed()-o1.getRed();
                    }
                });
                break;
            case 4:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o1.getYellow()-o2.getYellow();
                    }
                });
                break;
            case 5:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o2.getYellow()-o1.getYellow();
                    }
                });
                break;
            case 6:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o1.getGreen()-o2.getGreen();
                    }
                });
                break;
            case 7:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o2.getGreen()-o1.getGreen();
                    }
                });
                break;
        }

    }
}