package com.example.smarttransportation.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarttransportation.Been.SJFX;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PieChart_2 extends Fragment {

    private PieChart pc;
    private TextView tv1;
    private TextView tv2;
    List<SJFX> list=new ArrayList<>();
    private int a=0;
    private int b=0;
    private Map<String,Integer> map;
    private List<Map.Entry<String,Integer>>mapList;

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            loadData();
            return false;
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.piechart_15,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pc=view.findViewById(R.id.pc);
        tv1=view.findViewById(R.id.tv1);
        tv2=view.findViewById(R.id.tv2);
        tv1.setText("无重复违章");
        tv2.setText("有重复违章");
        getData();
    }
    public void getData(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","User1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new H().sendResutil("get_peccancy", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    list=new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(),
                            new TypeToken<List<SJFX>>(){}.getType());
                    for (int i = 0; i < list.size(); i++) {
                        for (int j = 0; j < list.size(); j++) {
                            if(list.get(j).getDatetime().equals("")){
                                list.remove(j);
                            }
                        }
                    }

                    map=new HashMap<>();
                    for (int i = 0; i < list.size(); i++) {
                        String plate=list.get(i).getCarnumber();
                        Integer count=map.get(plate);
                        map.put(plate,(count==null) ? 1 : count+1);
                    }
                    mapList=new ArrayList<>(map.entrySet());

                    for (int i = 0; i < mapList.size(); i++) {
                        if(mapList.get(i).getValue() == 1){
                            a++;
                        }else {
                            b++;
                        }
                    }

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
    public void loadData(){

        //不显示标题
        pc.getDescription().setEnabled(false);
        pc.getLegend().setEnabled(false);
        //设置偏移量
        pc.setExtraTopOffset(30);
        pc.setExtraBottomOffset(30);

        Float f1= Float.valueOf(a);
        Float f2= Float.valueOf(b);


        //数据源
        List<PieEntry>entries=new ArrayList<>();
        entries.add(new PieEntry(f1/(f1+f2)*100));
        entries.add(new PieEntry(f2/(f1+f2)*100));

        //添加到PieDataSet中
        PieDataSet pieDataSet=new PieDataSet(entries,"");
        pieDataSet.setColors(getResources().getColor(R.color.pc_hong),getResources().getColor(R.color.pc_lan));
        //自定义描述
        final String s[]={"无重复违章","有重复违章"};
        pieDataSet.setValueFormatter(new ValueFormatter() {

            private int indd = -1;
            @Override
            public String getPieLabel(float value, PieEntry pieEntry) {
                indd++;
                if(indd>=s.length){
                    indd=0;
                }
                return s[indd]+value+"%";
            }
        });
        //设置饼块之间的间隔
        pieDataSet.setSliceSpace(3f);
        //设置点击某一饼快多出来的距离
        pieDataSet.setSelectionShift(15f);
        //设置连接线显示在外面
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //设置连接线距离饼图的距离，为百分数
        pieDataSet.setValueLinePart1OffsetPercentage(100f);
        //定义连接线的长度
        pieDataSet.setValueLinePart1Length(1.5f);
        pieDataSet.setValueTextSize(20f);

        //不绘制空洞
        pc.setDrawHoleEnabled(false);
        //不可旋转
        pc.setRotationEnabled(false);
        PieData pieData = new PieData(pieDataSet);
        //设置数据
        pc.setData(pieData);

    }


}
