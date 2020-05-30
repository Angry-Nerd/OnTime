package com.akshit.ontime;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageListActivity extends AppCompatActivity {
    List<String> imgList;
    GridView lv;
    ImageListAdapter adapter;
    ProgressDialog progressDialog;
    Dialog dialog;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        Assignments.isOpened = false;
        Intent i=getIntent();
        imgList=new ArrayList<>();
        lv=findViewById(R.id.gridView);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        dialog=new Dialog(this);
        String extra=i.getStringExtra("Subject");
        String arr[]=extra.split(",");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_popup);
        WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        image=dialog.findViewById(R.id.dummy);
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("image").child(arr[0])
                .child(arr[1]).child(arr[2]).child(arr[3]);
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot!=null) {
                            for (DataSnapshot dsInner : dataSnapshot.getChildren()) {
                                ImageUpload img = dsInner.getValue(ImageUpload.class);
                                imgList.add(img.getUrl());
                            }
                        }
                        else
                            Toast.makeText(ImageListActivity.this,"No elemenst yet",Toast.LENGTH_LONG).show();
                    adapter = new ImageListAdapter(ImageListActivity.this, imgList);
                    lv.setAdapter(adapter);
                    progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView image1=view.findViewById(R.id.image);
                image.setImageDrawable(image1.getDrawable());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }
}
