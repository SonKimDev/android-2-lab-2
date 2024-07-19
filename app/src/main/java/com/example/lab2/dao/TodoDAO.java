package com.example.lab2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab2.database.SQLite;
import com.example.lab2.model.ToDo;

import java.util.ArrayList;

public class TodoDAO {
    private final SQLite sqLite;
    private SQLiteDatabase database;

    public TodoDAO(Context context) {
        sqLite = new SQLite(context);
        database = sqLite.getWritableDatabase();
    }

    public boolean addToDo(ToDo toDo) {
        ContentValues values = new ContentValues();
        values.put("title", toDo.getTitle());
        values.put("content", toDo.getContent());
        values.put("date", toDo.getDate());
        values.put("type", toDo.getType());
        values.put("status", toDo.getStatus());
        long check = database.insert("todo", null, values);
        if (check > 0) {
            return true;
        }
        return false;
    }

    public boolean deleteToDo(int id) {
        long check = database.delete("todo", "id = ?", new String[]{String.valueOf(id)});
        if (check > 0) {
            return true;
        }
        return false;
    }

    public boolean updateToDo(ToDo toDo) {
        ContentValues values = new ContentValues();
        values.put("title", toDo.getTitle());
        values.put("content", toDo.getContent());
        values.put("date", toDo.getDate());
        values.put("type", toDo.getType());
        values.put("status", toDo.getStatus());
        long check = database.update("todo", values, "id = ?", new String[]{String.valueOf(toDo.getId())});
        if (check > 0) {
            return true;
        }
        return false;
    }

    public boolean updateStatusToDo(int id, boolean checkStatus) {
        int statusValue = checkStatus ? 1 : 0;
        ContentValues values = new ContentValues();
        values.put("status", statusValue);
        long check = database.update("todo", values, "id = ?", new String[]{String.valueOf(id)});
        if (check > 0) {
            return true;
        }
        return false;
    }

    public ArrayList<ToDo> getAllToDo() {
        ArrayList<ToDo> toDos = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM todo", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ToDo toDo = new ToDo();
                toDo.setId(cursor.getInt(0));
                toDo.setTitle(cursor.getString(1));
                toDo.setContent(cursor.getString(2));
                toDo.setDate(cursor.getString(3));
                toDo.setType(cursor.getString(4));
                toDo.setStatus(cursor.getInt(5));
                toDos.add(toDo);
            } while (cursor.moveToNext());
        }
        return toDos;
    }
}
