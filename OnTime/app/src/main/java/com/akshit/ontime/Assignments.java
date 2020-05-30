package com.akshit.ontime;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Assignments extends AppCompatActivity {

//    private RewardedVideoAd mAd;
    private RecyclerView list;
    private InterstitialAd mInterstitialAd;
    private ArrayList<String> assignmentDate;
    private ArrayList<Integer> imageDate;
    private String name[];
    long returnTime=0,currentTime=0;
    static boolean isOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        Intent i=getIntent();
        String a=i.getStringExtra("Subject");
        String a1[]=a.split(",");
        MobileAds.initialize(this,"ca-app-pub-3444270384996610~5657514381");
        //ca-app-pub-3444270384996610~5657514381
        //Test:ca-app-pub-3940256099942544~3347511713
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3444270384996610/8720222612");
        //ca-app-pub-3444270384996610/8720222612
        //Test:ca-app-pub-3940256099942544/1033173712
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
                super.onAdClosed();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                returnTime = System.currentTimeMillis();
                isOpened = true;
            }

            @Override
            public void onAdLeftApplication() {
                currentTime = System.currentTimeMillis();
                Toast.makeText(Assignments.this,"Clicked",Toast.LENGTH_LONG).show();
                super.onAdLeftApplication();
            }
        });
        name=a1;
        list=findViewById(R.id.recycler_date);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Semsester").child(a1[0]).child("Subjects").child(a1[1])
                .child("Assignments").child("Date");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assignmentDate=new ArrayList<>();
                imageDate=new ArrayList<>();
                for(DataSnapshot ds:dataSnapshot.getChildren())
                    assignmentDate.add(ds.getValue().toString());
                addDateImages();
                showList();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void loadAd()
    {
        if(mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        else
            Toast.makeText(this,"Ad is not loaded yet",Toast.LENGTH_LONG).show();
    }

    private void addDateImages() {
        for(int i=0;i<assignmentDate.size();i++)
            imageDate.add(R.drawable.blur5);
    }
    private void showList() {

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new RecyclerAdapter(Assignments.this,assignmentDate,imageDate){
            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
                super.onBindViewHolder(holder, position); 
                final int i=position;
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Profile.checkCount()) {
                            if (isNetworkAvailable()) {
//                                Toast.makeText(getApplicationContext(),"Innn",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Assignments.this, ImageListActivity.class);
                                intent.putExtra("Subject", "assignments,"+name[0] + "," + name[1] + "," + (i + 1));
                                startActivity(intent);
                                Profile.updateCoinsCount(-5);
                            }
                            else
                            {
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
                        else
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Assignments.this);
                            builder.setMessage("Watch a video").setTitle("Less Coins");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    mInterstitialAd.show();
                                    loadAd();

                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_LONG).show();
                                }
                            });
                            builder.show();
                        }

                    }
                });
            }
        });
    }


    @Override
    protected void onResume() {

        if(returnTime-currentTime>=20000 && returnTime-currentTime<=10000000000L) {
            Toast.makeText(this,"You got 20 coins",Toast.LENGTH_LONG).show();
            Profile.updateCoinsCount(20);
            currentTime=0;
            returnTime=0;
        }
        else if(isOpened)
            Toast.makeText(this,"You got no coins ",Toast.LENGTH_LONG).show();

        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
