package com.akshit.ontime;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    CircleImageView imageView;
    ImageView image;
    TextView name,dob,address,number,coins;
    ProgressBar progressBar;
    Button edit;
    FirebaseStorage storage;
    StorageReference reference;
    public static int coinsCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=findViewById(R.id.profileName);
        dob=findViewById(R.id.profileDOB);
        progressBar=findViewById(R.id.progressBar);
        imageView=findViewById(R.id.imageProfile);
        address=findViewById(R.id.profileAddress);
        number=findViewById(R.id.profileNumber);
        coins=findViewById(R.id.coins);
        storage=FirebaseStorage.getInstance();
        reference=storage.getReference().child("images/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        edit=findViewById(R.id.edit);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(Profile.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_popup);
                WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width=WindowManager.LayoutParams.MATCH_PARENT;
                lp.height=WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes(lp);
                image=dialog.findViewById(R.id.dummy);
                image.setImageDrawable(imageView.getDrawable());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("image");
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                Picasso.with(Profile.this).load(dataSnapshot.getValue().toString()).networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        Picasso.with(Profile.this).load(dataSnapshot.getValue().toString()).into(imageView);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference = reference.getParent().child("Name");
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reference = reference.getParent().child("DOB");
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dob.setText(dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reference = reference.getParent().child("Address");
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                address.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reference = reference.getParent().child("Number");
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                number.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reference = reference.getParent().child("Coins");
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                coins.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Profile.this,StudentInfo.class);
                startActivity(i);
                finish();
            }
        });

    }
    static void updateCoinsCount(final int coin)
    {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Coins");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reference.setValue(Integer.parseInt(dataSnapshot.getValue().toString())+coin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        coinsCount+=coin;

    }

    static void getCoinsCount()
    {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Coins");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               coinsCount=Integer.parseInt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public static boolean checkCount() {
        getCoinsCount();
        return coinsCount >= 5;
    }
}
