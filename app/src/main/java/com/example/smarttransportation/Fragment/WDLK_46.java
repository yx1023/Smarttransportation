package com.example.smarttransportation.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarttransportation.Adapter.WDLK_Adapyer;
import com.example.smarttransportation.Been.RLD;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WDLK_46 extends Fragment {

    private ListView lv;

    List<RLD>list=new ArrayList<>();
    WDLK_Adapyer adapyer;
    
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            for (int i = 0; i < list.size(); i++) {
                RLD wdlk = list.get(i);
                int nowH = wdlk.getRedH();
                if (nowH > 0) {
                    nowH--;
                    wdlk.setRedH(nowH);
                    wdlk.setHorizontal("红");
                } else {
                    nowH = wdlk.getYellowH();
                    if (nowH > 0) {
                        nowH--;
                        wdlk.setYellowH(nowH);
                        wdlk.setHorizontal("黄");
                    } else {
                        nowH = wdlk.getGreenH();
                        if (nowH > 0) {
                            nowH--;
                            wdlk.setGreenH(nowH);
                            wdlk.setHorizontal("绿");
                        } else {
                            wdlk.setGreenH(wdlk.getGreen());
                            wdlk.setYellowH(wdlk.getYellow());
                            wdlk.setRedH(wdlk.getRed());
                            wdlk.setHorizontal("红");
                        }
                    }
                }
                int nowV = wdlk.getGreenV();
                if (nowV > 0) {
                    nowV--;
                    wdlk.setGreenV(nowV);
                    wdlk.setVertical("绿");
                } else {
                    nowV = wdlk.getRedV();
                    if (nowV > 0) {
                        nowV--;
                        wdlk.setRedV(nowV);
                        wdlk.setVertical("红");
                    } else {
                        nowV = wdlk.getYellowV();
                        if (nowV > 0) {
                            nowV--;
                            wdlk.setYellowV(nowV);
                            wdlk.setVertical("黄");
                        } else {
                            wdlk.setGreenV(wdlk.getGreen());
                            wdlk.setYellowV(wdlk.getYellow());
                            wdlk.setRedV(wdlk.getRed());
                            wdlk.setVertical("绿");
                            wdlk.setEnable(true);
                        }
                    }
                }
                list.set(i, wdlk);
            }
            if(adapyer==null){
                adapyer=new WDLK_Adapyer(getContext(),list);
                lv.setAdapter(adapyer);
                adapyer.setClickItem(new WDLK_Adapyer.ClickItem() {
                    @Override
                    public void myClickItem(int lx, int position) {
                        RLD rld=list.get(position);
                        switch (lx){
                            case 1:
                                rld.setHorizontal("绿");
                                rld.setYellowH(0);
                                rld.setRedH(0);
                                rld.setGreenH(30);
                                rld.setVertical("红");
                                rld.setRedV(30);
                                rld.setYellowV(0);
                                rld.setGreenV(0);
                                rld.setEnable(false);
                                break;
                            case 2:
                                rld.setHorizontal("红");
                                rld.setYellowH(0);
                                rld.setRedH(30);
                                rld.setGreenH(0);
                                rld.setVertical("绿");
                                rld.setRedV(0);
                                rld.setYellowV(0);
                                rld.setGreenV(30);
                                rld.setEnable(false);
                                break;
                            case 3:
                                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                                View v=getLayoutInflater().inflate(R.layout.digon_46,null);
                                builder.setView(v);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                Button b1 = v.findViewById(R.id.queding);
                                Button b2 = v.findViewById(R.id.quxiao);
                                EditText red=v.findViewById(R.id.red);
                                EditText yellow=v.findViewById(R.id.yellow);
                                EditText green=v.findViewById(R.id.green);
                                ImageView iv=v.findViewById(R.id.cha);
                                b2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
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
                                        int r= Integer.parseInt(red.getText().toString());
                                        int y= Integer.parseInt(yellow.getText().toString());
                                        int g= Integer.parseInt(green.getText().toString());

                                        JSONObject jsonObject=new JSONObject();
                                        try {
                                            jsonObject.put("UserName","user1");
                                            jsonObject.put("number",position+1);
                                            jsonObject.put("red",r);
                                            jsonObject.put("yellow",y);
                                            jsonObject.put("green",g);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                        new H().sendResutil("set_traffic_light", jsonObject.toString(), "POST", new Callback() {
                                            @Override
                                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                            }

                                            @Override
                                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                            }
                                        });

                                    }

                                });

                                break;
                        }
                    }
                });
            }else {
                adapyer.notifyDataSetChanged();
            }

            return false;
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wdlk_46,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv=view.findViewById(R.id.lv);
        for (int i = 1; i <= 5; i++) {
            getData(i);
        }
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        },0,1000);

    }


    public void getData(int i){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("number",i);
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
                    list.add(new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").optJSONObject(0).toString(),
                            RLD.class));

                    if(list.size()==5){

                        sort();
                    }


                    

                    
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        });
    }
    public void sort(){
        list.sort(new Comparator<RLD>() {
            @Override
            public int compare(RLD o1, RLD o2) {
                return o1.getNumber()-o2.getNumber();
            }
        });

    }
}
