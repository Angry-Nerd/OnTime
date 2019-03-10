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
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class First extends AppCompatActivity {

//    Button Physics,HVPE,M1,CS,BEEE;
//    ListView list;
//    ViewGroup.LayoutParams layoutParams;

    String []strings={"Physics","Human Values and Professional Ethics","Maths-1","Commutative English","BEEE"};
    RecyclerView list;
    ViewGroup.LayoutParams layoutParams;
    ArrayList<String> names=new ArrayList<>();
    ArrayList<Integer> images=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        list=findViewById(R.id.recycler_first);
        names.add("Physics");
        names.add("HVPE");
        names.add("Maths-1");
        names.add("CE");
        names.add("BEEE");

        images.add(R.drawable.blur);
        images.add(R.drawable.blur2);
        images.add(R.drawable.blur3);
        images.add(R.drawable.blur4);
        images.add(R.drawable.blur5);


        list.setHasFixedSize(true);
        RecyclerAdapter recyclerAdapter=new RecyclerAdapter(this,names,images){
            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int positionn) {
                super.onBindViewHolder(holder, positionn);
                final int position=positionn;
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            if(position==0)
                            {
                                Intent i1=new Intent(First.this,SemesterContents.class);
                                i1.putExtra("Sub","First,Physics,Physics");
                                startActivity(i1);
                            }
                            else if(position==1)
                            {
                                Intent i1=new Intent(First.this,SemesterContents.class);
                                i1.putExtra("Sub","First,HVPE,Human Values and Professional Ethics");
                                startActivity(i1);
                            }
                            else if(position==2)
                            {
                                Intent i1=new Intent(First.this,SemesterContents.class);
                                i1.putExtra("Sub","First,M1,Maths-1");
                                startActivity(i1);
                            }
                            else if(position==3)
                            {
                                Intent i1=new Intent(First.this,SemesterContents.class);
                                i1.putExtra("Sub","First,CS,Commutative English");
                                startActivity(i1);
                            }
                            else if(position==4)
                            {
                                Intent i1=new Intent(First.this,SemesterContents.class);
                                i1.putExtra("Sub","First,BEEE,BEEE");
                                startActivity(i1);
                            }
                    }
                });
            }
        };

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(recyclerAdapter);
























//        toolbar.setTitleTextColor(Color.BLACK);
//        toolbar.setLogo(getResources().getDrawable(R.drawable.first));
//        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strings){
//            @NonNull
//            @Override
//            public View getView(int p, View v, ViewGroup a)
//            {
//
//                View view = super.getView(p,v,a);
//                TextView t=(TextView)view.findViewById(android.R.id.text1);
//                t.setTextSize(27);
////                t.setPaddingRelative(0,20,0,20);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                    t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                }
//
//                t.setTextColor(getResources().getColor(R.color.app));
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
//                    Intent i1=new Intent(First.this,SemesterContents.class);
//                    i1.putExtra("Sub","First,Physics,Physics");
//                    startActivity(i1);
//                }
//                if(i==1)
//                {
//                    Intent i1=new Intent(First.this,SemesterContents.class);
//                    i1.putExtra("Sub","First,HVPE,Human Values and Professional Ethics");
//                    startActivity(i1);
//                }
//                if(i==2)
//                {
//                    Intent i1=new Intent(First.this,SemesterContents.class);
//                    i1.putExtra("Sub","First,M1,Maths-1");
//                    startActivity(i1);
//                }
//                if(i==3)
//                {
//                    Intent i1=new Intent(First.this,SemesterContents.class);
//                    i1.putExtra("Sub","First,CS,Commutative English");
//                    startActivity(i1);
//                }if(i==4)
//                {
//                    Intent i1=new Intent(First.this,SemesterContents.class);
//                    i1.putExtra("Sub","First,BEEE,BEEE");
//                    startActivity(i1);
//                }
//
//            }
//        });
//        Physics=findViewById(R.id.Physics);
//        HVPE=findViewById(R.id.HVPE);
//        CS=findViewById(R.id.CS);
//        M1=findViewById(R.id.M1);
//        BEEE=findViewById(R.id.BEEE);
//        Physics.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(First.this,SemesterContents.class);
//                i.putExtra("Sub","First,Physics");
//                startActivity(i);
//            }
//        });
//        BEEE.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(First.this,SemesterContents.class);
//                i.putExtra("Sub","First,BEEE");
//                startActivity(i);
//            }
//        });
//        HVPE.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(First.this,SemesterContents.class);
//                i.putExtra("Sub","First,HVPE");
//                startActivity(i);
//            }
//        });
//        M1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(First.this,SemesterContents.class);
//                i.putExtra("Sub","First,M1");
//                startActivity(i);
//            }
//        });
//        CS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(First.this,SemesterContents.class);
//                i.putExtra("Sub","First,CS");
//                startActivity(i);
//            }
//        });
    }
}
