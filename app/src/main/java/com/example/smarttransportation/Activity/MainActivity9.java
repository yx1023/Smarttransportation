package com.example.smarttransportation.Activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.ZHGL_Adapter;
import com.example.smarttransportation.Been.ZHGL;
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

public class MainActivity9 extends AppCompatActivity {

    private ImageView mCaidan;
    private TextView mPLCZ;
    private TextView mCZJL;
    private ListView mLv;
    List<ZHGL>list=new ArrayList<>();
    ZHGL_Adapter adapter;
    List <String> strings=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        initView();
        getData();
    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mPLCZ = (TextView) findViewById(R.id.PLCZ);
        mCZJL = (TextView) findViewById(R.id.CZJL);
        mLv = (ListView) findViewById(R.id.lv);
        mPLCZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(strings.size()<2){
                    Toast.makeText(MainActivity9.this, "至少选中两个", Toast.LENGTH_SHORT).show();
                }else {
                    setDialogs();
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
        new H().sendResutil("get_vehicle", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<ZHGL>>(){}.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new ZHGL_Adapter(MainActivity9.this,list);
                            mLv.setAdapter(adapter);
                            adapter.setMyclock(new ZHGL_Adapter.Myclock() {
                                @Override
                                public void c1(String plate) {
                                    if(strings.size()!=0){
                                        for (int i = 0; i < strings.size(); i++) {
                                            if(strings.get(i).equals(plate)){
                                                strings.remove(i);
                                                return;
                                            }
                                        }
                                        strings.add(plate);
                                    }else {
                                        strings.add(plate);
                                    }
                                    System.out.println(strings);


                                }

                                @Override
                                public void c2(String plate) {
                                    setDialog(plate);
                                }
                            });
                        }
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void setDialog(String plate){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.dialog,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();
        TextView t1=view.findViewById(R.id.tv1);
        EditText et=view.findViewById(R.id.et_money);
        Button cz=view.findViewById(R.id.b_cz);
        Button qx=view.findViewById(R.id.b_qx);
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        t1.setText(plate);

        cz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int money= Integer.parseInt(et.getText().toString());
                if(money>999||money<1){
                    et.setText("");
                    Toast.makeText(MainActivity9.this, "充值金额范围是1-999", Toast.LENGTH_SHORT).show();
                }else {
                    CZ(plate,money);
                    getData();
                    dialog.dismiss();
                }



            }
        });


    }


    public void setDialogs(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.dialog,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();
        TextView t1=view.findViewById(R.id.tv1);
        TextView t2=view.findViewById(R.id.tv2);
        TextView t3=view.findViewById(R.id.tv3);
        TextView t4=view.findViewById(R.id.tv4);
        EditText et=view.findViewById(R.id.et_money);
        Button cz=view.findViewById(R.id.b_cz);
        Button qx=view.findViewById(R.id.b_qx);

        if(strings.size()==2){
            t1.setText(strings.get(0));
            t2.setText(strings.get(1));
        } else if (strings.size() == 3) {
            t1.setText(strings.get(0));
            t2.setText(strings.get(1));
            t3.setText(strings.get(2));
        } else if (strings.size() == 4) {
            t1.setText(strings.get(0));
            t2.setText(strings.get(1));
            t3.setText(strings.get(2));
            t4.setText(strings.get(3));
        }
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        cz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                int money= Integer.parseInt(et.getText().toString());
                if(money>999||money<1){
                    et.setText("");
                    Toast.makeText(MainActivity9.this, "充值金额范围是1-999", Toast.LENGTH_SHORT).show();
                }else {

                    for (int i = 0; i < strings.size(); i++) {
                        CZ(strings.get(i),money);
                    }
                        strings.clear();
                    getData();
                    dialog.dismiss();
                }



            }
        });


    }

    public void CZ(String plate,int money){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("plate",plate);
            jsonObject.put("balance",money);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("set_balance", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    String RESULT=jsonObject1.getString("RESULT");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(RESULT.equals("S")){
                                Toast.makeText(MainActivity9.this, "充值成功", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(MainActivity9.this, "充值失败", Toast.LENGTH_SHORT).show();
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