package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity40 extends AppCompatActivity {

    private Spinner mSp;
    private TextView mYue;
    private EditText mMoney;
    private Button mB1;
    private Button mB2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main40);
        initView();
        ArrayAdapter<String>adapter=new ArrayAdapter<>(this,R.layout.spinne,new String[]{"鲁A10001","鲁A10002","鲁A10003","鲁A10004","鲁A10005","鲁A10006"});
        mSp.setAdapter(adapter);
    }

    private void initView() {
        mSp = (Spinner) findViewById(R.id.sp);
        mYue = (TextView) findViewById(R.id.yue);
        mMoney = (EditText) findViewById(R.id.money);
        mB1 = (Button) findViewById(R.id.b1);
        mB2 = (Button) findViewById(R.id.b2);
        mSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mSp.getSelectedItemId()==0){
                    getMoney("鲁A10001");
                } else if (mSp.getSelectedItemId()==1) {
                    getMoney("鲁A10002");
                }else if (mSp.getSelectedItemId()==2) {
                    getMoney("鲁A10003");
                }else if (mSp.getSelectedItemId()==3) {
                    getMoney("鲁A10004");
                }else if (mSp.getSelectedItemId()==4) {
                    getMoney("鲁A10005");
                }else if (mSp.getSelectedItemId()==5) {
                    getMoney("鲁A10006");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plate= (String) mSp.getSelectedItem();
                CZ(plate, Integer.parseInt(mMoney.getText().toString()));
            }
        });

    }
    public void getMoney(String plate){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("plate",plate);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_balance_c", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String balance=jsonObject.getString("balance");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mYue.setText(balance);
                        }
                    });
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
                    JSONObject jsonObject=new JSONObject(s);
                    String RESULT=jsonObject.getString("RESULT");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(RESULT.equals("S")){
                                Toast.makeText(MainActivity40.this, "充值成功", Toast.LENGTH_SHORT).show();
                                getMoney(plate);
                            }else {
                                Toast.makeText(MainActivity40.this, "充值失败", Toast.LENGTH_SHORT).show();
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