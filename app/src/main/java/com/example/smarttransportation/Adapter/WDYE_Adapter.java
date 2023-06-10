package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.Car;
import com.example.smarttransportation.R;

import java.util.List;

public class WDYE_Adapter extends ArrayAdapter<Car> {
    public WDYE_Adapter(@NonNull Context context, List<Car> resource) {
        super(context,0, resource);
    }

    public interface Myclick{
        void onclick(int number);
    }

    public Myclick getMyclick() {
        return myclick;
    }

    public void setMyclick(Myclick myclick) {
        this.myclick = myclick;
    }

    private Myclick myclick;



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.wdye_item,parent,false);
            hv=new HV();
            hv.t1=convertView.findViewById(R.id.item_title);
            hv.t2=convertView.findViewById(R.id.item_state);
            hv.button=convertView.findViewById(R.id.item_money);
            hv.rl=convertView.findViewById(R.id.bt_color);
            convertView.setTag(hv);

        }else {

            hv= (HV) convertView.getTag();

        }

        Car car=getItem(position);
        hv.t1.setText(car.getNumber()+"号小车");
        int a= Integer.parseInt(car.getBalance());
        if(a>=100){
            hv.t2.setText("正常");
            hv.rl.setBackgroundResource(R.color.lv);
        }else {
            hv.t2.setText("警告");
            hv.rl.setBackgroundResource(R.color.h);
        }
        hv.button.setText(car.getBalance());

        hv.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myclick.onclick(car.getNumber());
            }
        });


        return convertView;
    }


    static class HV{
        TextView t1;
        TextView t2;
        TextView button;
        RelativeLayout rl;
    }
}
