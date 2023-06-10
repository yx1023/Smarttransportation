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

import com.example.smarttransportation.Fragment.GRXX;
import com.example.smarttransportation.Fragment.YZSZ;
import com.example.smarttransportation.Fragment.ZDJL;
import com.example.smarttransportation.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity16 extends AppCompatActivity {

    private ImageView mCaidan;
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private ViewPager mVP;
    MyAdapter myAdapter;
    List<Fragment>fragmentList=new ArrayList<>();
    GRXX grxx=new GRXX();
    ZDJL zdjl=new ZDJL();
    YZSZ yzsz=new YZSZ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main16);
        initView();
        myAdapter=new MyAdapter(this.getSupportFragmentManager(),fragmentList);
        mVP.setOffscreenPageLimit(3);
        mVP.setAdapter(myAdapter);
    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mTv3 = (TextView) findViewById(R.id.tv3);
        mVP = (ViewPager) findViewById(R.id.VP);
        fragmentList.add(grxx);
        fragmentList.add(zdjl);
        fragmentList.add(yzsz);
        mVP.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    mTv1.setBackgroundResource(R.drawable.xiahuaxian);
                    mTv2.setBackground(null);
                    mTv3.setBackground(null);
                } else if (position == 1) {
                    mTv2.setBackgroundResource(R.drawable.xiahuaxian);
                    mTv1.setBackground(null);
                    mTv3.setBackground(null);
                }else if (position == 2) {
                    mTv3.setBackgroundResource(R.drawable.xiahuaxian);
                    mTv1.setBackground(null);
                    mTv2.setBackground(null);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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