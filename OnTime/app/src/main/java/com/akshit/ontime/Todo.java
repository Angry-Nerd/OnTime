package com.akshit.ontime;


import android.app.AlarmManager;
import android.app.DatePickerDialog;

import android.app.PendingIntent;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Build;

import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class Todo extends AppCompatActivity {
    Toolbar toolbar;
    List<TodoItem> arrayList;
    TextView textView;
    static boolean is_Date_Set=false;
    ListView listView;
    boolean is_Time_Set=false;
    Calendar calendar = Calendar.getInstance();
    boolean is_Item_deleted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        toolbar = findViewById(R.id.toolba1);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Todo");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        textView=findViewById(R.id.noTask);
        listView = findViewById(R.id.list);

        textView.setVisibility(View.GONE);
        BackgroundTodo todo = new BackgroundTodo(this);
        todo.execute("get_entry");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> adapterView, final View view, final int i1, final long l) {

                Toast.makeText(getApplicationContext(), "Enter Deadline", Toast.LENGTH_LONG).show();
                final String[] hour = new String[1];
                final String[] day = new String[1];
                final int[] h = new int[1];
                final int[] m = new int[1];
                final int[] d = new int[1];
                final int[] month = new int[1];
                TimePickerDialog.OnTimeSetListener dates = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int j1, int j2) {
                        hour[0] = Integer.toString(j1);
                        hour[0] += ":" + Integer.toString(j2);
                        h[0] =j1;
                        m[0] =j2;
                        is_Time_Set = true;
                    }
                };
                final TimePickerDialog timePickerDialog = new TimePickerDialog(Todo.this, dates, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

                DatePickerDialog.OnDateSetListener dater = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DAY_OF_MONTH, i2);
                        day[0]=i2+"/"+i1+"/"+i;
                        d[0] =i2;
                        month[0] =i1;
                        is_Date_Set = true;
                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(Todo.this, dater, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Toast.makeText(getApplicationContext(), "Enter Time", Toast.LENGTH_LONG).show();
                        timePickerDialog.show();
                    }
                });
                timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (is_Time_Set) {
                            TextView textView = view.findViewById(R.id.object);
                            update(textView.getText().toString(),hour[0],day[0]);
                            alarmManager(textView.getText().toString(),i1,h[0],m[0],d[0],month[0]);
                        }
                    }
                });
                datePickerDialog.show();
            }
            });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int i1, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Todo.this);
                builder.setTitle("Delete").setMessage("Do you want to delete it.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextView textView = view.findViewById(R.id.object);
                            deleteItem(textView.getText().toString(),i1);
                        }
                    });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
                return true;
                }
        });
    }

    void deleteItem(String task,int i1) {
        BackgroundTodo todo = new BackgroundTodo(this);
        todo.execute("delete_entry","","","",task);
        is_Item_deleted=true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager(task,i1,0,0,0,0);
        }

    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void alarmManager(String task,int item, int hour, int min, int day, int month) {
        Calendar calendar=Calendar.getInstance();
        int curDate=calendar.get(Calendar.DAY_OF_MONTH);
        int curHour=calendar.get(Calendar.HOUR_OF_DAY);
        int curMin=calendar.get(Calendar.MINUTE);
        int curSec=calendar.get(Calendar.SECOND);
        int curMonth=calendar.get(Calendar.MONTH);
        int curTime=(curMonth*2592000)+(curDate*86400)+(curHour*3600)+(curMin*60)+curSec;
        int setTime=(month*2592000)+(day*86400)+(hour*3600)+(min*60);
        int timeLeft=(setTime-curTime)*1000;
        Long l= (long) timeLeft;
        Long alert=new GregorianCalendar().getTimeInMillis()+l;

        AlarmManager alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,AlertReciever.class);
        if(!is_Item_deleted) {
            intent.putExtra("Work", "Do your work and delete from ToDo");
            intent.putExtra("Name", task);
            intent.putExtra("Id", item);
        }
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,item,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null&&!is_Item_deleted) {
            alarmManager.set(AlarmManager.RTC_WAKEUP,alert,pendingIntent);
        }
        else if (alarmManager != null&&is_Item_deleted) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void update(String task,String hour,String day) {
        BackgroundTodo todo = new BackgroundTodo(this);
        todo.execute("update_entry",task,day,hour);
        is_Time_Set=false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.add)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            final EditText editText=new EditText(this);
            builder.setMessage("Enter Task").setTitle("What is Your Task?");
            builder.setView(editText);
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Date c=Calendar.getInstance().getTime();
                    SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yy",Locale.getDefault());
                    Toast.makeText(getApplicationContext(),"Deadline set to current date",Toast.LENGTH_LONG).show();
                    BackgroundTodo todo = new BackgroundTodo(Todo.this);
                    todo.execute("insert_entry",editText.getText().toString(),sdf.format(c),"12:00");
                    textView.setVisibility(View.GONE);
                }
            });
            builder.show();
        }
        if(id==R.id.clear)
        {
            DatabaseHelper databaseHelper = new DatabaseHelper(Todo.this);
            long l = databaseHelper.getProfilesCount();
            for(int i=0;i<l;i++) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(this, AlertReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                }
            }
            BackgroundTodo todo = new BackgroundTodo(Todo.this);
            todo.execute("delete_all");
            textView.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}