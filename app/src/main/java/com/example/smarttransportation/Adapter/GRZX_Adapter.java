package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.H;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GRZX_Adapter extends ArrayAdapter<String> {


    List<String>list=new ArrayList<>();
    public GRZX_Adapter(@NonNull Context context, List<String> resource,List<String>list) {
        super(context, 0,resource);
        this.list=list;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HV hv;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_20,parent,false);
            hv=new HV();
            hv.iv=convertView.findViewById(R.id.iv);
            hv.tv1=convertView.findViewById(R.id.plate);
            hv.tv2=convertView.findViewById(R.id.money);
            convertView.setTag(hv);
        }else {
            hv= (HV) convertView.getTag();
        }
        String s=getItem(position);
        hv.tv1.setText(s);
        hv.tv2.setText(list.get(position));
        if(position==0){
            hv.iv.setImageResource(R.drawable.benchi);

        } else if (position == 1) {
            hv.iv.setImageResource(R.drawable.baoma);
        }else {
            hv.iv.setImageResource(R.drawable.audi);
        }

        return convertView;
    }

    static class HV{
        ImageView iv;
        TextView tv1;
        TextView tv2;
    }

}
