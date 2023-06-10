package com.example.smarttransportation.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.smarttransportation.Fragment.CZJL_45;
import com.example.smarttransportation.Fragment.YCKZ_45;
import com.example.smarttransportation.Fragment.Yue_45;
import com.example.smarttransportation.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity45 extends AppCompatActivity implements View.OnClickListener {

    private ImageView mMune45;
    private TextView mDqyh;
    private ViewPager mVP;
    private TextView mWdye;
    private TextView mYckz;
    private TextView mCzjl;
    MyAdapter adapter;
    List<Fragment>list=new ArrayList<>();
    Yue_45 yue_45=new Yue_45();
    YCKZ_45 yckz_45=new YCKZ_45();
    CZJL_45 czjl_45=new CZJL_45();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main45);
        initView();
        adapter=new MyAdapter(this.getSupportFragmentManager(),list);
        mVP.setOffscreenPageLimit(3);
        mVP.setAdapter(adapter);
    }

    private void initView() {
        mMune45 = (ImageView) findViewById(R.id.mune45);
        mDqyh = (TextView) findViewById(R.id.dqyh);
        mVP = (ViewPager) findViewById(R.id.VP);
        mWdye = (TextView) findViewById(R.id.wdye);
        mYckz = (TextView) findViewById(R.id.yckz);
        mCzjl = (TextView) findViewById(R.id.czjl);
        mVP.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    mWdye.setTypeface(mWdye.getTypeface(), Typeface.BOLD);
                    mYckz.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    mCzjl.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }else  if(position==1){
                    mWdye.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    mYckz.setTypeface(mYckz.getTypeface(),Typeface.BOLD);
                    mCzjl.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
                else  if(position==2){
                    mWdye.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    mYckz.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    mCzjl.setTypeface(mCzjl.getTypeface(),Typeface.BOLD);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        list.add(yue_45);
        list.add(yckz_45);
        list.add(czjl_45);
        mWdye.setOnClickListener(this);
        mYckz.setOnClickListener(this);
        mCzjl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wdye:
                mVP.setCurrentItem(0);
                break;
            case R.id.yckz:
                mVP.setCurrentItem(1);
                break;
            case R.id.czjl:
                mVP.setCurrentItem(2);
                break;

        }
    }

     class MyAdapter extends FragmentPagerAdapter {
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