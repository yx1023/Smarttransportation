package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.RGY_Adapter;
import com.example.smarttransportation.Been.HLD;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity11 extends AppCompatActivity {

    private ImageView mCaidan;
    private Spinner mSp;
    private Button mCx;
    private Button mPl;
    private ListView mLv;
    List<HLD>list=new ArrayList<>();

    RGY_Adapter adapter;

    List<Integer>numbers=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);
        initView();
        ArrayAdapter<String>adapter=new ArrayAdapter<>(this,R.layout.spinne,new String[]{"路口升序","路口降序","红灯升序","红灯降序","黄灯升序","黄灯降序","绿灯升序","绿灯降序"});
        mSp.setAdapter(adapter);
        goTo();
    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mSp = (Spinner) findViewById(R.id.sp);
        mCx = (Button) findViewById(R.id.cx);
        mPl = (Button) findViewById(R.id.pl);
        mLv = (ListView) findViewById(R.id.lv);
        mCx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                for (int i = 1; i < 6; i++) {
                    getData(i);
                }
            }
        });
        mPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numbers.size()<2){
                    Toast.makeText(MainActivity11.this, "至少选中两个", Toast.LENGTH_SHORT).show();
                }else {

                    setDialogs();
                }
            }
        });
    }
    public void goTo(){
        list.clear();
        for (int i = 1; i < 6; i++) {
            getData(i);
        }
    }

    public void getData(int number){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("number",number);
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
                    list.add(new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").optJSONObject(0).toString(),HLD.class));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sort();
                            adapter=new RGY_Adapter(MainActivity11.this,list);
                            mLv.setAdapter(adapter);
                            adapter.setMyclock(new RGY_Adapter.Myclock() {
                                @Override
                                public void c(int id, int number) {
                                    switch (id){
                                        case 1:
                                            for (int i = 0; i < numbers.size(); i++) {
                                                if(numbers.get(i)==number){
                                                    numbers.remove(i);
                                                    return;
                                                }
                                            }
                                            numbers.add(number);
                                            System.out.println(numbers);
                                            break;
                                        case 2:
                                            setDialog(number);
                                            break;
                                    }
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
    public void sort() {
        switch ((int) mSp.getSelectedItemId()) {
            case 0:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o1.getNumber() - o2.getNumber();
                    }
                });
                break;
            case 1:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o2.getNumber() - o1.getNumber();
                    }
                });
                break;
            case 2:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o1.getRed() - o2.getRed();
                    }
                });
                break;
            case 3:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o2.getRed() - o1.getRed();
                    }
                });

                break;
            case 4:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o1.getGreen() - o2.getGreen();
                    }
                });
                break;
            case 5:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o2.getGreen() - o1.getGreen();
                    }
                });

                break;
            case 6:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o1.getYellow() - o2.getYellow();
                    }
                });
                break;
            case 7:
                list.sort(new Comparator<HLD>() {
                    @Override
                    public int compare(HLD o1, HLD o2) {
                        return o2.getYellow() - o1.getYellow();
                    }
                });
                break;
        }
    }
    public void setDialog(int number){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity11.this);
        View view= getLayoutInflater().inflate(R.layout.dialog2,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();
        EditText et_r=view.findViewById(R.id.et_red);
        EditText et_y=view.findViewById(R.id.et_y);
        EditText et_g=view.findViewById(R.id.et_g);
        Button qd=view.findViewById(R.id.qd);
        Button qx=view.findViewById(R.id.qx);
        qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_r.getText().toString().equals("") && !et_y.getText().toString().equals("") && !et_g.getText().toString().equals("") ){
                    setRLD(number,Integer.parseInt(et_r.getText().toString()),Integer.parseInt(et_y.getText().toString()),Integer.parseInt(et_g.getText().toString()));
                    dialog.dismiss();
                    goTo();

                }else {
                    Toast.makeText(MainActivity11.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



    }
    public void setDialogs(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity11.this);
        View view= getLayoutInflater().inflate(R.layout.dialog2,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();
        EditText et_r=view.findViewById(R.id.et_red);
        EditText et_y=view.findViewById(R.id.et_y);
        EditText et_g=view.findViewById(R.id.et_g);
        Button qd=view.findViewById(R.id.qd);
        Button qx=view.findViewById(R.id.qx);
        qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_r.getText().toString().equals("") && !et_y.getText().toString().equals("") && !et_g.getText().toString().equals("") ){
                    for (int i = 0; i < numbers.size(); i++) {

                        setRLD(numbers.get(i),Integer.parseInt(et_r.getText().toString()),Integer.parseInt(et_y.getText().toString()),Integer.parseInt(et_g.getText().toString()));
                    }
                    dialog.dismiss();
                    numbers.clear();
                    goTo();

                }else {
                    Toast.makeText(MainActivity11.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    public void setRLD(int number,int red,int yellow,int green){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("number",number);
            jsonObject.put("red",red);
            jsonObject.put("yellow",yellow);
            jsonObject.put("green",green);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("set_traffic_light", jsonObject.toString(), "POST", new Callback() {
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
                                Toast.makeText(MainActivity11.this, "设置成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity11.this, "设置失败", Toast.LENGTH_SHORT).show();
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