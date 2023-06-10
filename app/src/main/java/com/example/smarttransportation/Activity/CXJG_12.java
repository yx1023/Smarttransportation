package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.Lv_Adapter_left;
import com.example.smarttransportation.Adapter.Lv_Adapter_right;
import com.example.smarttransportation.Been.Car_Infoid;
import com.example.smarttransportation.Been.Left;
import com.example.smarttransportation.Been.Right;
import com.example.smarttransportation.Been.WZ;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CXJG_12 extends AppCompatActivity {

    private ImageView mCaidan;
    private ImageView mJia;
    private ListView mLvLeft;
    private ListView mLvRight;
    Lv_Adapter_left adapter_left;
    Lv_Adapter_right adapter_right;
    List<Integer>list_id;
    List<Car_Infoid>list_infoid;
    List<WZ>list;
    List<Left>left_List=MainActivity12.getLeft();

    List<Right>right_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cxjg12);
        initView();
        setLeft();
    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mJia = (ImageView) findViewById(R.id.jia);
        mLvLeft = (ListView) findViewById(R.id.lv_left);
        mLvRight = (ListView) findViewById(R.id.lv_right);
        mJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getId(left_List.get(position).getPlate());
            }
        });
        mLvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(CXJG_12.this,JKZP_1.class);
                startActivity(intent);
            }
        });
    }
    public void setLeft(){
        adapter_left=new Lv_Adapter_left(this,left_List);
        mLvLeft.setAdapter(adapter_left);
        adapter_left.setMyclock(new Lv_Adapter_left.Myclock() {
            @Override
            public void c(int i) {
                left_List.remove(i);
                adapter_left.notifyDataSetChanged();
            }
        });
    }


    public void getId(String plate){

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("plate",plate);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_peccancy_plate", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list_id=new ArrayList<>();
                    list_id=new Gson().fromJson(jsonObject1.getJSONArray("id").toString(),new TypeToken<List<Integer>>(){}.getType());
                    System.out.println(list_id);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {




                                getInFoid();

                        }
                    });


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void getInFoid(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_all_car_peccancy", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list_infoid=new ArrayList<>();
                   List<Car_Infoid> newlist_infoid=new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<Car_Infoid>>(){}.getType());
                    System.out.println(newlist_infoid.size()+"*********");

                    for (int i = 0; i < list_id.size(); i++) {
                        for (int j = 0; j < newlist_infoid.size(); j++) {
                            if(list_id.get(i)==newlist_infoid.get(j).getId()){
                                list_infoid.add(newlist_infoid.get(j));

                            }
                        }
                    }
                    System.out.println(list_infoid.size()+"//////////////////");

                    getData();


                } catch (JSONException e) {
                    throw new RuntimeException(e);
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
        new H().sendResutil("get_pessancy_infos", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    list=new ArrayList<>();
                    JSONObject jsonObject1=new JSONObject(s);
                   List<WZ> newlist=new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<WZ>>(){}.getType());

                    for (int i = 0; i < list_infoid.size(); i++) {
                        for (int j = 0; j < newlist.size(); j++) {
                            if(list_infoid.get(i).getInfoid().equals(newlist.get(j).getInfoid())){
                                list.add(newlist.get(j));
                            }
                        }
                    }
                    right_List=new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        Right right=new Right();
                        right.setData(list.get(i).getMessage());
                        right.setFK(list.get(i).getFine());
                        right.setKF(list.get(i).getDeduct());
                        right.setTime(list_infoid.get(i).getTime());
                        right.setRoad(list.get(i).getRoad());
                        right_List.add(right);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                                adapter_right=new Lv_Adapter_right(CXJG_12.this,right_List);
                                mLvRight.setAdapter(adapter_right);


                        }
                    });






                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}