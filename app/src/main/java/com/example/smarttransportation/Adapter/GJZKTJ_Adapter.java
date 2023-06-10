package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.B;
import com.example.smarttransportation.R;


import java.util.ArrayList;
import java.util.List;


public class GJZKTJ_Adapter extends ArrayAdapter<B> {


    public GJZKTJ_Adapter(@NonNull Context context, List<B> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item4_1,parent,false);
            viewHolder.text1=convertView.findViewById(R.id.tv___1);
            viewHolder.text2=convertView.findViewById(R.id.tv___2);
            viewHolder.text3=convertView.findViewById(R.id.tv___3);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

            B b=getItem(position);

            viewHolder.text1.setText(b.getBus()+"");
            viewHolder.text2.setText(b.getBus()+"");
            viewHolder.text3.setText(b.getPerson()+"");



        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }



    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView text1;
        TextView text2;
        TextView text3;
    }
}
