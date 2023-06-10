package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.C_43;
import com.example.smarttransportation.R;

import java.util.List;

public class Car_43 extends ArrayAdapter<C_43> {

    public Car_43(@NonNull Context context, List<C_43> resource) {
        super(context, 0,resource);
    }
    public interface Myclick{
        void c(int number);
    }

    public Myclick getMyclick() {
        return myclick;
    }

    public void setMyclick(Myclick myclick) {
        this.myclick = myclick;
    }

    Myclick myclick;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv=new HV();
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_43,parent,false);
            hv.button=convertView.findViewById(R.id.cz);
            hv.textView1=convertView.findViewById(R.id.number);
            hv.textView2=convertView.findViewById(R.id.money);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        C_43 c_43=getItem(position);
        hv.textView1.setText("第"+c_43.getNumber()+"号小车的剩余余额");
        hv.textView2.setText(c_43.getMoney()+"");

        hv.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myclick.c(c_43.getNumber());
            }
        });

        return convertView;
    }

    static class HV{
        TextView textView1;
        TextView textView2;
        Button button;
    }
}
