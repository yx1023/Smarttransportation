package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.smarttransportation.Fragment.HBarChart_1;
import com.example.smarttransportation.Fragment.PieChart_1;
import com.example.smarttransportation.Fragment.PieChart_2;
import com.example.smarttransportation.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity15 extends AppCompatActivity {

    private ImageView mCaidan;
    private TextView mText;
    private ViewPager mVP;
    private TextView mYuan1;
    private TextView mYuan2;
    private TextView mYuan3;
    private TextView mYuan4;
    private TextView mYuan5;
    private TextView mYuan6;
    private TextView mYuan7;

    PieChart_1 pieChart_1=new PieChart_1();
    PieChart_2 pieChart_2=new PieChart_2();
    HBarChart_1 hBarChart_1=new HBarChart_1();

    List<Fragment>fragmentList=new ArrayList<>();

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main15);
        initView();
        adapter=new MyAdapter(this.getSupportFragmentManager(),fragmentList);
        mVP.setOffscreenPageLimit(7);
        mVP.setAdapter(adapter);
    }

    private void initView() {
        mCaidan = (ImageView) findViewById(R.id.caidan);
        mText = (TextView) findViewById(R.id.text);
        mVP = (ViewPager) findViewById(R.id.VP);
        mYuan1 = (TextView) findViewById(R.id.yuan1);
        mYuan2 = (TextView) findViewById(R.id.yuan2);
        mYuan3 = (TextView) findViewById(R.id.yuan3);
        mYuan4 = (TextView) findViewById(R.id.yuan4);
        mYuan5 = (TextView) findViewById(R.id.yuan5);
        mYuan6 = (TextView) findViewById(R.id.yuan6);
        mYuan7 = (TextView) findViewById(R.id.yuan7);
        fragmentList.add(pieChart_1);
        fragmentList.add(pieChart_2);
        fragmentList.add(hBarChart_1);
    }


    class MyAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public MyAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}