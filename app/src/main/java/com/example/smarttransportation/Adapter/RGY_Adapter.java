package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.HLD;
import com.example.smarttransportation.R;

import java.util.List;

public class RGY_Adapter extends ArrayAdapter<HLD> {
    public RGY_Adapter(@NonNull Context context, List<HLD> resource) {
        super(context,0, resource);
    }

    public interface Myclock{
        void c(int id,int number);
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
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item5,parent,false);
            hv=new HV();
            hv.tv1=convertView.findViewById(R.id.number);
            hv.tv2=convertView.findViewById(R.id.red);
            hv.tv3=convertView.findViewById(R.id.green);
            hv.tv4=convertView.findViewById(R.id.yellow);
            hv.cb=convertView.findViewById(R.id.Box);
            hv.button=convertView.findViewById(R.id.sz);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        HLD hld=getItem(position);
        hv.tv1.setText(hld.getNumber()+"");
        hv.tv2.setText(hld.getRed()+"");
        hv.tv3.setText(hld.getGreen()+"");
        hv.tv4.setText(hld.getYellow()+"");
        hv.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myclock.c(1,hld.getNumber());
            }
        });
        hv.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myclock.c(2,hld.getNumber());
            }
        });

        return convertView;
    }

    static class HV{
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        CheckBox cb;
        Button button;

    }
}
