package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;

public class MainActivity4 extends AppCompatActivity implements View.OnClickListener {

    private ImageView mCaidan;
    private TextView mVidio;
    private TextView mPhoto;
    private ImageView mIv1;
    private TextView mTv1;
    private ImageView mIv2;
    private TextView mTv2;
    private ImageView mIv3;
    private TextView mTv3;
    private ImageView mIv4;
    private TextView mTv4;
    int vp=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        initView();
        setTV();

    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mVidio = (TextView) findViewById(R.id.vidio);
        mPhoto = (TextView) findViewById(R.id.photo);
        mIv1 = (ImageView) findViewById(R.id.iv1);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mIv2 = (ImageView) findViewById(R.id.iv2);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mIv3 = (ImageView) findViewById(R.id.iv3);
        mTv3 = (TextView) findViewById(R.id.tv3);
        mIv4 = (ImageView) findViewById(R.id.iv4);
        mTv4 = (TextView) findViewById(R.id.tv4);
        mVidio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp=1;
                setTV();
            }
        });
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp=0;
                setTV();
            }
        });
        mIv1.setOnClickListener(this);
        mIv2.setOnClickListener(this);
        mIv3.setOnClickListener(this);
        mIv4.setOnClickListener(this);

    }
    public void setTV(){
        if(vp==1){
            mVidio.setBackgroundResource(R.drawable.bg);
            mPhoto.setBackgroundResource(R.drawable.bg2);
            mTv1.setVisibility(View.VISIBLE);
            mTv2.setVisibility(View.VISIBLE);
            mTv3.setVisibility(View.VISIBLE);
            mTv4.setVisibility(View.VISIBLE);
            mIv1.setImageResource(R.drawable.shipinbiao);
            mIv2.setImageResource(R.drawable.shipinbiao);
            mIv3.setImageResource(R.drawable.shipinbiao);
            mIv4.setImageResource(R.drawable.shipinbiao);
        }else {
            mVidio.setBackgroundResource(R.drawable.bg2);
            mPhoto.setBackgroundResource(R.drawable.bg);
            mTv1.setVisibility(View.GONE);
            mTv2.setVisibility(View.GONE);
            mTv3.setVisibility(View.GONE);
            mTv4.setVisibility(View.GONE);
            mIv1.setImageResource(R.drawable.weizhang1);
            mIv2.setImageResource(R.drawable.weizhang4);
            mIv3.setImageResource(R.drawable.weizhang5);
            mIv4.setImageResource(R.drawable.weizhang10);
        }
    }
    public void setIntent(String id){
        Intent intent=new Intent();
        if(vp==1){
            intent.setClass(this,Vidio.class);
        } else {
            intent.setClass(this,Photo.class);
        }
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv1:
                setIntent("1");
                break;
            case R.id.iv2:
                setIntent("2");
                break;
            case R.id.iv3:
                setIntent("3");
                break;
            case R.id.iv4:
                setIntent("4");
                break;
        }
    }
}