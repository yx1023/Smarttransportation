package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.R;

import java.util.List;

public class YCKZ_Adapter extends ArrayAdapter<String> {
    public YCKZ_Adapter(@NonNull Context context, List<String> resource) {
        super(context,0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.yckz_item,parent,false);
            hv=new HV();
            hv.tv1=convertView.findViewById(R.id.number);
            hv.tv2=convertView.findViewById(R.id.qd);
            hv.tv3=convertView.findViewById(R.id.tz);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }

        String s=getItem(position);
        hv.tv1.setText(s);
        hv.tv2.setBackgroundResource(R.drawable.bg2);
        hv.tv3.setBackgroundResource(R.drawable.bg3);
        hv.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hv.tv2.setBackgroundResource(R.drawable.bg2);
                hv.tv3.setBackgroundResource(R.drawable.bg3);
                Toast.makeText(getContext(), s+"号小车启动成功", Toast.LENGTH_SHORT).show();
            }
        });
        hv.tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hv.tv3.setBackgroundResource(R.drawable.bg2);
                Toast.makeText(getContext(), s+"号小车停止成功", Toast.LENGTH_SHORT).show();
                hv.tv2.setBackgroundResource(R.drawable.bg3);
            }
        });
        return convertView;

    }

    static class HV{
        TextView tv1;
        TextView tv2;
        TextView tv3;
    }
}
