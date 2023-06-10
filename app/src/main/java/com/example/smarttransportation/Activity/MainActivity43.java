package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.Car_43;
import com.example.smarttransportation.Been.C_43;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity43 extends AppCompatActivity {

    private ImageView mMune17;
    private ListView mList;
    Car_43 adapter;
    List<C_43>list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main43);
        initView();
        for (int i = 1; i <7; i++) {
            getMoney(i);
        }
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mList = (ListView) findViewById(R.id.list);
    }
    public void getMoney(int i){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("number",i);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_balance_b", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    int balance=jsonObject1.getInt("balance");
                    C_43 c_43=new C_43();
                    c_43.setMoney(balance);
                    c_43.setNumber(i);
                    list.add(c_43);
                    list.sort(new Comparator<C_43>() {
                        @Override
                        public int compare(C_43 o1, C_43 o2) {
                            return o1.getNumber()- o2.getNumber();
                        }
                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new Car_43(MainActivity43.this,list);
                            mList.setAdapter(adapter);
                            adapter.setMyclick(new Car_43.Myclick() {
                                @Override
                                public void c(int number) {
                                    CZ(number);
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
    public void CZ(int i){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View v=getLayoutInflater().inflate(R.layout.dialog3,null);
        builder.setView(v);
        AlertDialog dialog=builder.create();
        dialog.show();
        TextView textView=v.findViewById(R.id.tv1);
        EditText editText=v.findViewById(R.id.et_money);
        Button b1=v.findViewById(R.id.b_cz);
        Button b2=v.findViewById(R.id.b_qx);
        textView.setText(i+"");
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int et= Integer.parseInt(editText.getText().toString());
                setData(i,et);
                dialog.dismiss();
            }
        });
    }
    public void setData(int i,int money){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("number",i);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_plate", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    String plate=jsonObject1.getString("plate");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cz(plate,money);
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void cz( String plate,int i){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("plate",plate);
            jsonObject.put("balance",i);
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
                    String str=jsonObject1.getString("RESULT");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list.clear();
                            for (int i = 1; i < 7; i++) {
                                getMoney(i);
                            }
                            if(str.equals("S")){
                                Toast.makeText(MainActivity43.this, "充值成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity43.this, "充值失败", Toast.LENGTH_SHORT).show();
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