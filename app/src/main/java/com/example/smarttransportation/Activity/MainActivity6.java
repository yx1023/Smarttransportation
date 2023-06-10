package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.smarttransportation.Fragment.CO2_Fragment;
import com.example.smarttransportation.Fragment.DL_Fragment;
import com.example.smarttransportation.Fragment.GZ_Fragment;
import com.example.smarttransportation.Fragment.PM_Fragment;
import com.example.smarttransportation.Fragment.SD_Fragment;
import com.example.smarttransportation.Fragment.WD_Fragment;
import com.example.smarttransportation.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity6 extends AppCompatActivity {

    private ImageView mCaidan;
    private TextView mText;
    private ViewPager mVP;
    private TextView mTvWD;
    private TextView mTvSD;
    private TextView mTvPM25;
    private TextView mTvCO2;
    private TextView mTvGZ;
    private TextView mTvDLZT;
    List<Fragment>list=new ArrayList<>();
    WD_Fragment wd_fragment=new WD_Fragment();
    SD_Fragment sd_fragment=new SD_Fragment();
    PM_Fragment pm_fragment=new PM_Fragment();
    CO2_Fragment co2_fragment=new CO2_Fragment();
    GZ_Fragment gz_fragment=new GZ_Fragment();
    DL_Fragment dl_fragment=new DL_Fragment();
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        initView();
        adapter=new MyAdapter(this.getSupportFragmentManager(),list);
        mVP.setOffscreenPageLimit(6);
        mVP.setAdapter(adapter);
    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mText = (TextView) findViewById(R.id.text);
        mVP = (ViewPager) findViewById(R.id.VP);
        mTvWD = (TextView) findViewById(R.id.tv_WD);
        mTvSD = (TextView) findViewById(R.id.tv_SD);
        mTvPM25 = (TextView) findViewById(R.id.tv_PM25);
        mTvCO2 = (TextView) findViewById(R.id.tv_CO2);
        mTvGZ = (TextView) findViewById(R.id.tv_GZ);
        mTvDLZT = (TextView) findViewById(R.id.tv_DLZT);

        mVP.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mText.setText("温度");
                        mTvWD.setBackgroundResource(R.drawable.ag1);
                        mTvCO2.setBackgroundResource(R.drawable.ag2);
                        mTvPM25.setBackgroundResource(R.drawable.ag2);
                        mTvSD.setBackgroundResource(R.drawable.ag2);
                        mTvGZ.setBackgroundResource(R.drawable.ag2);
                        mTvDLZT.setBackgroundResource(R.drawable.ag2);
                        break;
                    case 1:
                        mText.setText("湿度");
                        mTvWD.setBackgroundResource(R.drawable.ag2);
                        mTvCO2.setBackgroundResource(R.drawable.ag2);
                        mTvPM25.setBackgroundResource(R.drawable.ag2);
                        mTvSD.setBackgroundResource(R.drawable.ag1);
                        mTvGZ.setBackgroundResource(R.drawable.ag2);
                        mTvDLZT.setBackgroundResource(R.drawable.ag2);

                        break;
                    case 2:
                        mText.setText("PM2.5");
                        mTvWD.setBackgroundResource(R.drawable.ag2);
                        mTvCO2.setBackgroundResource(R.drawable.ag2);
                        mTvPM25.setBackgroundResource(R.drawable.ag1);
                        mTvSD.setBackgroundResource(R.drawable.ag2);
                        mTvGZ.setBackgroundResource(R.drawable.ag2);
                        mTvDLZT.setBackgroundResource(R.drawable.ag2);
                        break;
                    case 3:
                        mText.setText("CO2");
                        mTvWD.setBackgroundResource(R.drawable.ag2);
                        mTvCO2.setBackgroundResource(R.drawable.ag1);
                        mTvPM25.setBackgroundResource(R.drawable.ag2);
                        mTvSD.setBackgroundResource(R.drawable.ag2);
                        mTvGZ.setBackgroundResource(R.drawable.ag2);
                        mTvDLZT.setBackgroundResource(R.drawable.ag2);
                        break;
                    case 4:
                        mText.setText("光照");
                        mTvWD.setBackgroundResource(R.drawable.ag2);
                        mTvCO2.setBackgroundResource(R.drawable.ag2);
                        mTvPM25.setBackgroundResource(R.drawable.ag2);
                        mTvSD.setBackgroundResource(R.drawable.ag2);
                        mTvGZ.setBackgroundResource(R.drawable.ag1);
                        mTvDLZT.setBackgroundResource(R.drawable.ag2);
                        break;
                    case 5:
                        mText.setText("道路状况");
                        mTvWD.setBackgroundResource(R.drawable.ag2);
                        mTvCO2.setBackgroundResource(R.drawable.ag2);
                        mTvPM25.setBackgroundResource(R.drawable.ag2);
                        mTvSD.setBackgroundResource(R.drawable.ag2);
                        mTvGZ.setBackgroundResource(R.drawable.ag2);
                        mTvDLZT.setBackgroundResource(R.drawable.ag1);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        list.add(wd_fragment);
        list.add(sd_fragment);
        list.add(pm_fragment);
        list.add(co2_fragment);
        list.add(gz_fragment);
        list.add(dl_fragment);

    }



    class MyAdapter extends FragmentPagerAdapter{

            List<Fragment>list=new ArrayList<>();
            public MyAdapter(@NonNull FragmentManager fm,List<Fragment>list) {
                super(fm);
                this.list=list;
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        }
}