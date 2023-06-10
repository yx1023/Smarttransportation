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

import com.example.smarttransportation.Adapter.T_22_Adapter;
import com.example.smarttransportation.Been.T_22;
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

public class MainActivity22 extends AppCompatActivity {

    private ImageView mMune17;
    private TextView mPL;
    private TextView mJL;
    private ListView mLv;
    T_22_Adapter adapter;
    List<T_22>list=new ArrayList<>();
    List <Integer> integerList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        initView();
        for (int i = 1; i < 6; i++) {
            getData(i);
        }
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mPL = (TextView) findViewById(R.id.PL);
        mJL = (TextView) findViewById(R.id.JL);
        mLv = (ListView) findViewById(R.id.lv);
        mPL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(integerList.size()<2){
                    Toast.makeText(MainActivity22.this, "至少选中两个", Toast.LENGTH_SHORT).show();
                }else {

                    setDialogs();
                }
            }
        });
    }

    public void getData(int i){

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

                    T_22 t_22=new T_22();
                    t_22.setNumber(i);
                    t_22.setMoney(balance);
                    list.add(t_22);

                    if(list.size()==5){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list.sort(new Comparator<T_22>() {
                                    @Override
                                    public int compare(T_22 o1, T_22 o2) {
                                        return o1.getNumber()-o2.getNumber();
                                    }
                                });
                                if(adapter==null){
                                    adapter=new T_22_Adapter(MainActivity22.this,list);
                                    mLv.setAdapter(adapter);
                                }else {
                                    adapter.notifyDataSetChanged();
                                }

                                adapter.setMyclock(new T_22_Adapter.Myclock() {
                                    @Override
                                    public void c(int i) {
                                        if(integerList.size()!=0){
                                            for (int j = 0; j < integerList.size(); j++) {
                                                if(integerList.get(j)==i){
                                                    integerList.remove(j);
                                                    return;
                                                }
                                            }
                                            integerList.add(i);
                                        }else {
                                            integerList.add(i);
                                        }
                                        System.out.println(integerList+"---------------");
                                    }

                                    @Override
                                    public void c1(int i) {
                                        setDialog(i+1);
                                    }
                                });
                            }
                        });
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setDialogs(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.dialog3,null);
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
        if(integerList.size()==2){
            t1.setText(integerList.get(0)+1+"");
            t2.setText(integerList.get(1)+1+"");
        } else if (integerList.size() == 3) {
            t1.setText(integerList.get(0)+1+"");
            t2.setText(integerList.get(1)+1+"");
            t3.setText(integerList.get(2)+1+"");
        } else if (integerList.size() == 4) {
            t1.setText(integerList.get(0)+1+"");
            t2.setText(integerList.get(1)+1+"");
            t3.setText(integerList.get(2)+1+"");
            t4.setText(integerList.get(3)+1+"");
        }
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int money= Integer.parseInt(et.getText().toString());
                if(money>999||money<1){
                    et.setText("");
                    Toast.makeText(MainActivity22.this, "充值金额范围是1-999", Toast.LENGTH_SHORT).show();
                }else {

                    for (int i = 0; i < integerList.size(); i++) {
                        getPlate((integerList.get(i)+1),money);
                    }




                    dialog.dismiss();
                }
            }
        });
    }

    public void setDialog(int number){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.dialog3,null);
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
        t1.setText(number+"");

        cz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int money= Integer.parseInt(et.getText().toString());
                if(money>999||money<1){
                    et.setText("");
                    Toast.makeText(MainActivity22.this, "充值金额范围是1-999", Toast.LENGTH_SHORT).show();
                }else {
                    getPlate(number,money);

                    dialog.dismiss();
                }
            }
        });
    }
    public void getPlate(int number,int money){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("number",number);
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
                     CZ(plate,money);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
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
                                Toast.makeText(MainActivity22.this, "充值成功", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(MainActivity22.this, "充值失败", Toast.LENGTH_SHORT).show();
                            }
                            list.clear();
                            for (int i = 1; i <6 ; i++) {
                                getData(i);
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