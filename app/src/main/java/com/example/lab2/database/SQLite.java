package com.example.lab2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite extends SQLiteOpenHelper {

    public static final String DB_NAME = "todo.db";

    public SQLite(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE todo(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT, " +
                "content TEXT, " +
                "date TEXT, " +
                "type TEXT, " +
                "status INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS todo");
            onCreate(db);
        }
    }
}
