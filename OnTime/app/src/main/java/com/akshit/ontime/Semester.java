package com.akshit.ontime;

import android.content.Context;

import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import Aks#it B2ns2L
import org.w3c.dom.Text;

public class Semester extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Coins");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Profile.coinsCount = Integer.parseInt(dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id)
                {
                    case R.id.hisaab:
                    {
                        startActivity(new Intent(getApplicationContext(),Hisaab.class));
                        break;
                    }
                    case R.id.logout: {
                        if (checkNet()) {
                            final Snackbar snackbar= Snackbar.make(mDrawerLayout,"No Internet Connection",Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            snackbar.dismiss();
                                        }
                                    }).show();
                            return false;
                        }
                        else {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                            break;
                        }
                    }
                    case R.id.Todo:
                    {
                        startActivity(new Intent(getApplicationContext(),Todo.class));
                        break;
                    }
                    case R.id.fun:
                    {
                        startActivity(new Intent(getApplicationContext(),TicTacToe.class));
                        break;
                    }
                    case R.id.timeTable:
                    {
                        startActivity(new Intent(getApplicationContext(),TimeTable.class));
                        break;
                    }
                    case R.id.profile:
                    {
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        break;
                    }

                }
                mDrawerLayout.closeDrawer(Gravity.START,true);
                return true;
            }

        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
        @Override
        public void onClick(View view) {

    }
        private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
        }
        return activeNetworkInfo != null;
    }
    public boolean checkNet()
    {
        return !isNetworkAvailable();
    }

    public void firstSem(View view) {
        startActivity(new Intent(getApplicationContext(), First.class));
    }

    public void secondSem(View view) {
        startActivity(new Intent(getApplicationContext(),Second.class));
    }

    public void thirdSem(View view) {
        startActivity(new Intent(getApplicationContext(), Third.class));
    }

    public void fourthSem(View view) {
        startActivity(new Intent(getApplicationContext(), Fourth.class));
    }

    public void fifthSem(View view) {
        startActivity(new Intent(getApplicationContext(), Fifth.class));
    }

    public void notes(View view) {
        startActivity(new Intent(this,Notes.class));
    }
}


