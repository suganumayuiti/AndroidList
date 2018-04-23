package com.example.suga.dbsamp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "contact.dsb";
    static final int DB_Version = 1;


    public final static String ID = "_id";             // id
    static final String NAME  = "name";
    static final String Age = "age";



    public MyOpenHelper(Context context){
        super(context,DB_NAME,null,DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE PERSON ("
                +"ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"NAME TEXT NOT NULL,"
                +"AGE INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {
    }


}