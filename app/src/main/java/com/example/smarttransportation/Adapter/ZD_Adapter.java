package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.R;
import com.example.smarttransportation.Been.ZD;

import java.util.List;

public class ZD_Adapter extends ArrayAdapter<ZD> {
    public ZD_Adapter(@NonNull Context context, List<ZD> resource) {
        super(context, 0,resource);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item2,parent,false);
            hv=new HV();
            hv.id=convertView.findViewById(R.id.id);
            hv.money=convertView.findViewById(R.id.money);
            hv.number=convertView.findViewById(R.id.number);
            hv.time=convertView.findViewById(R.id.time);
            hv.name=convertView.findViewById(R.id.name);
           convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        ZD zd=getItem(position);
        hv.id.setText(zd.getId()+"");
        hv.money.setText(zd.getMoney()+"");
        hv.number.setText(zd.getNumber()+"");
        hv.name.setText(zd.getName());
        hv.time.setText(zd.getTime());

        return  convertView;
    }

    static class HV{
        TextView id;
        TextView number;
        TextView name;
        TextView time;
        TextView money;
    }
}
