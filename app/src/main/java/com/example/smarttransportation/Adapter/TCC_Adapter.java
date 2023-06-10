package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.TCC;
import com.example.smarttransportation.R;

import java.util.List;

public class TCC_Adapter extends ArrayAdapter<TCC> {
    public TCC_Adapter(@NonNull Context context, List<TCC> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_tcc,parent,false);
            hv=new HV();
            hv.t1=convertView.findViewById(R.id.t1);
            hv.t2=convertView.findViewById(R.id.t2);
            hv.t3=convertView.findViewById(R.id.t3);
            hv.t4=convertView.findViewById(R.id.t4);
            hv.t5=convertView.findViewById(R.id.t5);
            hv.t6=convertView.findViewById(R.id.t6);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }

        TCC tcc=getItem(position);
        hv.t1.setText(tcc.getNumber()+"");
        hv.t2.setText(tcc.getPlate());
        hv.t3.setText("admin");
        hv.t4.setText(tcc.getEntrance());
        hv.t5.setText(tcc.getExit());
        hv.t6.setText(tcc.getPrice()+"");


        return convertView;
    }

    static class HV{
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        TextView t5;
        TextView t6;
    }
}
