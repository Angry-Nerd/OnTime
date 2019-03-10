package com.akshit.ontime;

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

public class Third extends AppCompatActivity {

//    Button DCLD,M3,DS,CA,OOPS;
//    ListView list;
//    ViewGroup.LayoutParams layoutParams;
//    String []strings={"Maths-3","Discrete Strucuture","Computer Architecture","Digital Circuit Logic Design","Object Oriented Programming"};
    RecyclerView list;
    ViewGroup.LayoutParams layoutParams;
    ArrayList<String> names=new ArrayList<>();
    ArrayList<Integer> images=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedSInstanceState) {
        super.onCreate(savedSInstanceState);
        setContentView(R.layout.activity_third);
        list=findViewById(R.id.recycler_third);
        names.add("Maths-3");
        names.add("Discrete Strucuture");
        names.add("Computer Architecture");
        names.add("Digital Circuit Logic Design");
        names.add("Object Oriented Programming");

        images.add(R.drawable.blur);
        images.add(R.drawable.blur2);
        images.add(R.drawable.blur3);
        images.add(R.drawable.blur4);
        images.add(R.drawable.blur5);


        list.setHasFixedSize(true);
        RecyclerAdapter recyclerAdapter=new RecyclerAdapter(this,names,images)
        {
            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final int i=position;
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(i==3)
                        {
                            Intent i1=new Intent(Third.this,SemesterContents.class);
                            i1.putExtra("Sub","Third,DCLD,Digital Circuit Logic Design");
                            startActivity(i1);
                        }
                        else if(i==0)
                        {
                            Intent i1=new Intent(Third.this,SemesterContents.class);
                            i1.putExtra("Sub","Third,Maths-3,Maths-3");
                            startActivity(i1);
                        }
                        else if(i==1)
                        {
                            Intent i1=new Intent(Third.this,SemesterContents.class);
                            i1.putExtra("Sub","Third,DS,Discrete Strucuture");
                            startActivity(i1);
                        }
                        else if(i==2)
                        {
                            Intent i1=new Intent(Third.this,SemesterContents.class);
                            i1.putExtra("Sub","Third,CA,Computer Architecture");
                            startActivity(i1);
                        }
                        else if(i==4)
                        {
                            Intent i1=new Intent(Third.this,SemesterContents.class);
                            i1.putExtra("Sub","Third,OOPS,Object Oriented Programming");
                            startActivity(i1);
                        }
                    }
                });
            }
        };

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(recyclerAdapter);



















//
//        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strings){
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
//                layoutParams = view.getLayoutParams();
//                t.setTextColor(getResources().getColor(R.color.app));
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
//                if(i==3)
//                {
//                    Intent i1=new Intent(Third.this,SemesterContents.class);
//                    i1.putExtra("Sub","Third,DCLD,Digital Circuit Logic Design");
//                    startActivity(i1);
//                }
//                if(i==0)
//                {
//                    Intent i1=new Intent(Third.this,SemesterContents.class);
//                    i1.putExtra("Sub","Third,Maths-3,Maths-3");
//                    startActivity(i1);
//                }
//                if(i==1)
//                {
//                    Intent i1=new Intent(Third.this,SemesterContents.class);
//                    i1.putExtra("Sub","Third,DS,Discrete Strucuture");
//                    startActivity(i1);
//                }
//                if(i==2)
//                {
//                    Intent i1=new Intent(Third.this,SemesterContents.class);
//                    i1.putExtra("Sub","Third,CA,Computer Architecture");
//                    startActivity(i1);
//                }if(i==4)
//                {
//                    Intent i1=new Intent(Third.this,SemesterContents.class);
//                    i1.putExtra("Sub","Third,OOPS,Object Oriented Programming");
//                    startActivity(i1);
//                }
//
//            }
//        });
////        M3=findViewById(R.id.M3);
////        DS=findViewById(R.id.DS);
////        CA=findViewById(R.id.CA);
////        DCLD=findViewById(R.id.DCLD);
////        OOPS=findViewById(R.id.OOPS);
////
////        M3.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent i=new Intent(Third.this,SemesterContents.class);
////                i.putExtra("Sub","Third,Maths-3");
////                startActivity(i);
////            }
////        });
////        OOPS.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent i=new Intent(Third.this,SemesterContents.class);
////                i.putExtra("Sub","Third,OOPS");
////                startActivity(i);
////            }
////        });
////        CA.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent i=new Intent(Third.this,SemesterContents.class);
////                i.putExtra("Sub","Third,CA");
////                startActivity(i);
////            }
////        });
////        DCLD.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent i=new Intent(Third.this,SemesterContents.class);
////                i.putExtra("Sub","Third,DCLD");
////                startActivity(i);
////            }
////        });
////        DS.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent i=new Intent(Third.this,SemesterContents.class);
////                i.putExtra("Sub","Third,DS");
////                startActivity(i);
////            }
////        });
    }
}
