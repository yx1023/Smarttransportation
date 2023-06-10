package com.example.smarttransportation.Activity;

import android.content.ContentValues;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttransportation.R;
import com.example.smarttransportation.Utility.DBmarager;

public class MainActivity44_zc extends AppCompatActivity {

    private ImageView mFanhui;
    private EditText mNumber;
    private EditText mYouxiang;
    private EditText mMima1;
    private EditText mMima2;
    private Button mTj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity44_zc);
        initView();
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mNumber = (EditText) findViewById(R.id.number);
        mYouxiang = (EditText) findViewById(R.id.youxiang);
        mMima1 = (EditText) findViewById(R.id.mima1);
        mMima2 = (EditText) findViewById(R.id.mima2);
        mTj = (Button) findViewById(R.id.tj);
        mNumber.setFilters(new InputFilter[]{
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
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr=mNumber.getText().toString();
                if (!inputStr.matches(".*[a-zA-Z]{4}.*")) {
                    Toast.makeText(MainActivity44_zc.this, "输入的字符串不符合要求，请重新输入！", Toast.LENGTH_SHORT).show();
                }
                if(mYouxiang.getText().toString().length()<6){
                    Toast.makeText(MainActivity44_zc.this, "请输入不少于6位数字", Toast.LENGTH_SHORT).show();
                }
                if(mMima1.getText().toString().length()<6){
                    Toast.makeText(MainActivity44_zc.this, "请输入不少于6位数字", Toast.LENGTH_SHORT).show();
                }
                if(!mMima2.getText().toString().equals(mMima1.getText().toString())){
                    Toast.makeText(MainActivity44_zc.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                }

                DBmarager dBmarager=new DBmarager(MainActivity44_zc.this);
                boolean b=dBmarager.isExist("zhanghao");
                if(b==false){
                    String sql = "create table zhanghao (" +
                            "id integer primary key autoincrement," +
                            "                           number varchar," +
                            "                           mima varchar," +
                            "                           yx varchar);";
                    dBmarager.createtable(sql);
                }
                ContentValues cv=new ContentValues();
                cv.put("number",mNumber.getText().toString());
                cv.put("mima",mMima1.getText().toString());
                cv.put("yx",mYouxiang.getText().toString());
                dBmarager.insertDB("zhanghao",cv);
                Toast.makeText(MainActivity44_zc.this, "提交成功", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
}