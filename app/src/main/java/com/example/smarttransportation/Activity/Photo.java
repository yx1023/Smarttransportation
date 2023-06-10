package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.ImageListener;

public class Photo extends AppCompatActivity {

    private ImageView mFanhui;
    private ImageView mPhoto;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        initView();
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if (id.equals("1")) {
            mPhoto.setImageResource(R.drawable.weizhang1);
        } else if (id.equals("2")) {
            mPhoto.setImageResource(R.drawable.weizhang4);
        } else if (id.equals("3")) {
            mPhoto.setImageResource(R.drawable.weizhang5);
        } else if (id.equals("4")) {
            mPhoto.setImageResource(R.drawable.weizhang10);
        } else if (id.equals("地铁")) {
            mPhoto.setImageResource(R.drawable.ditiguihua);
            mText.setText("地铁规划");
        }
        mPhoto.setOnTouchListener(new ImageListener(mPhoto));
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mPhoto = (ImageView) findViewById(R.id.photo);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mText = (TextView) findViewById(R.id.text);
    }
}