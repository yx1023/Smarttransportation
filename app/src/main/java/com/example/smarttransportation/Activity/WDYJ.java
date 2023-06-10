package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Adapter.YJ_Adapter;
import com.example.smarttransportation.Been.YJFK;
import com.example.smarttransportation.R;

import java.util.List;

public class WDYJ extends AppCompatActivity {

    private ImageView mFanhui;
    private ListView mLV;
    YJ_Adapter adapter;
    List<YJFK>list=MainActivity31.getList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdyj);
        initView();
        if(list.size()!=0){
            adapter=new YJ_Adapter(this,list);
            mLV.setAdapter(adapter);
        }

    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mLV = (ListView) findViewById(R.id.LV);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}