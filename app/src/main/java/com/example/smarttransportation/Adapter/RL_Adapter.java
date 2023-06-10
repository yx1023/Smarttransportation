package com.example.smarttransportation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarttransportation.Been.RL;
import com.example.smarttransportation.R;

import java.util.List;

public class RL_Adapter extends ArrayAdapter<RL> {
    public RL_Adapter(@NonNull Context context, List<RL> resource) {
        super(context,0 ,resource);
    }


    public interface ClickItem{
        void myClick(int position,int bg);
    }

    private ClickItem clickItem;

    public void setClickItem(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_rl,parent,false);
            holder=new ViewHolder();
            holder.LL=convertView.findViewById(R.id.LL);
            holder.t1=convertView.findViewById(R.id.tv1);
            holder.t2=convertView.findViewById(R.id.tv2);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        final RL rl=getItem(position);
        holder.t1.setText(rl.getSolar());
        holder.t2.setText(rl.getLunar());
        if(rl.getBg()==0){
            holder.LL.setBackgroundResource(R.drawable.rl1);
        }else if(rl.getBg()==1){
            holder.LL.setBackgroundResource(R.drawable.rl2);
        }else {
            holder.LL.setBackgroundResource(R.drawable.rl3);
        }

        holder.LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rl.isEnable()){
                    clickItem.myClick(position,rl.getBg());
                }
            }
        });


        return convertView;
    }

    static class ViewHolder{
        LinearLayout LL;
        TextView t1;
        TextView t2;


    }
}
