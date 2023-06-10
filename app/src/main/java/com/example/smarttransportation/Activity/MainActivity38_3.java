package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.RL_Adapter;
import com.example.smarttransportation.Been.RL;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.LunarCalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity38_3 extends AppCompatActivity {

    private ImageView mFanhui;
    private TextView mYM;
    private GridView mGVRl;
    private TextView mRiqi;
    private Button mBT2;
    private List<RL> rls;
    private List<Integer> bglist;
    private List<CCRQ> ccrqs;
    RL_Adapter adapter;
    private String xianlu;
    private String pj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main383);
        Intent intent=getIntent();
        xianlu = intent.getStringExtra("xianlu");
        pj =intent.getStringExtra("pj");
        initView();
        initDate();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mYM = (TextView) findViewById(R.id.YM);
        mGVRl = (GridView) findViewById(R.id.GV_rl);
        mRiqi = (TextView) findViewById(R.id.riqi);
        mBT2 = (Button) findViewById(R.id.BT2);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity38_3.this,MainActivity38_4.class);
                intent.putExtra("xianlu",xianlu);
                intent.putExtra("riqi",mRiqi.getText().toString());
                intent.putExtra("pj",pj);
                startActivity(intent);
            }
        });
    }

    private void initDate() {
        rls = new ArrayList<>();
        bglist = new ArrayList<>();
        ccrqs = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 0; i < week - 1; i++) {
            rls.add(new RL("", "", 1, 0, false));
            bglist.add(1);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

// 创建农历计算器
        LunarCalendar lunarCal = new LunarCalendar();
        lunarCal.computeChineseFields(year, month + 1, day);

// 获取农历日期
        String lunarDate = lunarCal.getChineseDateString();
        for (int i = 0; i < 42; i++) {
            Calendar calendar1=Calendar.getInstance();
            calendar1.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1,calendar.get(Calendar.DAY_OF_MONTH));
            rls.add(new RL(calendar.get(Calendar.DAY_OF_MONTH) + "", lunarDate
                    , getWeek(calendar), calendar.get(Calendar.MONTH) + 1, true));
            bglist.add(getWeek(calendar));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (rls.size() != 42) {
            int j = 49 - rls.size();
            for (int i = 0; i < j; i++) {
                rls.add(new RL("", "", 1, 0, false));
                bglist.add(1);
            }
        }
        adapter = new RL_Adapter(this, rls);
        mGVRl.setAdapter(adapter);
        adapter.setClickItem(new RL_Adapter.ClickItem() {
            @Override
            public void myClick(int position, int bg) {
                RL rl = rls.get(position);
                SimpleDateFormat format=new SimpleDateFormat("yyyy");
                String tim=format.format(new Date());
                if (bg == 0 || bg == 1) {
                    rl.setBg(2);
                    rls.set(position, rl);
                    ccrqs.add(new CCRQ(position, tim +"-"+ rls.get(position).getMonth() + "-" + rls.get(position).getSolar()));
                } else {
                    rl.setBg(bglist.get(position));
                    rls.set(position, rl);
                    for (int i = 0; i < ccrqs.size(); i++) {
                        if (ccrqs.get(i).getId() == position) {
                            ccrqs.remove(i);
                        }
                    }
                }
                setDateTime();
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void setDateTime() {
        Collections.sort(ccrqs, new Comparator<CCRQ>() {
            @Override
            public int compare(CCRQ o1, CCRQ o2) {
                return o1.getId() - o2.getId();
            }
        });
        String str = "";
        for (int i = 0; i < ccrqs.size(); i++) {
            if (i == 0) {
                str = ccrqs.get(i).getMsg();
            } else {
                str += "," + ccrqs.get(i).getMsg();
            }
        }
        mRiqi.setText(str);


    }
    private int getWeek(Calendar calendar) {

        int week = calendar.get(Calendar.DAY_OF_WEEK);
        if (week == 1 || week == 7) {
            return 1;
        } else {
            return 0;
        }
    }


    class CCRQ {
        private int id;
        private String msg;

        public CCRQ(int id, String msg) {
            this.id = id;
            this.msg = msg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}