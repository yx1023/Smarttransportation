package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.Tc_Adapter;
import com.example.smarttransportation.Been.TC;
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

public class MainActivity42 extends AppCompatActivity {

    private ImageView mMune36;
    private ListView mList;
    List<TC>list=new ArrayList<>();
    Tc_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main42);
        initView();
        getData();
    }

    private void initView() {
        mMune36 = (ImageView) findViewById(R.id.mune36);
        mList = (ListView) findViewById(R.id.list);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity42.this,MainActivity42_2.class);
                intent.putExtra("name",list.get(position).getParking_name());
                startActivity(intent);
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
        new H() .sendResutil("get_park_data", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<TC>>(){}.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new Tc_Adapter(MainActivity42.this,list);
                            mList.setAdapter(adapter);
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}