package com.akshit.ontime;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class PracticalList extends AppCompatActivity {

    RecyclerView list,list2;
    ArrayList<String> practicalDate;
    ArrayList<String> practicalAim;
    ArrayList<Integer> imageDate;
    ArrayList<Integer> imageAim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_list);
        Intent i=getIntent();
        String a=i.getStringExtra("Subject");
        final String a1[]=a.split(",");
        list=findViewById(R.id.recycler_aim_of_practical);
        list2=findViewById(R.id.recycler_date_of_practical);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Semsester").child(a1[0]).child("Subjects").child(a1[1])
                .child("Practicals").child("Aim");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                practicalAim=new ArrayList<>();
                imageAim=new ArrayList<>();
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    practicalAim.add(ds.getValue().toString());
                }
                addAimImages();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        databaseReference = databaseReference.getParent().child("Date");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                practicalDate=new ArrayList<>();
                imageDate=new ArrayList<>();
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    practicalDate.add(ds.getValue().toString());

                }
                addDateImages();
                showList(a1);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private void addAimImages() {
        for(int i=0;i<practicalAim.size();i++)
            imageAim.add(R.drawable.blur5);
    }
    private  void addDateImages()
    {
        for(int i=0;i<practicalDate.size();i++)
            imageDate.add(R.drawable.blur4);
    }
    private void showList(final String name[]) {

        list.setLayoutManager(new LinearLayoutManager(this));
        list2.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new RecyclerAdapter(PracticalList.this,practicalAim,imageAim){
            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
                super.onBindViewHolder(holder, position);
                final int i = position;
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isNetworkAvailable()) {
                            Intent intent = new Intent(PracticalList.this, ImageListActivity.class);
                            intent.putExtra("Subject", "Practicals,"+name[0] + "," + name[1] + "," + (i + 1));
                            startActivity(intent);
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

            }});
        list2.setAdapter(new RecyclerAdapter(PracticalList.this,practicalDate,imageDate));
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