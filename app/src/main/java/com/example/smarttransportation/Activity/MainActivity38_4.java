package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;

public class MainActivity38_4 extends AppCompatActivity {

    private ImageView mFanhui;
    private TextView mXianlu;
    private EditText mName;
    private EditText mTel;
    private Spinner mScdd;
    private Button mBT3;
    private String xianlu;
    private String riqi;
    private String pj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main384);
        initView();
        Intent intent = getIntent();
        xianlu = intent.getStringExtra("xianlu");
        riqi = intent.getStringExtra("riqi");
        pj=intent.getStringExtra("pj");

        mXianlu.setText(xianlu);
        ArrayAdapter<String>adapter;
        if(xianlu.equals("光谷金融街————南湖商厦")){
            adapter=new ArrayAdapter<>(this,R.layout.spinne,new String[]{"光谷金融街","戎军南路","长河公园","南湖商厦"} );
        }else {
            adapter=new ArrayAdapter<>(this,R.layout.spinne,new String[]{"德州职业","大学东路","奥德乐广场","德州站"} );
        }
        mScdd.setAdapter(adapter);
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mXianlu = (TextView) findViewById(R.id.xianlu);
        mName = (EditText) findViewById(R.id.name);
        mTel = (EditText) findViewById(R.id.tel);
        mScdd = (Spinner) findViewById(R.id.scdd);
        mBT3 = (Button) findViewById(R.id.BT3);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity38_4.this,MainActivity38_5.class);
                intent.putExtra("xianlu",mXianlu.getText().toString());
                intent.putExtra("tel",mTel.getText().toString());
                intent.putExtra("dd",mScdd.getSelectedItem().toString());
                intent.putExtra("name",mName.getText().toString());
                intent.putExtra("time",riqi);
                intent.putExtra("pj",pj);
                startActivity(intent);
            }
        });
    }
}