package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.ImageListener;

public class JKZP_2 extends AppCompatActivity {

    private ImageView mCaidan;
    private ImageView mIv;
    private Button mFh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jkzp2);
        initView();
        Intent intent=getIntent();
        int id=intent.getIntExtra("id",0);
        if(id==1){
            mIv.setImageResource(R.drawable.weizhang1);
        } else if (id == 2) {
            mIv.setImageResource(R.drawable.weizhang2);
        }
        else if (id == 3) {
            mIv.setImageResource(R.drawable.weizhang6);
        }else if (id == 4) {
            mIv.setImageResource(R.drawable.weizhang7);
        }
        mIv.setOnTouchListener(new ImageListener(mIv));
    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mIv = (ImageView) findViewById(R.id.iv);
        mFh = (Button) findViewById(R.id.fh);
        mFh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}