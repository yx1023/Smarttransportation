package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity37_2 extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mLL;
    private ImageView mFanhui;
    private TextView mInfo;
    private ImageView mIv1;
    private ImageView mIv2;
    private String id;
    private String time;
    private String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main372);
        initView();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        time = intent.getStringExtra("time");
        money = intent.getStringExtra("money");

        int t= Integer.parseInt(time)*1000;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Timer timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Random random=new Random();
                        setEWM("车辆编号=" + id + ",付费金额=" + money + "元"+random.nextInt(100));
                    }
                },0,t);
            }
        });
    }

    private void initView() {
        mLL = (LinearLayout) findViewById(R.id.LL);
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mInfo = (TextView) findViewById(R.id.info);
        mIv1 = (ImageView) findViewById(R.id.iv1);
        mIv2 = (ImageView) findViewById(R.id.iv2);

        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mIv1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                mInfo.setText("车辆编号=" + id + ",付费金额=" + money + "元");

                return true;
            }
        });

        mIv1.setOnClickListener(this);
        mIv2.setOnClickListener(this);

    }
    public void setEWM(String uri) {
        Hashtable<EncodeHintType, String> hashtable = new Hashtable<>();
        hashtable.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(uri, BarcodeFormat.QR_CODE, 300, 300, hashtable);
            int[] pix = new int[300 * 300];
            for (int x = 0; x < 300; x++) {
                for (int y = 0; y < 300; y++) {
                    if (bitMatrix.get(x, y)) {
                        pix[y * 300 + x] = 0xff000000;
                    } else {
                        pix[y * 300 + x] = 0xffffffff;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pix, 0, 300, 0, 0, 300, 300);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mIv1.setImageBitmap(bitmap);
                    mIv2.setImageBitmap(bitmap);
                }
            });

        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv1:
                mIv2.setVisibility(View.VISIBLE);
                mLL.setVisibility(View.GONE);
                break;
            case R.id.iv2:


                mLL.setVisibility(View.VISIBLE);
                mIv2.setVisibility(View.GONE);
                break;
        }
    }
}