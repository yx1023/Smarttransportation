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

import com.example.smarttransportation.Fragment.XXCX;
import com.example.smarttransportation.Fragment.XXFX;
import com.example.smarttransportation.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity18 extends AppCompatActivity {

    private ImageView mMune17;
    private ViewPager mVp;
    private TextView mCx;
    private TextView mFx;
    MyAdapter myAdapter;
    XXCX xxcx=new XXCX();

    XXFX xxfx=new XXFX();

    List<Fragment>fragmentList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main18);
        initView();
        myAdapter=new MyAdapter(this.getSupportFragmentManager(),fragmentList);
        mVp.setOffscreenPageLimit(2);
        mVp.setAdapter(myAdapter);

    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mVp = (ViewPager) findViewById(R.id.vp);
        mCx = (TextView) findViewById(R.id.cx);
        mFx = (TextView) findViewById(R.id.fx);
        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    mCx.setBackgroundResource(R.drawable.bg2);
                    mFx.setBackgroundResource(R.drawable.bg);
                } else if (position == 1) {
                    mCx.setBackgroundResource(R.drawable.bg);
                    mFx.setBackgroundResource(R.drawable.bg2);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragmentList.add(xxcx);
        fragmentList.add(xxfx);
    }

    class MyAdapter extends FragmentPagerAdapter{

        List<Fragment>fragmentList=new ArrayList<>();

        public MyAdapter(@NonNull FragmentManager fm,List<Fragment>fragmentList) {

            super(fm);
            this.fragmentList=fragmentList;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}