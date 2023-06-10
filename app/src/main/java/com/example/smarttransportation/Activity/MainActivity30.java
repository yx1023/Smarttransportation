package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;

public class MainActivity30 extends AppCompatActivity implements View.OnClickListener {

    private ImageView mMune17;
    private ImageView mIv1;
    private ImageView mIv2;
    private ImageView mIv3;
    private ImageView mIv4;
    private ImageView mIv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main30);
        initView();
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mIv1 = (ImageView) findViewById(R.id.iv1);
        mIv2 = (ImageView) findViewById(R.id.iv2);
        mIv3 = (ImageView) findViewById(R.id.iv3);
        mIv4 = (ImageView) findViewById(R.id.iv4);
        mIv5 = (ImageView) findViewById(R.id.iv5);
        mIv1.setOnClickListener(this);
        mIv2.setOnClickListener(this);
        mIv3.setOnClickListener(this);
        mIv4.setOnClickListener(this);
        mIv5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,Vidio.class);
        switch (v.getId()){
            case R.id.iv1:
                intent.putExtra("id","1");
                startActivity(intent);
                break;
            case R.id.iv2:
                intent.putExtra("id","2");
                startActivity(intent);
                break;
            case R.id.iv3:
                intent.putExtra("id","3");
                startActivity(intent);
                break;
            case R.id.iv4:
                intent.putExtra("id","4");
                startActivity(intent);
                break;
            case R.id.iv5:
                intent.putExtra("id","5");
                startActivity(intent);
                break;
        }
    }
}