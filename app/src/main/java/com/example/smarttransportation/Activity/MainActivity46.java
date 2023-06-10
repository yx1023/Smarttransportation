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

import com.example.smarttransportation.Fragment.WDJT_46;
import com.example.smarttransportation.Fragment.WDLK_46;
import com.example.smarttransportation.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity46 extends AppCompatActivity {

    private ImageView mMune45;
    private TextView mDqyh;
    private ViewPager mVP;
    private TextView mWdlk;
    private TextView mDlhj;
    WDLK_46 wdlk_46=new WDLK_46();
    WDJT_46 wdjt_46=new WDJT_46();

    MY my;
    List<Fragment>list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main46);
        initView();
        my=new MY(this.getSupportFragmentManager(),list);
        mVP.setCurrentItem(2);
        mVP.setAdapter(my);
    }

    private void initView() {
        mMune45 = (ImageView) findViewById(R.id.mune45);
        mDqyh = (TextView) findViewById(R.id.dqyh);
        mVP = (ViewPager) findViewById(R.id.VP);
        mWdlk = (TextView) findViewById(R.id.wdlk);
        mDlhj = (TextView) findViewById(R.id.dlhj);
        list.add(wdlk_46);
        list.add(wdjt_46);
    }
    class MY extends FragmentPagerAdapter {

        List<Fragment> list=new ArrayList<>();

        public MY(@NonNull FragmentManager fm, List<Fragment>list) {
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