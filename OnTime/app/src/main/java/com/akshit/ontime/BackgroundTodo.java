package com.akshit.ontime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BackgroundTodo extends AsyncTask<String,Void,String> {

    @SuppressLint("StaticFieldLeak")
    Context ctx;
    private TodoItemAdapter adapter;
    List<TodoItem> list;
    @SuppressLint("StaticFieldLeak")
    private
    ListView listView;
    @SuppressLint("StaticFieldLeak")
    private Activity activity;

    public BackgroundTodo(Context ctx){
        this.ctx=ctx;
        activity = (Activity)ctx;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String method = strings[0];
        DatabaseHelper db = new DatabaseHelper(ctx);
        if(method.equals("get_entry")){
            listView = activity.findViewById(R.id.list);
            SQLiteDatabase database = db.getReadableDatabase();
            Cursor cursor = db.getAllData(database);
            String task,date,time;
            list = new ArrayList<>();
//            if(cursor.getCount()==0) return "Nothing";
            while (cursor.moveToNext()){
                task=cursor.getString(0);
                date=cursor.getString(1);
                time=cursor.getString(2);
                list.add(new TodoItem(task,date,time));
            }
            return "get_entry";
        }
        else if(method.equals("insert_entry")){

            SQLiteDatabase database = db.getWritableDatabase();
            db.insertData(strings[1],strings[2],strings[3],database);
            BackgroundTodo todo = new BackgroundTodo(ctx);
            todo.execute("get_entry");
            return "One row inserted...";
        }
        else if(method.equals("update_entry")){
            SQLiteDatabase database = db.getWritableDatabase();
            db.updateData(strings[1],strings[2],strings[3],database);
            BackgroundTodo todo = new BackgroundTodo(ctx);
            todo.execute("get_entry");
            return "updated...";
        }
        else if(method.equals("delete_entry")){
            SQLiteDatabase database = db.getWritableDatabase();
            db.deleteData(strings[4],database);
            BackgroundTodo todo = new BackgroundTodo(ctx);
            todo.execute("get_entry");
            return "deleted...";
        }
        else if(method.equals("delete_all"))
        {
            SQLiteDatabase database = db.getWritableDatabase();
            db.deleteAll(database);
            BackgroundTodo todo = new BackgroundTodo(ctx);
            todo.execute("get_entry");
            return "all deleted...";
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s.equals("get_entry")){
            adapter = new TodoItemAdapter(ctx,R.layout.custom_list1,list);
            listView.setAdapter(adapter);
        }
    }
}
