package com.example.smarttransportation.Activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.M_Adapter;
import com.example.smarttransportation.Been.M;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.DBmarager;

import java.util.ArrayList;
import java.util.List;

public class ETC_JL extends AppCompatActivity {

    private ImageView mFanhui;
    private TextView mText;
    private ListView mLv;
    List<M> list = new ArrayList<>();
    M_Adapter adapter;
    private TextView mTtt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc_jl);
        initView();
        getSQL();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mText = (TextView) findViewById(R.id.text);
        mLv = (ListView) findViewById(R.id.lv);
        mTtt = (TextView) findViewById(R.id.ttt);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void getSQL() {
        DBmarager dBmarager = new DBmarager(this);
        boolean b = dBmarager.isExist("etc");
        if (b) {
            mTtt.setVisibility(View.GONE);
            Cursor cursor = dBmarager.queryDB("etc", null, null, null, null, null, null, null);
            list = shift(cursor);
            adapter = new M_Adapter(this, list);
            mLv.setAdapter(adapter);
        }
    }

    @SuppressLint("Range")
    public List<M> shift(Cursor cursor) {
        List<M> list1 = new ArrayList<>();
        int zong=0;
        while (cursor.moveToNext()) {
            M m = new M();
            m.setNumber(cursor.getInt(cursor.getColumnIndex("number")));
            m.setMoney(cursor.getInt(cursor.getColumnIndex("money")));
            m.setTime(cursor.getString(cursor.getColumnIndex("time")));
            m.setPlate(cursor.getString(cursor.getColumnIndex("plate")));
            zong+=m.getMoney();
            list1.add(m);
        }
        mText.setText("充值合计："+zong+"元");
        return list1;
    }
}