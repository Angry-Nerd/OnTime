package com.akshit.ontime;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Fifth extends AppCompatActivity {

    RecyclerView list;
    ViewGroup.LayoutParams layoutParams;
    ArrayList<String> names=new ArrayList<>();
    ArrayList<Integer> images=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);

        list=findViewById(R.id.recycler_fifth);
        names.add("Computer Networks-2");
        names.add("CPI");
        names.add("DAA");
        names.add("CG");
        names.add("RD");

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
                            Intent i1=new Intent(Fifth.this,SemesterContents.class);
                            i1.putExtra("Sub","Fifth,CN-2,CN-2");
                            startActivity(i1);
                        }
                        else if(i==1)
                        {
                            Intent i1=new Intent(Fifth.this,SemesterContents.class);
                            i1.putExtra("Sub","Fifth,CPI,CPI");
                            startActivity(i1);
                        }
                        else if(i==2)
                        {
                            Intent i1=new Intent(Fifth.this,SemesterContents.class);
                            i1.putExtra("Sub","Fifth,DAA,DAA");
                            startActivity(i1);
                        }
                        else if(i==3)
                        {
                            Intent i1=new Intent(Fifth.this,SemesterContents.class);
                            i1.putExtra("Sub","Fifth,CG,CG");
                            startActivity(i1);
                        }
                        else if(i==4)
                        {
                            Intent i1=new Intent(Fifth.this,SemesterContents.class);
                            i1.putExtra("Sub","Fifth,RD,RD");
                            startActivity(i1);
                        }
                    }
                });

            }
        };

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(recyclerAdapter);
    }
}
