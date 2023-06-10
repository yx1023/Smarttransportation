package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.News_Adapter;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity39 extends AppCompatActivity {

    private ImageView mMune17;
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private ListView mLv;
    List<String>list=new ArrayList<>();
    News_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main39);
        initView();
        getNews(1);
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mTv3 = (TextView) findViewById(R.id.tv3);
        mLv = (ListView) findViewById(R.id.lv);
        mTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv1.setBackgroundResource(R.drawable.xiahuaxian_huang);
                mTv2.setBackground(null);
                mTv3.setBackground(null);
                getNews(1);
            }
        });
        mTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv2.setBackgroundResource(R.drawable.xiahuaxian_huang);
                mTv1.setBackground(null);
                mTv3.setBackground(null);
                getNews(2);
            }
        });
        mTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv3.setBackgroundResource(R.drawable.xiahuaxian_huang);
                mTv1.setBackground(null);
                mTv2.setBackground(null);
                getNews(3);
            }
        });
    }
    public  void getNews(int type){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("type",type);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H() .sendResutil("get_all_news", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.optJSONArray("news").toString(),new TypeToken<List<String>>(){}.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new News_Adapter(MainActivity39.this,list);
                            mLv.setAdapter(adapter);
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}