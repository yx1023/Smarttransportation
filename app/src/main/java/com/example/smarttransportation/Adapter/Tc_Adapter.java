package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.TC;
import com.example.smarttransportation.R;

import java.util.List;

public class Tc_Adapter extends ArrayAdapter<TC> {
    public Tc_Adapter(@NonNull Context context, List<TC> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv=new HV();
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.sub_c,parent,false);
            hv.textView=convertView.findViewById(R.id.text1);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        TC tc=getItem(position);
        hv.textView.setText(tc.getParking_name());
        return convertView;
    }
    static  class HV{
        TextView textView;
    }
}
