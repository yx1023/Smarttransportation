package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.ELV_28;
import com.example.smarttransportation.Been.B;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity28 extends AppCompatActivity {

    private ImageView mMune17;
    private ExpandableListView mELV;
    Map<String, List<B>>map=new HashMap<>();
    List<B>list1=new ArrayList<>();
    List<B>list2=new ArrayList<>();

    ELV_28 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main28);
        initView();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getData();
            }
        },0,3000);
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mELV = (ExpandableListView) findViewById(R.id.ELV);
    }
    public void getData(){



        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_bus_stop_distance", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list1=new Gson().fromJson(jsonObject1.optJSONArray("中医院站").toString(),new TypeToken<List<B>>(){}.getType());
                    list1.sort(new Comparator<B>() {
                        @Override
                        public int compare(B o1, B o2) {
                            return o1.getDistance()-o2.getDistance();
                        }
                    });
                    map.put("一号站台",list1);

                    list2=new Gson().fromJson(jsonObject1.optJSONArray("联想大厦站").toString(),new TypeToken<List<B>>(){}.getType());
                    list2.sort(new Comparator<B>() {
                        @Override
                        public int compare(B o1, B o2) {
                            return o1.getDistance()-o2.getDistance();
                        }
                    });
                    map.put("二号站台",list2);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(adapter==null){
                                adapter=new ELV_28(map);
                                mELV.setAdapter(adapter);
                            }else {
                                adapter.notifyDataSetChanged();
                            }

                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}