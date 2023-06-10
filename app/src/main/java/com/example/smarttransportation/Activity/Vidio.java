package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;

public class Vidio extends AppCompatActivity {

    private ImageView mFanhui;
    private VideoView mVidio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidio);
        initView();
        Intent intent=getIntent();
        String id=intent.getStringExtra("id");
        if(id.equals("1")){
           playVideoRaw(R.raw.car1);
        } else if (id.equals("2")) {
            playVideoRaw(R.raw.car2);
        } else if (id.equals("3")) {
            playVideoRaw(R.raw.car3);
        } else if (id.equals("4")) {
            playVideoRaw(R.raw.car4);
        }else if (id.equals("5")) {
            playVideoRaw(R.raw.car4);
        }

    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mVidio = (VideoView) findViewById(R.id.vidio);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void playVideoRaw(int u) {
//        MediaController mediaController = new MediaController(this);


        //获取raw.mp4的uri地址
        String uri = "android.resource://" + getPackageName() + "/" + u;
        mVidio.setVideoURI(Uri.parse(uri));


        //让video和mediaController建立关联
//        mVidio.setMediaController(mediaController);
//        mediaController.setMediaPlayer(mVidio);


        //让video获取焦点
        mVidio.requestFocus();
        //监听播放完成，
        mVidio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //重新开始播放
                mVidio.start();
            }
        });
        mVidio.start();
    }


}