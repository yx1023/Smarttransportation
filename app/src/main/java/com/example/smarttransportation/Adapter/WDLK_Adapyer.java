package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.RLD;
import com.example.smarttransportation.R;

import java.util.List;

public class WDLK_Adapyer extends ArrayAdapter<RLD> {
    public WDLK_Adapyer(@NonNull Context context, List<RLD> resource) {
        super(context, 0,resource);
    }
    public interface ClickItem{
        void myClickItem(int lx,int position);
    }

    private ClickItem clickItem;

    public void setClickItem(ClickItem clickItem) {
        this.clickItem = clickItem;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.wdzj_item,parent,false);
            hv=new HV();
            hv.b1=convertView.findViewById(R.id.hx);
            hv.b2=convertView.findViewById(R.id.zx);
            hv.number=convertView.findViewById(R.id.lk);
            hv.lv=convertView.findViewById(R.id.LD);
            hv.huang=convertView.findViewById(R.id.HD);
            hv.hong=convertView.findViewById(R.id.RD);
            hv.hx=convertView.findViewById(R.id.ZT1);
            hv.zx=convertView.findViewById(R.id.ZT2);
            hv.deng1=convertView.findViewById(R.id.D1);
            hv.deng2=convertView.findViewById(R.id.D2);
            hv.sz=convertView.findViewById(R.id.sz);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        RLD rld=getItem(position);
        hv.number.setText("路口"+rld.getNumber());
        hv.lv.setText("绿灯"+rld.getGreen()+"秒");
        hv.huang.setText("黄灯"+rld.getYellow()+"秒");
        hv.hong.setText("红灯"+rld.getRed()+"秒");
        if (rld.getHorizontal().equals("红")) {
           hv.deng1.setBackgroundResource(R.drawable.hongdeng);
            hv.hx.setText("红灯" + rld.getRedH() + "秒");
            hv.deng1.setText(rld.getRedH() + "");
        } else if (rld.getHorizontal().equals("绿")) {
            hv.deng1.setBackgroundResource(R.drawable.lvdeng);
            hv.hx.setText("绿灯" + rld.getGreenH() + "秒");
            hv.deng1.setText(rld.getGreenH() + "");
        }else {
            hv.deng1.setBackgroundResource(R.drawable.huangdeng);
            hv.hx.setText("黄灯" + rld.getYellowH() + "秒");
            hv.deng1.setText(rld.getYellowH() + "");
        }

        if (rld.getVertical().equals("红")) {
            hv.deng2.setBackgroundResource(R.drawable.hongdeng);
            hv.zx.setText("红灯" + rld.getRedV() + "秒");
            hv.deng2.setText(rld.getRedV() + "");
        } else if (rld.getVertical().equals("绿")) {
            hv.deng2.setBackgroundResource(R.drawable.lvdeng);
            hv.zx.setText("绿灯" + rld.getGreenV() + "秒");
            hv.deng2.setText(rld.getGreenV() + "");
        }else {
            hv.deng2.setBackgroundResource(R.drawable.huangdeng);
            hv.zx.setText("黄灯" + rld.getYellowV() + "秒");
            hv.deng2.setText(rld.getYellowV() + "");
        }
        hv.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem.myClickItem(1,position);
            }
        });
        hv.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem.myClickItem(2,position);
            }
        });
        hv.sz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem.myClickItem(3,position);
            }
        });



        return convertView;
    }

    static class HV{
        TextView number;
        TextView lv;
        TextView hong;
        TextView huang;
        TextView hx;
        TextView zx;
        TextView deng1;
        TextView deng2;
        TextView sz;
        Button b1;
        Button b2;
    }
}
