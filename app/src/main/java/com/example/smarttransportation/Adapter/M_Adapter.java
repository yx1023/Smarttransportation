package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.M;
import com.example.smarttransportation.R;

import java.util.List;

public class M_Adapter extends ArrayAdapter<M> {
    public M_Adapter(@NonNull Context context, List<M> resource) {
        super(context, 0,resource);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv=new HV();
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item1,parent,false);
            hv.textView1=convertView.findViewById(R.id.number);
            hv.textView2=convertView.findViewById(R.id.red);
            hv.textView3=convertView.findViewById(R.id.yellow);
            hv.textView4=convertView.findViewById(R.id.green);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        M m =getItem(position);
        hv.textView1.setText(m.getNumber()+"");
        hv.textView2.setText(m.getPlate());
        hv.textView3.setText(m.getMoney()+"");
        hv.textView4.setText(m.getTime()+"");


        return convertView;
    }

    static class HV{
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
    }
}
