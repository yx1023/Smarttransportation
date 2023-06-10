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

import com.example.smarttransportation.Fragment.A29_1;
import com.example.smarttransportation.Fragment.A29_2;
import com.example.smarttransportation.Fragment.A29_3;
import com.example.smarttransportation.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity29 extends AppCompatActivity {

    private ImageView mMune17;
    private ViewPager mVP;
    private TextView mYuan1;
    private TextView mYuan2;
    private TextView mYuan3;
    MyAdapter adapter;
    List<Fragment>list=new ArrayList<>();
    A29_1 a29_1=new A29_1();
    A29_2 a29_2=new A29_2();
    A29_3 a29_3=new A29_3();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main29);
        initView();
        adapter=new MyAdapter(this.getSupportFragmentManager(),list);
        mVP.setOffscreenPageLimit(3);
        mVP.setAdapter(adapter);
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mVP = (ViewPager) findViewById(R.id.VP);
        mYuan1 = (TextView) findViewById(R.id.yuan__1);
        mYuan2 = (TextView) findViewById(R.id.yuan__2);
        mYuan3 = (TextView) findViewById(R.id.yuan__3);
        mVP.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    mYuan1.setBackgroundResource(R.drawable.ag1);
                    mYuan2.setBackgroundResource(R.drawable.ag2);
                    mYuan3.setBackgroundResource(R.drawable.ag2);
                } else if (position == 1) {
                    mYuan1.setBackgroundResource(R.drawable.ag2);
                    mYuan2.setBackgroundResource(R.drawable.ag1);
                    mYuan3.setBackgroundResource(R.drawable.ag2);
                }else if (position == 2) {
                    mYuan1.setBackgroundResource(R.drawable.ag2);
                    mYuan2.setBackgroundResource(R.drawable.ag2);
                    mYuan3.setBackgroundResource(R.drawable.ag1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        list.add(a29_1);
        list.add(a29_2);
        list.add(a29_3);
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