package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.Been.LX;
import com.example.smarttransportation.R;

import java.util.List;

public class XXXX extends AppCompatActivity {

    private ImageView mFanhui;
    private ImageView mIv;
    private TextView mText;
    private RatingBar mRatingBar;
    private TextView mTel;
    List<LX> list=MainActivity35.getList();
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xxxx);
        initView();

        Intent intent=getIntent();
        id = intent.getIntExtra("id",0);
        if(list.get(id).getName().equals("故宫")){
            mIv.setImageResource(R.drawable.gugong);
        }else {
            mIv.setImageResource(R.drawable.tiananmen);
        }
        mText.setText(list.get(id).getPresentation());
        mTel.setText(list.get(id).getTel());
        mRatingBar.setRating(Float.parseFloat(list.get(id).getGrade()));
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mIv = (ImageView) findViewById(R.id.iv);
        mText = (TextView) findViewById(R.id.text);
        mRatingBar = (RatingBar) findViewById(R.id.rating_bar);
        mTel = (TextView) findViewById(R.id.tel);
        mTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + list.get(id).getTel()));
                startActivity(intent);
            }
        });
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}