package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.Left;
import com.example.smarttransportation.R;

import java.util.List;

public class Lv_Adapter_left extends ArrayAdapter<Left> {
    public Lv_Adapter_left(@NonNull Context context, List<Left> resource) {
        super(context, 0,resource);
    }

    public interface Myclock{
        void c(int i);
    }

    public Myclock getMyclock() {
        return myclock;
    }

    public void setMyclock(Myclock myclock) {
        this.myclock = myclock;
    }

    Myclock myclock;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_left,parent,false);
            hv=new HV();
            hv.tv1=convertView.findViewById(R.id.plate);
            hv.tv2=convertView.findViewById(R.id.size);
            hv.tv3=convertView.findViewById(R.id.kf);
            hv.iv=convertView.findViewById(R.id.jian);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        Left left=getItem(position);
        hv.tv1.setText(left.getPlate());
        hv.tv2.setText("未处理违章次数 "+left.getSize()+" 次");
        hv.tv3.setText("扣 "+left.getKF()+" 分          罚款 "+left.getMoney()+" 元");
        hv.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myclock.c(position);
            }
        });


        return convertView;
    }

    static class HV{
        TextView tv1;
        TextView tv2;
        TextView tv3;
        ImageView iv;
    }
}
