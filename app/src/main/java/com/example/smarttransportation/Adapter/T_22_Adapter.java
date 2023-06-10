package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.T_22;
import com.example.smarttransportation.R;

import java.util.List;

public class T_22_Adapter extends ArrayAdapter<T_22> {
    public T_22_Adapter(@NonNull Context context, List<T_22> resource) {
        super(context, 0,resource);
    }

    public interface Myclock{
        void c(int i);
        void c1(int i);
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
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item22,parent,false);
            hv=new HV();
            hv.tv1=convertView.findViewById(R.id.number);
            hv.tv2=convertView.findViewById(R.id.money);
            hv.iv=convertView.findViewById(R.id.iv);
            hv.button=convertView.findViewById(R.id.bt);
            convertView.setTag(hv);

        }else {
            hv= (HV) convertView.getTag();
        }
        T_22 t_22=getItem(position);
        hv.tv1.setText(t_22.getNumber()+"");
        hv.tv2.setText(t_22.getMoney()+"");

        hv.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myclock.c(position);
            }
        });
        hv.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myclock.c1(position);
            }
        });

        return convertView;
    }

    static class HV{
        TextView tv1;
        TextView tv2;
        CheckBox iv;
        Button button;
    }
}
