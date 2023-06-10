package com.example.smarttransportation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;

public class MainActivity37 extends AppCompatActivity {

    private ImageView mMune36;
    private ImageView mShuaxin;
    private Spinner mSp;
    private EditText mEt;
    private EditText mEt2;
    private Button mSC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main37);
        initView();
        ArrayAdapter<String>adapter=new ArrayAdapter<>(this,R.layout.spinne,new String[]{"1","2","3","4"});
        mSp.setAdapter(adapter);
    }

    private void initView() {
        mMune36 = (ImageView) findViewById(R.id.mune36);
        mShuaxin = (ImageView) findViewById(R.id.shuaxin);
        mSp = (Spinner) findViewById(R.id.sp);
        mEt = (EditText) findViewById(R.id.et);
        mEt2 = (EditText) findViewById(R.id.et2);
        mSC = (Button) findViewById(R.id.SC);
        mSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TJ();
            }
        });
    }
    public void TJ(){
        if(mEt.getText().toString().equals("")){
            Toast.makeText(MainActivity37.this, "付费金额不能为空", Toast.LENGTH_SHORT).show();
            mEt.requestFocus();
            return;
        }
        if(mEt2.getText().toString().equals("")){
            Toast.makeText(MainActivity37.this, "二维码更新周期不能为空", Toast.LENGTH_SHORT).show();
            mEt2.requestFocus();
            return;
        }


        Intent intent=new Intent(MainActivity37.this,MainActivity37_2.class);
        intent.putExtra("id",mSp.getSelectedItem().toString());
        intent.putExtra("money",mEt.getText().toString());
        intent.putExtra("time",mEt2.getText().toString());
        startActivity(intent);

    }
}