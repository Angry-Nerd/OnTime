package com.akshit.ontime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.akshit.ontime.ui.HomeActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

public class TimeTable extends AppCompatActivity {
    private List<Item> arrayList;
    private ItemAdapter arrayAdapter;
    private TextView textView;
    static boolean is_Time_Set=false;
    private ListView listView;
    boolean is_Rearrange=false;
    Calendar calendar=Calendar.getInstance();
    private boolean delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        Toolbar toolbar = findViewById(R.id.toolba1);
        setSupportActionBar(toolbar);
        textView=findViewById(R.id.noTask);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyInstance", Context.MODE_PRIVATE);
        String array = sharedPreferences.getString("Event", "");
        String date=sharedPreferences.getString("Start","");
        listView = (ListView) findViewById(R.id.list);
        if(!array.equals(""))
        {
            textView.setVisibility(View.GONE);
            String[] ar = array.split(",");
            String[] date1=date.split(",");
            arrayList=new ArrayList<Item>();

            for(int i=0;i<ar.length;i++)
                arrayList.add(new Item(ar[i],date1[i]));

            ItemAdapter arrayAdapter1 = new ItemAdapter(getApplicationContext(), R.layout.custom_list_tt, arrayList) {
            };
            listView.setAdapter(arrayAdapter1)   ;
        }
        else
            textView.setVisibility(View.VISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i1, final long l){

                    final String[] hour = new String[1];
                    final int[] h = new int[1];
                    final int[] m = new int[1];
                    Toast.makeText(getApplicationContext(), "Enter Start Time", Toast.LENGTH_LONG).show();
                    TimePickerDialog.OnTimeSetListener dater = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int j1, int j2) {
                            hour[0] =Integer.toString(j1);
                            h[0] =j1;
                            hour[0]+=":"+Integer.toString(j2);
                            m[0] =j2;
                            is_Time_Set=true;
                        }
                        };

                    TimePickerDialog timePickerDialog = new TimePickerDialog(TimeTable.this, dater, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true);

                    timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if(is_Time_Set) {
                                Toast.makeText(TimeTable.this,hour[0],Toast.LENGTH_LONG).show();
                                callMe(hour[0], i1);
                                alarmManager(i1,h[0],m[0]);
                            }
                        }
                    });
                    timePickerDialog.show();
                }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i1, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TimeTable.this);
                builder.setTitle("Delete").setMessage("Do you want to delete it.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyInstance", Context.MODE_PRIVATE);
                        String array = sharedPreferences.getString("Event", "");
                        String date=sharedPreferences.getString("Start","");
                        String[] pr = array.split(",");
                        String [] date1=date.split(",");

                        int z=pr.length,z1=date1.length;
                        if(z==0)
                            textView.setVisibility(View.VISIBLE);
                        List<Item> arrayList1 = new ArrayList<Item>();
                        List<String> prt=new ArrayList<String>();
                        List<String> datet=new ArrayList<String>();
                        for(int j=0;j<pr.length;j++)
                        {
                            if(j!=i1) {
                                prt.add(pr[j]);
                                datet.add(date1[j]);
                                arrayList1.add(new Item(pr[j],date1[j]));
                            }
                        }
                        pr=prt.toArray(new String[z-1]);
                        date1=datet.toArray(new String[z1-1]);
                        StringBuilder sbt=new StringBuilder();
                        StringBuilder sbd=new StringBuilder();
                        for(int j=0;j<pr.length&&j<date1.length;j++)
                        {
                            if(j<pr.length-1) {
                                sbt.append(pr[j]).append(",");
                                sbd.append(date1[j]).append(",");
                            }
                            else
                            {
                                sbt.append(pr[j]);
                                sbd.append(date1[j]);
                            }
                        }
                        SharedPreferences sp=getSharedPreferences("MyInstance",Context.MODE_PRIVATE);
                        SharedPreferences.Editor spe=sp.edit();
                        spe.putString("Event",sbt.toString());
                        spe.putString("Start",sbd.toString());
                        spe.commit();
                        ItemAdapter arrayAdapter2=new ItemAdapter(getApplicationContext(),R.layout.custom_list_tt,arrayList1);
                        listView.setAdapter(arrayAdapter2);
                        delete =true;
                        alarmManager(i1,0,0);

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

    private void alarmManager(int i1, int h, int m) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyInstance", Context.MODE_PRIVATE);
        String pr=sharedPreferences.getString("Event","");
        String[] ar=pr.split(",");
        Calendar calendar=Calendar.getInstance();
