package com.example.smarttransportation.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Been.ZD;
import com.example.smarttransportation.Utility.DBmarager;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Adapter.ZD_Adapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private ImageView mCaidan;
    private Spinner mSp;
    private Button mCx;
    private ListView mLv;

    List<ZD> list = new ArrayList<>();
    private TextView mTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinne, new String[]{"时间升序", "时间降序"});
        mSp.setAdapter(adapter1);
        getSQL("time asc");

    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mSp = (Spinner) findViewById(R.id.sp);
        mCx = (Button) findViewById(R.id.cx);
        mTv = (TextView) findViewById(R.id.tv);
        mLv = (ListView) findViewById(R.id.lv);
        mCaidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu=new PopupMenu(MainActivity3.this,mCaidan);
                menu.inflate(R.menu.menu);
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent=new Intent();
                        switch (item.getItemId()){
                            case R.id.WDZH:
                                intent.setClass(MainActivity3.this, MainActivity1.class);
                                startActivity(intent);
                                break;
                        }


                        return false;
                    }
                });
            }
        });
        mCx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSp.getSelectedItemId() == 0) {
                    getSQL("time asc");
                } else {
                    getSQL("time desc");
                }
            }
        });

    }

    public void getSQL(String order) {

        DBmarager dBmarager = new DBmarager(this);
        boolean b = dBmarager.isExist("tabless");
        if (b == false) {
            mTv.setVisibility(View.VISIBLE);
        } else {
            Cursor cursor = dBmarager.queryDB("tabless", null, null, null, null, null, order, null);
            list = sendee(cursor);
            ZD_Adapter adapter = new ZD_Adapter(this, list);
            mLv.setAdapter(adapter);
        }
    }
    @SuppressLint("Range")
    public List<ZD>sendee(Cursor c){
        List<ZD>qList=new ArrayList<>();
        while (c.moveToNext()){
            ZD q=new ZD();
            q.setId(c.getInt(c.getColumnIndex("id")));
            q.setName(c.getString(c.getColumnIndex("name")));
            q.setMoney(c.getInt(c.getColumnIndex("money")));
            q.setTime(c.getString(c.getColumnIndex("time")));
            q.setNumber(c.getInt(c.getColumnIndex("number")));
            qList.add(q);
        }
        return qList;

    }
}