package com.akshit.ontime;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Hisaab extends AppCompatActivity {
    Toolbar toolbar;
    List<Item> arrayList;
    ItemAdapter arrayAdapter;
    ItemAdapter arrayAdapter1;
    TextView textView;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hisaab);
        toolbar = findViewById(R.id.toolba1);
        setSupportActionBar(toolbar);
        textView=findViewById(R.id.noHisaab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences("MyInstance", Context.MODE_PRIVATE);
        String array = sharedPreferences.getString("Hisaab", "");
        String date=sharedPreferences.getString("Amount","");
        listView = findViewById(R.id.list11);
        if(!array.equals(""))
        {
            textView.setVisibility(View.GONE);
            String[] ar = array.split(",");
            String[] date1=date.split(",");
            arrayList=new ArrayList<>();

            for(int i=0;i<ar.length;i++)
                arrayList.add(new Item(ar[i],date1[i]));

            arrayAdapter1 = new ItemAdapter(getApplicationContext(), R.layout.custom_list2, arrayList) {
            };
            listView.setAdapter(arrayAdapter1);
        }
        else {
            textView.setText(R.string.no_entry);
            textView.setVisibility(View.VISIBLE);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i1, long l){
                AlertDialog.Builder builder1=new AlertDialog.Builder(Hisaab.this);
                final EditText editText1=new EditText(Hisaab.this);
                builder1.setMessage("Enter Amount").setTitle("Enter amount to be taken:");
                builder1.setView(editText1);
                builder1.setPositiveButton("Change Amount", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyInstance", Context.MODE_PRIVATE);
                        String pr=sharedPreferences.getString("Hisaab","");
                        String daterr=sharedPreferences.getString("Amount","");
                        String []dates=daterr.split(",");
                        String [] pr1=pr.split(",");
                        dates[i1]=editText1.getText().toString();
                        List<Item> list=new ArrayList<Item>();
                        for(int z=0;z<dates.length;z++)
                            list.add(new Item(pr1[z],dates[z]));
                        StringBuilder sbd=new StringBuilder();
                        for(int j=0;j<dates.length;j++)
                            if(j<pr1.length-1)
                                sbd.append(dates[j]).append(",");
                            else
                                sbd.append(dates[j]);
                        SharedPreferences sp=getSharedPreferences("MyInstance",Context.MODE_PRIVATE);
                        SharedPreferences.Editor spe=sp.edit();
                        spe.putString("Amount",sbd.toString());
                        spe.commit();

                        ItemAdapter itemAdapter=new ItemAdapter(getApplicationContext(),R.layout.custom_list2,list);
                        listView.setAdapter(itemAdapter);
                    }
                });
                builder1.show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i1, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Hisaab.this);
                builder.setTitle("Delete").setMessage("Do you want to delete it.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyInstance", Context.MODE_PRIVATE);
                        String array = sharedPreferences.getString("Hisaab", "");
                        String date=sharedPreferences.getString("Amount","");
                        String[] pr = array.split(",");
                        String [] date1=date.split(",");

                        int z=pr.length,z1=date1.length;
                        if(z==1)
                            textView.setVisibility(View.VISIBLE);
                        List<Item> arrayList1 = new ArrayList<Item>();
                        List<String> prt=new ArrayList<String>();
                        List<String> datet=new ArrayList<String >();
                        for(int j=0;j<pr.length;j++)
                        {
                            if(j!=i1) {
                                prt.add(pr[j]);
                                datet.add("10");
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
                        spe.putString("Hisaab",sbt.toString());
                        spe.putString("Amount",sbd.toString());
                        spe.commit();
                        ItemAdapter arrayAdapter2=new ItemAdapter(getApplicationContext(),R.layout.custom_list2,arrayList1);
                        listView.setAdapter(arrayAdapter2);
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
            builder.setMessage("What is his/her Name?").setTitle("Choose");
            builder.setView(editText);
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences sharedPreferences=getSharedPreferences("MyInstance", Context.MODE_PRIVATE);
                    String array=sharedPreferences.getString("Hisaab","");
                    String date=sharedPreferences.getString("Amount","");
                    String[] pr;
                    String[] date1;
                    Toast.makeText(getApplicationContext(),"Initially amount is set to 0",Toast.LENGTH_LONG).show();
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
                    arrayList.add(new Item(editText.getText().toString(),"0"));
                    if(array.equals("")) {
                        array = editText.getText().toString();
                        date="0";
                    }
                    else {
                        array = array + "," + editText.getText().toString();
                        date=date+","+"0";
                    }
                    SharedPreferences sp=getSharedPreferences("MyInstance",Context.MODE_PRIVATE);
                    SharedPreferences.Editor spe=sp.edit();
                    spe.putString("Hisaab",array);
                    spe.putString("Amount",date);
                    spe.commit();

                    arrayAdapter=new ItemAdapter(getApplicationContext(),R.layout.custom_list2,arrayList);
                    listView.setAdapter(arrayAdapter);
                    textView.setVisibility(View.GONE);

                }
            });
            builder.show();
        }
        if(id==R.id.clear)
        {
            SharedPreferences sp=getSharedPreferences("MyInstance",Context.MODE_PRIVATE);
            String s=sp.getString("Hisaab","");
            if(s.equals(""))
                Toast.makeText(getApplicationContext(),"It's Clear",Toast.LENGTH_LONG).show();
            else {
                SharedPreferences.Editor spe = sp.edit();
                spe.putString("Hisaab", "");
                spe.putString("Amount", "");
                spe.commit();
                List<Item> list = new ArrayList<Item>();
                arrayAdapter = new ItemAdapter(getApplicationContext(), R.layout.custom_list2, list);
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
