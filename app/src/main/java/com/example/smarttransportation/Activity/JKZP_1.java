package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;

public class JKZP_1 extends AppCompatActivity implements View.OnClickListener {

    private ImageView mCaidan;
    private ImageView mIv1;
    private ImageView mIv2;
    private ImageView mIv3;
    private ImageView mIv4;
    private Button mFh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jkzp1);
        initView();
    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mIv1 = (ImageView) findViewById(R.id.iv1);
        mIv2 = (ImageView) findViewById(R.id.iv2);
        mIv3 = (ImageView) findViewById(R.id.iv3);
        mIv4 = (ImageView) findViewById(R.id.iv4);
        mFh = (Button) findViewById(R.id.fh);
        mFh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIv1.setOnClickListener(this);
        mIv2.setOnClickListener(this);
        mIv3.setOnClickListener(this);
        mIv4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,JKZP_2.class);
        switch (v.getId()){
            case R.id.iv1:
                intent.putExtra("id",1);
                startActivity(intent);
                break;
            case R.id.iv2:
                intent.putExtra("id",2);
                startActivity(intent);
                break;
            case R.id.iv3:
                intent.putExtra("id",3);
                startActivity(intent);
                break;
            case R.id.iv4:
                intent.putExtra("id",4);
                startActivity(intent);
                break;
        }
    }
}