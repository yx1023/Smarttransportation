package com.example.smarttransportation.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.Road_Adapter;
import com.example.smarttransportation.Been.Road;
import com.example.smarttransportation.Been.news;
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

public class MainActivity33 extends AppCompatActivity {

    private ImageView mMune17;
    private ViewFlipper mVF;
    private ExpandableListView mLv;
    List<news>list=new ArrayList<>();
    List<Road>roadList=new ArrayList<>();
    Road_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main33);
        initView();
        getNews();
        getRoad();
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mVF = (ViewFlipper) findViewById(R.id.VF);
        mLv = (ExpandableListView) findViewById(R.id.lv);
    }
    public void getNews(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H() .sendResutil("get_news", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<news>>(){}.getType());

                    setFlipper();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void setFlipper() {
        for (int i = 0; i < list.size(); i++) {
            final news c = list.get(i);
            TextView textView = new TextView(this);
            textView.setText(c.getTitle());
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(30);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMsg(c.getTitle(), c.getMessage());
                }
            });
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mVF.addView(textView);
                }
            });

        }
        mVF.startFlipping();
    }
    private void showMsg(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("确定", null);
        builder.create().show();
    }

    public void getRoad(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H() .sendResutil("get_roads", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    roadList=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<Road>>(){}.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new Road_Adapter(roadList);
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