package com.example.smarttransportation.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttransportation.Been.Bus;
import com.example.smarttransportation.R;

import java.util.ArrayList;
import java.util.List;

public class Bus_Adapter extends BaseExpandableListAdapter {

    private List<Bus>list=new ArrayList<>();

    public Bus_Adapter(List<Bus>list){
        this.list=list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getBusline().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GHV ghv=new GHV();
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_38,parent,false);
            ghv.t1=convertView.findViewById(R.id.number);
            ghv.t2=convertView.findViewById(R.id.bus_name);
            ghv.t3=convertView.findViewById(R.id.f_m);
            ghv.t4=convertView.findViewById(R.id.time1);
            ghv.t5=convertView.findViewById(R.id.time2);
            ghv.iv=convertView.findViewById(R.id.iv);
            convertView.setTag(ghv);
        }else {
            ghv= (GHV) convertView.getTag();
        }

        ghv.t1.setText(list.get(groupPosition).getId()+"路");
        ghv.t2.setText(list.get(groupPosition).getBusline().get(0)+"————"+list.get(groupPosition).getBusline().get(list.get(groupPosition).getBusline().size()-1));
        ghv.t3.setText("票价：￥"+list.get(groupPosition).getFares()+"   里程："+list.get(groupPosition).getMileage()+"km");
        String[]time=list.get(groupPosition).getTime().split("~");
        ghv.t4.setText(time[0]);
        ghv.t5.setText(time[1]);
        if(isExpanded){
            ghv.iv.setImageResource(R.drawable.xiajiantou);
        }else {
            ghv.iv.setImageResource(R.drawable.youjiantou);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CHV chv=new CHV();
        if(convertView==null){
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_c,parent,false);
            chv.t1=convertView.findViewById(R.id.text1);
            convertView.setTag(chv);
        }else {
            chv= (CHV) convertView.getTag();
        }
        chv.t1.setText(list.get(groupPosition).getBusline().get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GHV{
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        TextView t5;
        ImageView iv;

    }
    static class CHV{
        TextView t1;


    }
}
