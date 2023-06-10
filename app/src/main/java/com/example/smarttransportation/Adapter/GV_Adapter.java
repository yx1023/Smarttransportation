package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.Weather;
import com.example.smarttransportation.R;

import java.util.List;

public class GV_Adapter extends ArrayAdapter<Weather> {
    List<String>strings;

    public GV_Adapter(@NonNull Context context, List<Weather> resource, List<String> strings) {
        super(context, 0,resource);
        this.strings=strings;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHould viewHould;
        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.gv_item,parent,false);

            viewHould=new ViewHould();
            viewHould.tv1=convertView.findViewById(R.id.item1);
            viewHould.tv2=convertView.findViewById(R.id.item2);
            viewHould.tv3=convertView.findViewById(R.id.item3);
            viewHould.iv=convertView.findViewById(R.id.item_iv);
            viewHould.ll=convertView.findViewById(R.id.LL);
            convertView.setTag(viewHould);
        }else {
            viewHould= (ViewHould) convertView.getTag();
        }
        Weather a=getItem(position);
        viewHould.tv1.setText(strings.get(position));
        switch (a.getWeather()){
            case "晴":
                viewHould.iv.setImageResource(R.drawable.qingtian);
                viewHould.ll.setBackgroundResource(R.color.lan);
                break;
            case "阴":
                viewHould.iv.setImageResource(R.drawable.yin);
                viewHould.ll.setBackgroundResource(R.color.lan);
                break;
            case "小雨":
                viewHould.iv.setImageResource(R.drawable.xiaoyu);
                viewHould.ll.setBackgroundResource(R.color.hui);
                break;
        }
        viewHould.tv2.setText(a.getWeather());
        viewHould.tv3.setText(a.getInterval().replace("~","/")+"℃");


        return convertView;
    }

    static class ViewHould{
        TextView tv1;
        TextView tv2;
        TextView tv3;
        ImageView iv;
        LinearLayout ll;
    }

}
