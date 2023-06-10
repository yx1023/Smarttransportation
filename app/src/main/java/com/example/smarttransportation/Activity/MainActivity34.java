package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;

public class MainActivity34 extends AppCompatActivity implements View.OnClickListener {

    private ImageView mMune17;
    private ImageView mCZ;
    private ImageView mYE;
    private ImageView mJL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main34);
        initView();
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mCZ = (ImageView) findViewById(R.id.CZ);
        mYE = (ImageView) findViewById(R.id.YE);
        mJL = (ImageView) findViewById(R.id.JL);
        mCZ.setOnClickListener(this);
        mYE.setOnClickListener(this);
        mJL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.CZ:
                intent.setClass(this,ETC_CZ.class);
                startActivity(intent);
                break;
            case R.id.YE:
                intent.setClass(this,ETC_YE.class);
                startActivity(intent);
                break;
            case R.id.JL:
                intent.setClass(this,ETC_JL.class);
                startActivity(intent);
                break;

        }
    }
}