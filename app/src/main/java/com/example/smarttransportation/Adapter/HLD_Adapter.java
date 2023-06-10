package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.HLD;
import com.example.smarttransportation.R;

import java.util.List;

public class HLD_Adapter extends ArrayAdapter<HLD> {
    public HLD_Adapter(@NonNull Context context, List<HLD> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item1,parent,false);
            hv=new HV();
            hv.tv1=convertView.findViewById(R.id.number);
            hv.tv2=convertView.findViewById(R.id.red);
            hv.tv3=convertView.findViewById(R.id.yellow);
            hv.tv4=convertView.findViewById(R.id.green);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        HLD hld=getItem(position);
        hv.tv1.setText(hld.getId()+"");
        hv.tv2.setText(hld.getRed()+"");
        hv.tv3.setText(hld.getYellow()+"");
        hv.tv4.setText(hld.getGreen()+"");



        return convertView;
    }

    static class HV{
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
    }
}
