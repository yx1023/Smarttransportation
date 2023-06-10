package com.example.smarttransportation.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttransportation.Been.Subway;
import com.example.smarttransportation.R;

import java.util.ArrayList;
import java.util.List;

public class Subway_Adapter extends BaseExpandableListAdapter {

    List<Subway>list=new ArrayList<>();

    public Subway_Adapter(List<Subway>list){
        this.list=list;
    }
    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getSite().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition).getName();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getSite().get(childPosition);
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
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_g,parent,false);
            ghv.imageView=convertView.findViewById(R.id.jiantou);
            ghv.textView=convertView.findViewById(R.id.tv_1);
            convertView.setTag(ghv);
        }else {
            ghv= (GHV) convertView.getTag();
        }
            ghv.textView.setText(list.get(groupPosition).getName());
        if(isExpanded){
            ghv.imageView.setImageResource(R.drawable.xiajiantou);
        }else {
            ghv.imageView.setImageResource(R.drawable.youjiantou);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CHV chv=new CHV();
        if(convertView==null){
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_c,parent,false);
            chv.textView=convertView.findViewById(R.id.text1);
            convertView.setTag(chv);
        }else {
            chv= (CHV) convertView.getTag();
        }

        chv.textView.setText(list.get(groupPosition).getSite().get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    static class GHV{
        TextView textView;
        ImageView imageView;
    }
    static class CHV{
        TextView textView;
    }
}
