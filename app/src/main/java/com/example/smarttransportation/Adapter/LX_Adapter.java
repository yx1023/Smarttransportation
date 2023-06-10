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

import com.example.smarttransportation.Been.LX;
import com.example.smarttransportation.R;

import java.util.List;

public class LX_Adapter extends ArrayAdapter<LX> {
    public LX_Adapter(@NonNull Context context, List<LX> resource) {
        super(context,0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv=new HV();
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.lx_item,parent,false);
            hv.iv=convertView.findViewById(R.id.iv);
            hv.name=convertView.findViewById(R.id.name);
            hv.money=convertView.findViewById(R.id.money);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        LX lx=getItem(position);
        if(lx.getName().equals("故宫")){
                hv.iv.setImageResource(R.drawable.gugong);
        }else {
            hv.iv.setImageResource(R.drawable.tiananmen);
        }
        hv.name.setText(lx.getName());
        hv.money.setText("票价：￥"+lx.getPrice());


        return convertView;
    }

    static class HV{
        ImageView iv;
        TextView name;
        TextView money;
    }
}
