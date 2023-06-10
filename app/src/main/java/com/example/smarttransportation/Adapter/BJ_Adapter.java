package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.XX;
import com.example.smarttransportation.R;

import java.util.List;

public class BJ_Adapter extends ArrayAdapter<XX> {
    public BJ_Adapter(@NonNull Context context, List<XX> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item6,parent,false);
            hv=new HV();
            hv.t1=convertView.findViewById(R.id.number);
            hv.t2=convertView.findViewById(R.id.bjlx);
            hv.t3=convertView.findViewById(R.id.yz);
            hv.t4=convertView.findViewById(R.id.dqz);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        XX xx=getItem(position);
        hv.t1.setText(position+"");
        hv.t2.setText(xx.getBj());
        hv.t3.setText(xx.getYz()+"");
        hv.t4.setText(xx.getDqz()+"");

        return convertView;
    }

    static class HV{
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
    }
}
