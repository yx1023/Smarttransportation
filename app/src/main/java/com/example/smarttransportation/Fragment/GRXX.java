package com.example.smarttransportation.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class GRXX extends Fragment {

    TextView name;
    LinearLayout LL1;
    LinearLayout LL2;
    LinearLayout LL3;
    TextView plate1;
    TextView plate2;
    TextView plate3;
    TextView sex;
    TextView tel;
    TextView sfzh;
    TextView zc_time;
    ImageView touxiang;
    private String name1;
    private String sex1;
    private String idnumber;
    private String registered_time;
    private String tel1;

    private List<String> list;

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            setData();
            return false;
        }
    });


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grxx,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name=view.findViewById(R.id.name);
        sex=view.findViewById(R.id.sex);
        tel=view.findViewById(R.id.tel);
        sfzh=view.findViewById(R.id.sfzh);
        zc_time=view.findViewById(R.id.zc_time);
        touxiang=view.findViewById(R.id.touxiang);
        plate1=view.findViewById(R.id.plate1);
        plate2=view.findViewById(R.id.plate2);
        plate3=view.findViewById(R.id.plate3);
        LL1=view.findViewById(R.id.LL1);
        LL2=view.findViewById(R.id.LL2);
        LL3=view.findViewById(R.id.LL3);
        getData();
    }
    public void getData(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("Password",1234);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_root", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    name1 = jsonObject1.getString("name");
                    sex1 = jsonObject1.getString("sex");
                    idnumber = jsonObject1.getString("idnumber");
                    registered_time = jsonObject1.getString("registered_time");
                    tel1 = jsonObject1.getString("tel");
                    list = new ArrayList<>();
                    list =new Gson().fromJson(jsonObject1.getJSONArray("plate").toString(),
                            new TypeToken<List<String>>(){}.getType());

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
    public void setData(){
        if(list.size()==1){
            LL2.setVisibility(View.INVISIBLE);
            LL3.setVisibility(View.INVISIBLE);
            plate1.setText(list.get(0));
        } else if (list.size() == 2) {
            LL3.setVisibility(View.INVISIBLE);
            plate1.setText(list.get(0));
            plate2.setText(list.get(1));
        } else if (list.size() == 3) {
            plate1.setText(list.get(0));
            plate2.setText(list.get(1));
            plate3.setText(list.get(2));
        } else if (list.size() == 0) {
            LL1.setVisibility(View.INVISIBLE);
            LL2.setVisibility(View.INVISIBLE);
            LL3.setVisibility(View.INVISIBLE);
        }

        name.setText("姓名："+name1);
        tel.setText("手机号码："+tel1);
        sex.setText("性别："+sex1);
        sfzh.setText("身份证号："+idnumber);
        zc_time.setText("注册时间："+registered_time);

    }
}
