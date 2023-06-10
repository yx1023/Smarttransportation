package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.ELV_Adapter;
import com.example.smarttransportation.Adapter.GJZKTJ_Adapter;
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

public class MainActivity10 extends AppCompatActivity {

    private ImageView mCaidan;
    private TextView mTv1;
    private Button mB1;
    private ExpandableListView mElv;
    Map<String, List<B>>map;
    List<B>list1=new ArrayList<>();
    List<B>list2=new ArrayList<>();
    List<B>list=new ArrayList<>();

    GJZKTJ_Adapter adapter2;
    List<B>gjcxes=new ArrayList<>();

    ELV_Adapter adapter;
    private int allPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
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
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mB1 = (Button) findViewById(R.id.b1);
        mElv = (ExpandableListView) findViewById(R.id.elv);
        mB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity10.this);
                v=getLayoutInflater().inflate(R.layout.item4,null);
                builder.setView(v);
                AlertDialog dialog=builder.create();
                ListView listView=v.findViewById(R.id.lv);
                TextView textView=v.findViewById(R.id.tv);
                Button button=v.findViewById(R.id.bottom);
                dialog.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Timer timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {


                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put("UserName", "user1");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        new H()
                                .sendResutil("get_bus_stop_distance", jsonObject.toString(), "POST", new Callback() {
                                    @Override
                                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                    }

                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                        String s=response.body().string();
                                        try {
                                            gjcxes.clear();
                                            JSONObject jsonObject1=new JSONObject(s);
                                            gjcxes=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString()
                                                    , new TypeToken<List<B>>() {
                                                    }.getType());

                                            adapter2=new GJZKTJ_Adapter(MainActivity10.this,gjcxes);
                                            runOnUiThread(()->{
                                                listView.setAdapter(adapter2);

                                                allPeople = 0;
                                                for (int i = 0; i < gjcxes.size(); i++) {
                                                    allPeople += gjcxes.get(i).getPerson();
                                                    textView.setText(allPeople +"");
                                                }
                                            });
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                });


                    }
                },0,3000);


            }
        });
    }
    public void getData(){
        if (map == null) {
            map = new HashMap<>();

        } else {
            map.clear();

        }


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
                    list=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),new TypeToken<List<B>>(){}.getType());

                    list1=new Gson().fromJson(jsonObject1.optJSONArray("中医院站").toString(),new TypeToken<List<B>>(){}.getType());
                    list1.sort(new Comparator<B>() {
                        @Override
                        public int compare(B o1, B o2) {
                            return o1.getDistance()-o2.getDistance();
                        }
                    });
                    map.put("中医院站",list1);

                    list2=new Gson().fromJson(jsonObject1.optJSONArray("联想大厦站").toString(),new TypeToken<List<B>>(){}.getType());
                    list2.sort(new Comparator<B>() {
                        @Override
                        public int compare(B o1, B o2) {
                            return o1.getDistance()-o2.getDistance();
                        }
                    });
                    map.put("联想大厦站",list2);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int person=0;
                            for (int i = 0; i < list.size(); i++) {
                                person+=list.get(i).getPerson();
                            }
                            mTv1.setText(person+"");
                            if(adapter==null){
                                adapter=new ELV_Adapter(map);
                                mElv.setAdapter(adapter);
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