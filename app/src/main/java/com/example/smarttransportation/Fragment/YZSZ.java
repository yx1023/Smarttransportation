package com.example.smarttransportation.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class YZSZ extends Fragment {

    TextView tv;
    EditText et;
    Button bt;

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if(msg.what==0){

                tv.setText("当前1-4号小车账户余额告警阈值为 "+threshold+" 元");
            } else if (msg.what==1) {
                if(result.equals("S")){
                    Toast.makeText(getContext(), "设置成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "设置失败", Toast.LENGTH_SHORT).show();
                }
            }


            return false;
        }
    });
    private int threshold;
    private String result;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.yzsz,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv=view.findViewById(R.id.tv1);
        et=view.findViewById(R.id.et);
        bt=view.findViewById(R.id.bt);
        getYZ();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et.getText().toString().equals("")){
                    Toast.makeText(getContext(), "请输入阈值", Toast.LENGTH_SHORT).show();
                }else {
                    setYZ();
                    getYZ();
                    et.setText("");
                }

            }
        });
    }

    public void getYZ(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_car_threshold", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    JSONArray jsonArray=jsonObject1.getJSONArray("ROWS_DETAIL");
                    JSONObject jsonObject2=new JSONObject(jsonArray.get(0).toString());
                    threshold = jsonObject2.getInt("threshold");
                    
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(0);
                        }
                    }).start();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setYZ(){
        int t= Integer.parseInt(et.getText().toString());
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("threshold",t);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("set_car_threshold", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);


                    result = jsonObject1.getString("RESULT");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(1);
                        }
                    }).start();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
