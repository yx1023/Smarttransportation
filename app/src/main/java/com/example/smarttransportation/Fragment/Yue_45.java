package com.example.smarttransportation.Fragment;

import android.content.ContentValues;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.smarttransportation.Adapter.WDYE_Adapter;
import com.example.smarttransportation.Been.Car;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.DBmarager;
import com.example.smarttransportation.Utility.H;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Yue_45 extends Fragment {
    private GridView gv;
    private List<Car> list=new ArrayList<>();
    WDYE_Adapter adapter;
    int money;

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    adapter=new WDYE_Adapter(getContext(),list);
                    gv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.setMyclick(new WDYE_Adapter.Myclick() {
                        @Override
                        public void onclick(int number) {
                            CZ(number);
                        }
                    });
                    break;
                case 2:
                    cz(plate,money);
                    break;
                case 3:
                    list.clear();
                    for (int i = 1; i < 5; i++) {
                        getData(i);
                    }
                    if(str.equals("S")){
                        Toast.makeText(getContext(), "充值成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "充值失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }



            return false;
        }
    });
    private String plate;
    private String str;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.yue_45,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gv=view.findViewById(R.id.gird_view);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                list.clear();
                for (int i = 1; i < 5; i++) {
                    getData(i);
                }

            }
        },0,5000);

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
                    Car car=new Car();
                    car.setNumber(i);
                    car.setBalance(jsonObject1.getString("balance"));
                    list.add(car);
                    sort();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message=Message.obtain();
                            message.what=1;
                            handler.sendMessage(message);

                        }
                    }).start();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
    public void sort(){
        list.sort(new Comparator<Car>() {
            @Override
            public int compare(Car o1, Car o2) {
                return o1.getNumber()- o2.getNumber();
            }
        });
    }

    public void CZ( int s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = getLayoutInflater().inflate(R.layout.dige_2, null);
        builder.setView(v);
        AlertDialog dialog = builder.create();
        dialog.show();

        Button b1 = v.findViewById(R.id.queding);
        Button b2 = v.findViewById(R.id.quxiao);
        EditText editText=v.findViewById(R.id.et);
        ImageView iv=v.findViewById(R.id.cha);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!editText.getText().toString().equals("")) {
                    int temp = Integer.parseInt(s.toString());
                    if (temp > 999)
                        s.replace(0, s.length(), "999");
                    else if (temp < 1)
                        s.replace(0, s.length(), "1");
                    else
                        return;
                }
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editText.getText().toString().equals("")){
                    money= Integer.parseInt(editText.getText().toString());

                    DBmarager marager=new DBmarager(getContext());
                    boolean b = marager.isExist("lll");
                    if(b==false){
                        String sql = "create table lll (" +
                                "id integer primary key autoincrement," +
                                "                            number varchar," +
                                "                            money varchar," +
                                "                           time varchar);";
                        marager.createtable(sql);
                    }
                    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String time=format.format(new Date());
                    ContentValues CV=new ContentValues();
                    CV.put("number",s+"");
                    CV.put("money",money);
                    CV.put("time",time);
                    marager.insertDB("lll",CV);

                    setData(s);



                    dialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "请输入充值金额", Toast.LENGTH_SHORT).show();
                }






            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    public void setData(int i){
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
                    plate = jsonObject1.getString("plate");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message=Message.obtain();
                            message.what=2;
                            handler.sendMessage(message);
                        }
                    }).start();

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
                    str = jsonObject1.getString("RESULT");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message=Message.obtain();
                            message.what=3;
                            handler.sendMessage(message);
                        }
                    }).start();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
}
