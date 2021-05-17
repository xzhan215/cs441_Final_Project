package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {


    private static final int VERSION = 5;

    public MyDatabaseHelper(Context context) {
        super(context, "Login.db", null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table User(email text primary key, password text, type text)");
        db.execSQL("Create table Checklist(item text primary key)");
        db.execSQL("Create table Review(order_number text primary key, factory text, content text, created_at datetime DEFAULT (datetime('now','localtime')))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 3) {
            db.execSQL("drop table if exists User");
            db.execSQL("drop table if exists Checklist");
            onCreate(db);
        }
        if(oldVersion < 5) {
            db.execSQL("drop table if exists User");
            db.execSQL("drop table if exists Checklist");
            db.execSQL("drop table if exists Review");
            onCreate(db);
        }
    }
}

