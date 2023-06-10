package com.example.smarttransportation.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttransportation.Been.Road;
import com.example.smarttransportation.R;

import java.util.ArrayList;
import java.util.List;

public class Road_Adapter extends BaseExpandableListAdapter {
    List<Road>list=new ArrayList<>();
    public Road_Adapter(List<Road>list){
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
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_33,parent,false);
            ghv.t1=convertView.findViewById(R.id.id);
            ghv.t2=convertView.findViewById(R.id.road1);
            ghv.t3=convertView.findViewById(R.id.road2);
            ghv.t4=convertView.findViewById(R.id.type);
            ghv.iv=convertView.findViewById(R.id.ivv);
            convertView.setTag(ghv);
        }else {
            ghv= (GHV) convertView.getTag();
        }
        ghv.t1.setText(list.get(groupPosition).getRoadid());
        ghv.t2.setText(list.get(groupPosition).getRoad());
        ghv.t3.setText(list.get(groupPosition).getRoad());
        ghv.t4.setText(list.get(groupPosition).getType());
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
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_c,parent,false);
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
    static  class GHV{
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        ImageView iv;
    }
    static  class CHV{
        TextView textView;
    }
}
