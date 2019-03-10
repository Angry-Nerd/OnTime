package com.akshit.ontime;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Fourth extends AppCompatActivity {

    RecyclerView list;
    ViewGroup.LayoutParams layoutParams;
    ArrayList<String> names=new ArrayList<>();
    ArrayList<Integer> images=new ArrayList<>();
//    Button DS,OS,MALP,SP,CN;
//    ListView list;
//    ViewGroup.LayoutParams layoutParams;
//    String []strings={"Discrete Structures","Operating System","Microprocessor Aseembly Language Programming","Computer Networks","System Programming"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        list=findViewById(R.id.recycler_fourth);
        names.add("Discrete Structures");
        names.add("Operating System");
        names.add("Microprocessor Aseembly Language Programming");
        names.add("Computer Networks");
        names.add("System Programming");

        images.add(R.drawable.blur);
        images.add(R.drawable.blur2);
        images.add(R.drawable.blur3);
        images.add(R.drawable.blur4);
        images.add(R.drawable.blur5);


        list.setHasFixedSize(true);
        RecyclerAdapter recyclerAdapter=new RecyclerAdapter(this,names,images){

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final int i=position;
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(i==0)
                        {
                            Intent i1=new Intent(Fourth.this,SemesterContents.class);
                            i1.putExtra("Sub","Fourth,DS,Discrete Structures");
                            startActivity(i1);
                        }
                        else if(i==1)
                        {
                            Intent i1=new Intent(Fourth.this,SemesterContents.class);
                            i1.putExtra("Sub","Fourth,OS,Operating System");
                            startActivity(i1);
                        }
                        else if(i==2)
                        {
                            Intent i1=new Intent(Fourth.this,SemesterContents.class);
                            i1.putExtra("Sub","Fourth,MALP,MALP");
                            startActivity(i1);
                        }
                        else if(i==3)
                        {
                            Intent i1=new Intent(Fourth.this,SemesterContents.class);
                            i1.putExtra("Sub","Fourth,CN,Computer Networks");
                            startActivity(i1);
                        }
                        else if(i==4)
                        {
                            Intent i1=new Intent(Fourth.this,SemesterContents.class);
                            i1.putExtra("Sub","Fourth,SP,System Programming");
                            startActivity(i1);
                        }
                    }
                });

            }
        };

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(recyclerAdapter);























//        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strings){
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public View getView(int p, View v, ViewGroup a)
//            {
//                View view = super.getView(p,v,a);
//                TextView t=(TextView)view.findViewById(android.R.id.text1);
//                t.setTextSize(27);
////                t.setPaddingRelative(0,20,0,20);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                    t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                }
//
//                layoutParams = view.getLayoutParams();
//
//                //Define your height here.
//                layoutParams.height = 260   ;
//
//                view.setLayoutParams(layoutParams);
//                return view;
//            }
//        };
//
//        list.setAdapter(arrayAdapter);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if(i==0)
//                {
//                    Intent i1=new Intent(Fourth.this,SemesterContents.class);
//                    i1.putExtra("Sub","Fourth,DS,Discrete Structures");
//                    startActivity(i1);
//                }
//                if(i==1)
//                {
//                    Intent i1=new Intent(Fourth.this,SemesterContents.class);
//                    i1.putExtra("Sub","Fourth,OS,Operating System");
//                    startActivity(i1);
//                }
//                if(i==2)
//                {
//                    Intent i1=new Intent(Fourth.this,SemesterContents.class);
//                    i1.putExtra("Sub","Fourth,MALP,MALP");
//                    startActivity(i1);
//                }
//                if(i==3)
//                {
//                    Intent i1=new Intent(Fourth.this,SemesterContents.class);
//                    i1.putExtra("Sub","Fourth,CN,Computer Networks");
//                    startActivity(i1);
//                }if(i==4)
//                {
//                    Intent i1=new Intent(Fourth.this,SemesterContents.class);
//                    i1.putExtra("Sub","Fourth,SP,System Programming");
//                    startActivity(i1);
//                }
//
//            }
//        });
//        DS=findViewById(R.id.DS);
//        OS=findViewById(R.id.OS);
//        SP=findViewById(R.id.SP);
//        MALP=findViewById(R.id.MALP);
//        CN=findViewById(R.id.CN);
//        DS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(Fourth.this,SemesterContents.class);
//                i.putExtra("Sub","Fourth,DS");
//                startActivity(i);
//            }
//        });
//        CN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(Fourth.this,SemesterContents.class);
//                i.putExtra("Sub","Fourth,CN");
//                startActivity(i);
//            }
//        });
//        OS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(Fourth.this,SemesterContents.class);
//                i.putExtra("Sub","Fourth,OS");
//                startActivity(i);
//            }
//        });
//        MALP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(Fourth.this,SemesterContents.class);
//                i.putExtra("Sub","Fourth,MALP");
//                startActivity(i);
//            }
//        });
//        SP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(Fourth.this,SemesterContents.class);
//                i.putExtra("Sub","Fourth,SP");
//                startActivity(i);
//            }
//        });

    }


    }

