package com.example.smarttransportation.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttransportation.Been.B;
import com.example.smarttransportation.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ELV_28 extends BaseExpandableListAdapter {

    String[] strings={"一号站台","二号站台"};
    Map<String, List<B>>map=new HashMap<>();

    public ELV_28(Map<String,List<B>>map){
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ELV_Adapter.GroupViewHolder groupViewHolder=new ELV_Adapter.GroupViewHolder();
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.groups,parent,false);
            groupViewHolder.textView=convertView.findViewById(R.id.zhatai);
            groupViewHolder.imageView=convertView.findViewById(R.id.iv__1);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder= (ELV_Adapter.GroupViewHolder) convertView.getTag();
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
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.child_2,parent,false);
            childViewHolder.textView1=convertView.findViewById(R.id.tv1);
            childViewHolder.textView2=convertView.findViewById(R.id.tv2);
            convertView.setTag(childViewHolder);
        }else {
            childViewHolder= (ChildViewHolder) convertView.getTag();
        }
        B b=map.get(strings[groupPosition]).get(childPosition);
        childViewHolder.textView1.setText(b.getBus()+"号公交车");
        childViewHolder.textView2.setText(b.getDistance()+"米");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder{
        TextView textView;
        ImageView imageView;
    }
    static class ChildViewHolder{
        TextView textView1;
        TextView textView2;
    }
}
