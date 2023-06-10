package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.YJFK;
import com.example.smarttransportation.R;

import java.util.List;

public class YJ_Adapter extends ArrayAdapter<YJFK> {
    public YJ_Adapter(@NonNull Context context, List<YJFK> resource) {
        super(context, 0,resource);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv=new HV();
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_31,parent,false);
            hv.bt=convertView.findViewById(R.id.BT);
            hv.time=convertView.findViewById(R.id.time);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        YJFK yjfk=getItem(position);
        hv.bt.setText("标题："+yjfk.getBT());
        hv.time.setText("提交时间："+yjfk.getTime());


        return convertView;
    }

    static class HV{
        TextView bt;
        TextView time;
    }
}
