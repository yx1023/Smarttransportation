package com.example.smarttransportation.Activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.DD_Adapter;
import com.example.smarttransportation.Been.DD;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.DBmarager;
import com.example.smarttransportation.Utility.H;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WDDD extends AppCompatActivity {

    private ImageView mFanhui;
    private ExpandableListView mELV;
    private TextView mT1;
    private TextView mT2;
    List<DD> list1 = new ArrayList<>();
    List<DD> list2 = new ArrayList<>();
    DD_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wddd);
        initView();
        DBmarager dBmarager = new DBmarager(this);
        boolean b=dBmarager.isExist("dingdan");
        if (b){
            Cursor cursor=dBmarager.queryDB("dingdan",null,null,null,null,null,null,null);
            list1=getSQL(cursor);
        }
        setAdapter(list1);


    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mELV = (ExpandableListView) findViewById(R.id.ELV);
        mT1 = (TextView) findViewById(R.id.t1);
        mT2 = (TextView) findViewById(R.id.t2);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mT1.setBackgroundResource(R.drawable.xiahuaxian_hong);
               mT2.setBackground(null);
                setAdapter(list1);
            }
        });
        mT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mT2.setBackgroundResource(R.drawable.xiahuaxian_hong);
                mT1.setBackground(null);
                list2.clear();
                getData();
            }
        });
    }

    public void setAdapter(List<DD>list){
        adapter=new DD_Adapter(list);
        mELV.setAdapter(adapter);
    }
    @SuppressLint ("Range")
    public List<DD> getSQL(Cursor cursor){
        List<DD>list=new ArrayList<>();
        while (cursor.moveToNext()){
            DD dd   =new DD();
            List<String>list3= Arrays.asList(cursor.getString(cursor.getColumnIndex("time")).split(","));
            dd.setData(list3);
            dd.setLine(cursor.getString(cursor.getColumnIndex("xianlu")));
            dd.setNum("O"+cursor.getString(cursor.getColumnIndex("id")));
            list.add(dd);
        }
        return list;
    }

    public void getData(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_bus_order", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list2=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),new TypeToken<List<DD>>(){}.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new DD_Adapter(list2);
                            mELV.setAdapter(adapter);
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

}