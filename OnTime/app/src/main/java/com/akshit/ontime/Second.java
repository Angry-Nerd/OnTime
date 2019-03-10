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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Second extends AppCompatActivity {
    RecyclerView list;
    ViewGroup.LayoutParams layoutParams;
    ArrayList<String> names=new ArrayList<>();
    ArrayList<Integer> images=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        list=findViewById(R.id.recycler_second);
        names.add("Chemistry");
        names.add("Maths-2");
        names.add("Engineering Drawing");
        names.add("Environmental Studies");
        names.add("Fundamentals of Computer Programming and Information Technology");

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
                            Intent i1=new Intent(Second.this,SemesterContents.class);
                            i1.putExtra("Sub","Second,Chemistry,Chemistry");
                            startActivity(i1);
                        }
                        else if(i==1)
                        {
                            Intent i1=new Intent(Second.this,SemesterContents.class);
                            i1.putExtra("Sub","Second,Maths-2,Maths-2");
                            startActivity(i1);
                        }
                        else if(i==2)
                        {
                            Intent i1=new Intent(Second.this,SemesterContents.class);
                            i1.putExtra("Sub","Second,ED,Engineering Drawing");
                            startActivity(i1);
                        }
                        else if(i==3)
                        {
                            Intent i1=new Intent(Second.this,SemesterContents.class);
                            i1.putExtra("Sub","Second,EVS,Environmental Studies");
                            startActivity(i1);
                        }
                        else if(i==4)
                        {
                            Intent i1=new Intent(Second.this,SemesterContents.class);
                            i1.putExtra("Sub","Second,FCPIT,FCPIT");
                            startActivity(i1);
                        }
                    }
                });
            }
        };

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(recyclerAdapter);















//        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strings){
//            @Override
//            public View getView(int p, View v, ViewGroup a)
//            {
//                View view = super.getView(p,v,a);
//                TextView t=(TextView)view.findViewById(android.R.id.text1);
//                t.setTextSize(27);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                    t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                }
//                t.setTextColor(getResources().getColor(R.color.app));
//                layoutParams = view.getLayoutParams();
//
//                //Define your height here.
//                layoutParams.height = 260   ;
//                if(p==4)
//                    layoutParams.height=290;
//                view.setLayoutParams(layoutParams);
//                return view;
//            }
//        };

//        list.setAdapter(arrayAdapter);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if(i==0)
//                {
//                    Intent i1=new Intent(Second.this,SemesterContents.class);
//                    i1.putExtra("Sub","Second,Chemistry,Chemistry");
//                    startActivity(i1);
//                }
//                if(i==1)
//                {
//                    Intent i1=new Intent(Second.this,SemesterContents.class);
//                    i1.putExtra("Sub","Second,Maths-2,Maths-2");
//                    startActivity(i1);
//                }
//                if(i==2)
//                {
//                    Intent i1=new Intent(Second.this,SemesterContents.class);
//                    i1.putExtra("Sub","Second,ED,Engineering Drawing");
//                    startActivity(i1);
//                }
//                if(i==3)
//                {
//                    Intent i1=new Intent(Second.this,SemesterContents.class);
//                    i1.putExtra("Sub","Second,EVS,Environmental Studies");
//                    startActivity(i1);
//                }if(i==4)
//                {
//                    Intent i1=new Intent(Second.this,SemesterContents.class);
//                    i1.putExtra("Sub","Second,FCPIT,FCPIT");
//                    startActivity(i1);
//                }
//
//            }
//        });
//        m2=findViewById(R.id.M2);
//        ED=findViewById(R.id.ED);
//        evs=findViewById(R.id.evs);
//        chem=findViewById(R.id.chem);
//        FCPIT=findViewById(R.id.FCPIT);
//
//        m2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(Second.this,SemesterContents.class);
//                i.putExtra("Sub","Second,Maths-2");
//                startActivity(i);
//            }
//        });
//        FCPIT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(Second.this,SemesterContents.class);
//                i.putExtra("Sub","Second,FCPIT");
//                startActivity(i);
//            }
//        });
//        evs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(Second.this,SemesterContents.class);
//                i.putExtra("Sub","Second,EVS");
//                startActivity(i);
//            }
//        });
//        chem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(Second.this,SemesterContents.class);
//                i.putExtra("Sub","Second,Chemistry");
//                startActivity(i);
//            }
//        });
//        ED.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(Second.this,SemesterContents.class);
//                i.putExtra("Sub","Second,ED");
//                startActivity(i);
//            }
//        });

    }

    private void addInfo() {

    }
}
