package com.akshit.ontime;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class SemesterContents extends AppCompatActivity {


    Integer[] arr={R.drawable.book1,R.drawable.book2,R.drawable.book3};
    RecyclerView list;
    ArrayList<String> names=new ArrayList<>();
    ArrayList<Integer> images=new ArrayList<>();
    LinearLayout linearLayout;
    TextView subjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_contents);

        Intent intent=getIntent();
        final String ss=intent.getStringExtra("Sub");
        String [] array=ss.split(",");
        linearLayout=findViewById(R.id.linear_semester_contents);
        Random random=new Random();
        int i=random.nextInt(3);
        linearLayout.setBackground(getResources().getDrawable(arr[i]));
        list= findViewById(R.id.recycler_semester_contents);
        subjects=findViewById(R.id.subjects);
        subjects.setText(array[2]);

        names.add("Syllabus");
        names.add("Assignments");
        names.add("Practicals");


        images.add(R.drawable.blur4);
        images.add(R.drawable.blur2);
        images.add(R.drawable.blur3);

        list.setHasFixedSize(true);
        RecyclerAdapter recyclerAdapter=new RecyclerAdapter(this,names,images){
            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final int i=position;
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isNetworkAvailable()) {
                            if (i == 0) {
                                Intent i1 = new Intent(SemesterContents.this, SyllabusList.class);
                                i1.putExtra("Subject", ss);
                                startActivity(i1);
                            }
                            else if (i == 1) {
                                Intent i1 = new Intent(SemesterContents.this, Assignments.class);
                                i1.putExtra("Subject", ss);
                                startActivity(i1);
                            }
                            else if (i == 2) {
                                Intent i1 = new Intent(SemesterContents.this, PracticalList.class);
                                i1.putExtra("Subject", ss);
                                startActivity(i1);
                            }

                        } else {
                            final Snackbar snackbar = Snackbar.make(v, "No Internet Connection", Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            snackbar.dismiss();
                                        }
                                    }).show();
                        }
                    }
                });
            }
        };

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(recyclerAdapter);

    }
    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


}
