package com.example.smarttransportation.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarttransportation.Adapter.YCKZ_Adapter;
import com.example.smarttransportation.R;


import java.util.ArrayList;
import java.util.List;

public class YCKZ_45 extends Fragment {

    ListView lv;
    List<String>list=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.yckz_45,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv=view.findViewById(R.id.lv);
        for (int i = 1; i < 5; i++) {
            list.add(String.valueOf(i));
        }
        YCKZ_Adapter adapter=new YCKZ_Adapter(getContext(),list);
        lv.setAdapter(adapter);
    }


}
