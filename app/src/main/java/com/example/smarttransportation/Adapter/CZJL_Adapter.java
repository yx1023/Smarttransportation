package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.A;
import com.example.smarttransportation.R;

import java.util.List;

public class CZJL_Adapter extends ArrayAdapter<A> {


    public CZJL_Adapter(@NonNull Context context, List<A> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.czji_item,parent,false);
            hv=new HV();
            hv.id=convertView.findViewById(R.id.id);
            hv.number=convertView.findViewById(R.id.number);
            hv.money=convertView.findViewById(R.id.money);
            hv.time=convertView.findViewById(R.id.time);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }

        A a=getItem(position);

        hv.id.setText(position+1+"");
        hv.time.setText(a.getTime());
        hv.money.setText(a.getMoney());
        hv.number.setText(a.getNumber());



        return convertView;
    }

    static class HV{
        TextView id;
        TextView number;
        TextView money;
        TextView time;
    }
}