//        int curDate=calendar.get(Calendar.DAY_OF_MONTH);
        int curHour=calendar.get(Calendar.HOUR_OF_DAY);
        int curMin=calendar.get(Calendar.MINUTE);
        int curSec=calendar.get(Calendar.SECOND);
        int curTime=curHour*3600+curMin*60+curSec;
        long setTime=h*3600+m*60;
        long l=(setTime-curTime)*1000;
        if(l<0&&!delete) {
            Toast.makeText(this,Long.toString(l),Toast.LENGTH_LONG).show();
            return;
        }
        Long alert=new GregorianCalendar().getTimeInMillis()+l;
        AlarmManager alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,AlertReciever.class);
        intent.putExtra("Work","Do your next Work");
        intent.putExtra("Name",ar[i1]);
        intent.putExtra("Id",i1+2828);
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(this);
        stackBuilder.addParentStack(HomeActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,i1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null&&!delete) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,alert,AlarmManager.INTERVAL_DAY,pendingIntent);
        }
        else if(delete){
                alarmManager.cancel(pendingIntent);
                delete =false;
        }

    }

    private void callMe(String string,int z) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyInstance", Context.MODE_PRIVATE);
        String daterr=sharedPreferences.getString("Start","");
        String pr=sharedPreferences.getString("Event","");
        String []dates=daterr.split(",");
        String [] pr1=pr.split(",");
        dates[z]=string;
        List<Item> list=new ArrayList<Item>();
        for(int i=0;i<dates.length;i++)
            list.add(new Item(pr1[i],dates[i]));
        StringBuilder sbd=new StringBuilder();
        for(int j=0;j<dates.length;j++)
        {
            if(j<pr1.length-1) {
                sbd.append(dates[j]).append(",");
            }
            else
            {
                sbd.append(dates[j]);
            }

        }
        SharedPreferences sp=getSharedPreferences("MyInstance",Context.MODE_PRIVATE);
        SharedPreferences.Editor spe=sp.edit();
        spe.putString("Start",sbd.toString());
        spe.commit();

        ItemAdapter itemAdapter=new ItemAdapter(getApplicationContext(),R.layout.custom_list_tt,list);
        listView.setAdapter(itemAdapter);
        is_Time_Set=false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.add)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            final EditText editText=new EditText(this);
            builder.setMessage("Enter Event").setTitle("What is Your Task?");
            builder.setView(editText);
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences sharedPreferences=getSharedPreferences("MyInstance", Context.MODE_PRIVATE);
                    String array=sharedPreferences.getString("Event","");
                    String date=sharedPreferences.getString("Start","");
                    String[] pr;
                    String[] date1;
                    Toast.makeText(getApplicationContext(),"Deadline set to current Start",Toast.LENGTH_LONG).show();
                    if(!array.equals("")) {
                        pr = array.split(",");
                        date1=date.split(",");
                        arrayList = new ArrayList<Item>();
                        for(int k=0;k<pr.length&&k<date1.length;k++)
                            arrayList.add(new Item(pr[k],date1[k]));
                    }
                    else
                    {
                        arrayList=new ArrayList<Item>();
                    }
                    arrayList.add(new Item(editText.getText().toString(),"12:00"));
                    if(array.equals("")) {
                        array = editText.getText().toString();
                        date="12:00";
                    }
                    else {
                        array = array + "," + editText.getText().toString();
                        date=date+","+"12:00";
                    }
                    SharedPreferences sp=getSharedPreferences("MyInstance",Context.MODE_PRIVATE);
                    SharedPreferences.Editor spe=sp.edit();
                    spe.putString("Event",array);
                    spe.putString("Start",date);
                    spe.commit();

                    arrayAdapter=new ItemAdapter(getApplicationContext(),R.layout.custom_list_tt,arrayList)
                    {
                    };
                    listView.setAdapter(arrayAdapter);
                    textView.setVisibility(View.GONE);

                }
            });
            builder.show();
        }
        else if(id==R.id.clear)
        {
            for(int i=0;i<arrayList.size();i++) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(this, AlertReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                }
            }
            SharedPreferences sp=getSharedPreferences("MyInstance",Context.MODE_PRIVATE);
            String s=sp.getString("Event","");
            if(s.equals(""))
                Toast.makeText(getApplicationContext(),"It's Clear",Toast.LENGTH_LONG).show();
            else {
                SharedPreferences.Editor spe = sp.edit();
                spe.putString("Event", "");
                spe.putString("Start", "");
                spe.commit();
                List<Item> list = new ArrayList<Item>();
                arrayAdapter = new ItemAdapter(getApplicationContext(), R.layout.custom_list_tt, list);
                listView.setAdapter(arrayAdapter);
                textView.setVisibility(View.VISIBLE);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

