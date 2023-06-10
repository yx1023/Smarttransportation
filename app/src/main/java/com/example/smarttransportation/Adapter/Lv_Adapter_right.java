package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.Right;
import com.example.smarttransportation.R;

import java.util.List;

public class Lv_Adapter_right extends ArrayAdapter<Right> {
    public Lv_Adapter_right(@NonNull Context context, List<Right> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_right,parent,false);
            hv=new HV();
            hv.tv1=convertView.findViewById(R.id.time);
            hv.tv2=convertView.findViewById(R.id.road);
            hv.tv3=convertView.findViewById(R.id.message);
            hv.tv4=convertView.findViewById(R.id.kf);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        Right right=getItem(position);
        hv.tv1.setText(right.getTime());
        hv.tv2.setText(right.getRoad());
        hv.tv3.setText(right.getData());
        hv.tv4.setText("扣分： "+right.getKF()+"分           罚款：  "+right.getFK()+"  元");


        return convertView;
    }

    static class HV{
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
    }
}
