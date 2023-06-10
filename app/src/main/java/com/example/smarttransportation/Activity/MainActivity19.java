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

import com.example.smarttransportation.Fragment.FX_19_1;
import com.example.smarttransportation.Fragment.FX_19_2;
import com.example.smarttransportation.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity19 extends AppCompatActivity {

    private ImageView mMune17;
    private TextView mText;
    private ViewPager mVP;
    private TextView mYuan1;
    private TextView mYuan2;
    MyAdapter adapter;
    List<Fragment>fragmentList=new ArrayList<>();
    FX_19_1 fx_19_1=new FX_19_1();
    FX_19_2 fx_19_2=new FX_19_2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main19);
        initView();
        adapter=new MyAdapter(this.getSupportFragmentManager(),fragmentList);
        mVP.setOffscreenPageLimit(2);
        mVP.setAdapter(adapter);
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mText = (TextView) findViewById(R.id.text);
        mVP = (ViewPager) findViewById(R.id.VP);
        mYuan1 = (TextView) findViewById(R.id.yuan1);
        mYuan2 = (TextView) findViewById(R.id.yuan2);

        mVP.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    mYuan1.setBackgroundResource(R.drawable.ag1);
                    mYuan2.setBackgroundResource(R.drawable.ag2);
                    mText.setText("平台上有违章车辆和没违章车辆占比统计");
                } else if (position == 1) {
                    mYuan1.setBackgroundResource(R.drawable.ag2);
                    mYuan2.setBackgroundResource(R.drawable.ag1);
                    mText.setText("排名前十的交通违法行为的占比统计");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fragmentList.add(fx_19_1);
        fragmentList.add(fx_19_2);
    }
    class MyAdapter extends FragmentPagerAdapter{

        List<Fragment>list=new ArrayList<>();
        public MyAdapter(@NonNull FragmentManager fm,List<Fragment>list) {
            super(fm);
            this.list= list;
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