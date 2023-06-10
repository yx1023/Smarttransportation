package com.example.smarttransportation.Fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarttransportation.Adapter.CZJL_Adapter;
import com.example.smarttransportation.Been.A;
import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.DBmarager;


import java.util.ArrayList;
import java.util.List;

public class CZJL_45 extends Fragment {

    List<A>list=new ArrayList<>();


    ListView lv;
    TextView TS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.czjl_45,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv=view.findViewById(R.id.lv);
        TS=view.findViewById(R.id.TS);
        DBmarager bmarager=new DBmarager(getContext());
        boolean b= bmarager.isExist("lll");
        if(b==false){
            TS.setVisibility(View.VISIBLE);
        }else {
            Cursor cursor=bmarager.queryDB("lll",null,null,null,null,null,"time desc",null);
            list=sendees(cursor);
            bmarager.closeDB();

            CZJL_Adapter adapter=new CZJL_Adapter(getContext(),list);
            lv.setAdapter(adapter);
        }

    }

    @SuppressLint("Range")
    public List<A>sendees(Cursor c){
        List<A>qList=new ArrayList<>();
        while (c.moveToNext()){
            A a=new A();
            a.setId(c.getInt(c.getColumnIndex("id")));
            a.setNumber(c.getString(c.getColumnIndex("number")));
            a.setMoney(c.getString(c.getColumnIndex("money")));
            a.setTime(c.getString(c.getColumnIndex("time")));
            qList.add(a);
        }
        return qList;

    }


}
