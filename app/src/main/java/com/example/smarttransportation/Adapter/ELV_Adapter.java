package com.example.smarttransportation.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttransportation.Been.B;
import com.example.smarttransportation.R;

import java.util.List;
import java.util.Map;

public class ELV_Adapter extends BaseExpandableListAdapter {

    String[]strings={"中医院站","联想大厦站"};
    Map<String, List<B>>map;

    public ELV_Adapter(Map<String,List<B>>map){
            this.map=map;
    }
    @Override
    public int getGroupCount() {
        return map.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return map.get(strings[groupPosition]).size();
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder=new GroupViewHolder();
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.groups,parent,false);
            groupViewHolder.textView=convertView.findViewById(R.id.zhatai);
            groupViewHolder.imageView=convertView.findViewById(R.id.iv__1);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder= (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.textView.setText(strings[groupPosition]);
        if(isExpanded){
            groupViewHolder.imageView.setImageResource(R.drawable.jiantou1);
        }else {
            groupViewHolder.imageView.setImageResource(R.drawable.jiantou2);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder=new ChildViewHolder();
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.childs,parent,false);
            childViewHolder.t1=convertView.findViewById(R.id.number);
            childViewHolder.t3=convertView.findViewById(R.id.time);
            childViewHolder.t4=convertView.findViewById(R.id.juli);
            convertView.setTag(childViewHolder);
        }else {
            childViewHolder= (ChildViewHolder) convertView.getTag();
        }

        B b=map.get(strings[groupPosition]).get(childPosition);
        childViewHolder.t1.setText(b.getBus() + "号（" + b.getPerson() + "人）");
        childViewHolder.t3.setText((b.getDistance() / 5/60) + "分钟到达");
        childViewHolder.t4.setText("距离站台"+b.getDistance()+"米");



        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder{
        TextView textView;
        ImageView imageView;
    }
    static class ChildViewHolder{
        TextView t1;
        TextView t3;
        TextView t4;
    }
}
