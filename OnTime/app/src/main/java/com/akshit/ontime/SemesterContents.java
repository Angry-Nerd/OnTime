package com.akshit.ontime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
