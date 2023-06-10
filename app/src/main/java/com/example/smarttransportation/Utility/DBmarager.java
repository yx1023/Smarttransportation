package com.example.smarttransportation.Utility;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBmarager extends SQLiteOpenHelper {
    SQLiteDatabase db;
static String db_name="tables.db";
static int db_version=1;

    public DBmarager(@Nullable Context context) {
        super(context, db_name, null,db_version);
        db=getWritableDatabase();
    }
    //打开数据库
    public SQLiteDatabase openDB(){
        return db=getWritableDatabase();
    }
    //关闭数据库
    public void closeDB(){
        db.close();
    }
    //建立数据表
    public boolean createtable(String sql){
        openDB();
        try {
            db.execSQL(sql);
            closeDB();
        }catch (Exception ex){
            closeDB();
            return false;
        }
        return true;
    }
    //输出表名  判断表是否存在
    public boolean isExist(String tablename){
        String sql="select * from "+tablename;
        openDB();
        try {
            db.rawQuery(sql,null);
            closeDB();
        }catch (Exception ex){
            closeDB();
            return false;
        }
        return true;
    }
    //插入
    public boolean insertDB(String table, ContentValues cv){
        openDB();
        try {
            //执行代码
            db.insert(table,null,cv);
            closeDB();
        }catch (Exception ex){
            closeDB();
            return false;
        }
        return true;
    }
    //删除
    public boolean deltable(String tablename , String position, String[] a){
        openDB();
        try {
            db.delete(tablename, position,a);
            closeDB();
        }catch (Exception ex){
            closeDB();
            return false;
        }
        return true;
    }
    //查找
    public Cursor queryDB(String tablename, String [] cols, String argwhere, String[] args, String group, String having, String order, String litmit){
        Cursor c;
        openDB();
        try {
            //执行代码
            c=db.query(tablename,cols,argwhere,args,group,having,order,litmit);

        }catch (Exception ex){

            return null;
        }
        return c;
    }
    //修改
    public boolean uptable(String tablename,ContentValues values ,String name, String[]b){
        openDB();
        try{
            db.update(tablename,values,name,b);
            closeDB();
        }catch (Exception ex){
            closeDB();
            return false;
        }
        return true;
    }








    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
