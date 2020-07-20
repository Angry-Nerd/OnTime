package com.akshit.ontime;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.akshit.ontime.ui.HomeActivity;
import com.akshit.ontime.ui.auth.ResetPassword;
import com.akshit.ontime.ui.auth.SignUpActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

//    private FirebaseAuth auth;
//    private EditText inputEmail, inputPassword;
//    private ProgressBar progressBar;
//    private DatabaseReference databaseReference;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        FirebaseUser auth1 = FirebaseAuth.getInstance().getCurrentUser();
//        progressBar = findViewById(R.id.progressBar);
//
//        if (auth1 != null) {
//            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//            finish();
//        }
//        inputEmail = findViewById(R.id.email);
//        inputPassword = findViewById(R.id.password);
//        Button btnSignup = findViewById(R.id.btn_signup);
//        Button btnLogin = findViewById(R.id.btn_login);
//        Button btnReset = findViewById(R.id.btn_reset_password);
//        progressBar.setVisibility(View.INVISIBLE);
//        auth = FirebaseAuth.getInstance();
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        btnSignup.setOnClickListener(v -> {
//            inputEmail.setText("");
//            inputPassword.setText("");
//            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
//        });
//        btnReset.setOnClickListener(v -> {
//            inputEmail.setText("");
//            inputPassword.setText("");
//            startActivity(new Intent(MainActivity.this, ResetPassword.class));
//        });
//        btnLogin.setOnClickListener(v -> {
//            if (checkNet()) {
//                final Snackbar snackbar = Snackbar.make(v, "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
//                snackbar.setAction("Dismiss",
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                snackbar.dismiss();
//                            }
//                        }).show();
//            } else {
////                    startActivity(new Intent(MainActivity.this,Semester.class));
//                final String email = inputEmail.getText().toString();
//                final String password = inputPassword.getText().toString();
//
//                if (email.isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
//                    inputEmail.requestFocus();
//                    return;
//                }
//
//                if (password.isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
//                    inputPassword.requestFocus();
//                    return;
//                }
//
//                if (password.length() < 6) {
//                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
//                    inputPassword.requestFocus();
//                    return;
//                }
//                progressBar.setVisibility(View.VISIBLE);
//
//                //authenticate user
//                auth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(MainActivity.this, task -> {
//
//
//                            if (!task.isSuccessful()) {
//                                progressBar.setVisibility(View.GONE);
//                                Toast.makeText(MainActivity.this, " Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
//                            } else {
//                                if (checkIfEmailVerified()) {
//                                    databaseReference = databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Is_First_Time");
//                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            if (dataSnapshot.getValue().toString().equals("Yes")) {
//                                                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
//                                                Toast.makeText(getApplicationContext(), "We have rewarded you 20 coins for first ever login", Toast.LENGTH_LONG).show();
//                                                startActivity(i);
//                                                databaseReference.getParent().child("Coins").setValue("20");
//                                                databaseReference.setValue("No");
//                                            } else {
//                                                Intent i = new Intent(MainActivity.this, HomeActivity.class);
//                                                startActivity(i);
//                                            }
//                                            progressBar.setVisibility(View.GONE);
//                                            Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_LONG).show();
//                                            finish();
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//
//
//                                } else {
//                                    inputPassword.setText("");
//                                }
//                            }
//                        });
//            }
//        });
//
//    }
//
//
//    public boolean checkNet() {
//        return !isNetworkAvailable();
//    }
//
//    private boolean checkIfEmailVerified() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (user != null) {
//            if (user.isEmailVerified()) {
//                return true;
//            } else {
//
//                Toast.makeText(MainActivity.this, "Please verify your email...", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean isNetworkAvailable() {
//        // Using ConnectivityManager to check for Network Connection
//        ConnectivityManager connectivityManager = (ConnectivityManager) this
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = null;
//        if (connectivityManager != null) {
//            activeNetworkInfo = connectivityManager
//                    .getActiveNetworkInfo();
//        }
//        return activeNetworkInfo != null;
//    }

}
