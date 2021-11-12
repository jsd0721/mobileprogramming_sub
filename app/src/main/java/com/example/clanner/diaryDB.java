package com.example.clanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class diaryDB extends SQLiteOpenHelper {

    public diaryDB(@Nullable Context context) {
        super(context, "diary.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table diary(_id integer primary key autoincrement, subject text, content text)");
    db.execSQL("insert into diary(subject) values('11월15일')");
    db.execSQL("insert into diary(subject) values('11월17일')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
