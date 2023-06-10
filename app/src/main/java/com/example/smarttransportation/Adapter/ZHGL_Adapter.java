package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.ZHGL;
import com.example.smarttransportation.R;

import java.util.List;

public class ZHGL_Adapter extends ArrayAdapter<ZHGL> {
    public ZHGL_Adapter(@NonNull Context context, List<ZHGL> resource) {
        super(context, 0,resource);
    }

    public interface Myclock{
        void c1(String plate);
        void c2(String plate);
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
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item3,parent,false);
            hv=new HV();
            hv.cz=convertView.findViewById(R.id.cz);
            hv.number=convertView.findViewById(R.id.number);
            hv.plate=convertView.findViewById(R.id.plate);
            hv.name=convertView.findViewById(R.id.name);
            hv.iv1=convertView.findViewById(R.id.iv);
            hv.iv2=convertView.findViewById(R.id.iv2);
            hv.money=convertView.findViewById(R.id.money);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        ZHGL zhgl=getItem(position);
        hv.number.setText(zhgl.getNumber()+"");
        if(zhgl.getBrand().equals("奔驰")){
            hv.iv1.setImageResource(R.drawable.benchi);
        } else if (zhgl.getBrand().equals("宝马")) {
            hv.iv1.setImageResource(R.drawable.baoma);
        }else if (zhgl.getBrand().equals("中华")) {
            hv.iv1.setImageResource(R.drawable.zhonghua);
        }else if (zhgl.getBrand().equals("奥迪")) {
            hv.iv1.setImageResource(R.drawable.audi);
        }
        hv.plate.setText(zhgl.getPlate());
        hv.money.setText("余额："+zhgl.getBalance()+"元");
        hv.name.setText("车主："+zhgl.getOwner());






        hv.iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                myclock.c1(zhgl.getPlate());
            }
        });
        hv.cz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myclock.c2(zhgl.getPlate());
            }
        });


        return convertView;
    }

    static class HV{
        TextView number;
        ImageView iv1;
        TextView name;
        TextView plate;
        CheckBox iv2;
        Button cz;
        TextView money;
    }
}
