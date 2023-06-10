package com.example.smarttransportation.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttransportation.Been.DD;
import com.example.smarttransportation.R;

import java.util.ArrayList;
import java.util.List;

public class DD_Adapter extends BaseExpandableListAdapter {
    List<DD>list=new ArrayList<>();

    public DD_Adapter(List<DD>list){
        this.list=list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getData().size();
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
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.dd_item,parent,false);
            ghv.iv=convertView.findViewById(R.id.iv);
            ghv.tv1=convertView.findViewById(R.id.xianlu);
            ghv.tv2=convertView.findViewById(R.id.piaojia);
            ghv.tv3=convertView.findViewById(R.id.dd_number);
            convertView.setTag(ghv);
        }else {
            ghv= (GHV) convertView.getTag();
        }
        if(isExpanded){
            ghv.iv.setImageResource(R.drawable.xiajiantou);
        }else {
            ghv.iv.setImageResource(R.drawable.youjiantou);
        }
        ghv.tv1.setText(list.get(groupPosition).getLine());
        ghv.tv2.setText("票价：￥"+list.get(groupPosition).getPrice());
        ghv.tv3.setText("订单编号："+list.get(groupPosition).getNum());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CHV chv=new CHV();
        if(convertView==null ){
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.dd_item2,parent,false);
            chv.tv1=convertView.findViewById(R.id.tv);
            chv.tv2=convertView.findViewById(R.id.tv_time);
            convertView.setTag(chv);
        }else {
            chv= (CHV) convertView.getTag();
        }
        if(childPosition!=0){
            chv.tv1.setVisibility(View.INVISIBLE);
        }
        chv.tv2.setText(list.get(groupPosition).getData().get(childPosition));


        return convertView;
    }

    static class GHV{
        TextView tv1;
        TextView tv2;
        TextView tv3;
        ImageView iv;
    }
    static class CHV{
        TextView  tv1;
        TextView  tv2;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
