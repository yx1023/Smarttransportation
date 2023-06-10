package com.example.smarttransportation.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.DBmarager;

public class MainActivity44_zh extends AppCompatActivity {

    private ImageView mFanhui;
    private EditText mZhNumber;
    private EditText mZhYouxiang;
    private Button mZh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity44_zh);
        initView();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mZhNumber = (EditText) findViewById(R.id.zh_number);
        mZhYouxiang = (EditText) findViewById(R.id.zh_youxiang);
        mZh = (Button) findViewById(R.id.zh);
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mZhNumber.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        for (int i = start; i < end; i++) {
                            if (!Character.isLetter(source.charAt(i))) {
                                return "";
                            }
                        }
                        return null;
                    }
                }
        });
        mZh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr=mZhNumber.getText().toString();
                String youxiang=mZhYouxiang.getText().toString();

                if (!inputStr.matches(".*[a-zA-Z]{4}.*")) {
                    Toast.makeText(MainActivity44_zh.this, "输入的字符串不符合要求，请重新输入！", Toast.LENGTH_SHORT).show();
                }
                if(youxiang.length()<6){
                    Toast.makeText(MainActivity44_zh.this, "请输入不少于6位数字", Toast.LENGTH_SHORT).show();
                }

                DBmarager marager=new DBmarager(MainActivity44_zh.this);
                Cursor c= marager.queryDB("zhanghao",null,"number=?",new String[]{inputStr},null,null,null,null);
                c.moveToNext();
                System.out.println(c+"/////////");


                if(c.getCount()==0){
                    Toast.makeText(MainActivity44_zh.this, "该用户不存在", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    @SuppressLint("Range") String yx=c.getString(c.getColumnIndex("yx"));
                    @SuppressLint("Range") String mima=c.getString(c.getColumnIndex("mima"));
                    String s= String.valueOf(yx);
                    if(youxiang.equals(s)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity44_zh.this);
                        builder.setMessage("密码："+mima);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }else {
                        Toast.makeText(MainActivity44_zh.this, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
                    }



                }


            }
        });
    }
}