package com.akshit.ontime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "Todo";
    private static final String TASK = "Task";
    private static final String DATE = "Date";
    private static final String TIME = "Time";
    private static final String DATABASE = "Todo.db";




    DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+"(task text,date text, time text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public void insertData(String name,String date,String time,SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK,name);
        contentValues.put(DATE,date);
        contentValues.put(TIME,time);
        db.insert(TABLE_NAME,null ,contentValues);
    }

    public Cursor getAllData(SQLiteDatabase database) {
        return database.query(TABLE_NAME,new String[]{TASK,DATE,TIME},null,null,null,null,null);
    }

    public void updateData(String id,String date,String time,SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK,id);
        contentValues.put(DATE,date);
        contentValues.put(TIME,time);
        db.update(TABLE_NAME, contentValues, "Task = ?",new String[] { id });
    }

    public void deleteData (String id,SQLiteDatabase db) {
        db.delete(TABLE_NAME, "Task = ?",new String[] {id});
    }
    public void deleteAll(SQLiteDatabase db){
        db.delete(TABLE_NAME,null,null);
    }
    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return count;
    }
}
