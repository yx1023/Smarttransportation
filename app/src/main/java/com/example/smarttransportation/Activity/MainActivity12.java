package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Been.Car_Infoid;
import com.example.smarttransportation.Been.Left;
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

public class MainActivity12 extends AppCompatActivity {

    private ImageView mCaidan;
    private EditText mEt;
    private Button mCx;
    List<Integer>list_id;
    List<Car_Infoid>list_infoid;
    List<WZ>list;

    static   List<Left>list_left=new ArrayList<>();

    public static List<Left> getLeft() {
        return list_left;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);
        initView();

    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mEt = (EditText) findViewById(R.id.et);
        mCx = (Button) findViewById(R.id.cx);
        mCx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getId(mEt.getText().toString());
            }
        });
    }
    public void getId(String plate){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("plate","鲁"+plate);
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



                            if(list_id.size()!=0){
                                Intent intent=new Intent(MainActivity12.this,CXJG_12.class);
                                startActivity(intent);
                                getInFoid();
                            }else {
                                Toast.makeText(MainActivity12.this, "没有查询到鲁"+plate+"车的违章数据！", Toast.LENGTH_SHORT).show();
                            }
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
                  List<Car_Infoid>  newlist_infoid=new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<Car_Infoid>>(){}.getType());

                    for (int i = 0; i < list_id.size(); i++) {
                        for (int j = 0; j < newlist_infoid.size(); j++) {
                            if(list_id.get(i)==newlist_infoid.get(j).getId()){
                                list_infoid.add(newlist_infoid.get(j));
                            }
                        }
                    }


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
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new ArrayList<>();
                   List<WZ> newlist=new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<WZ>>(){}.getType());

                    for (int i = 0; i < list_infoid.size(); i++) {
                        for (int j = 0; j < newlist.size(); j++) {
                            if(list_infoid.get(i).getInfoid().equals(newlist.get(j).getInfoid())){
                                list.add(newlist.get(j));
                            }
                        }
                    }
                    System.out.println(list.size()+"````````````````");


                        Left left=new Left();
                    left.setPlate("鲁"+mEt.getText().toString());
                    left.setSize(list.size());
                    int KF=0;
                    int FK=0;
                    for (int i = 0; i < list.size(); i++) {
                       KF+= list.get(i).getDeduct();
                       FK+=list.get(i).getFine();
                    }
                    left.setKF(KF);
                    left.setMoney(FK);
                    list_left.add(left);



                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}