package com.akshit.ontime;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class StudentInfo extends AppCompatActivity {

    EditText name,number,dob,address;
    Button save;
    DatabaseReference reference;
    Calendar calendar = Calendar.getInstance();
    String date;
    ImageView imageView;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri uriImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        name=findViewById(R.id.name);
        number=findViewById(R.id.number);
        save=findViewById(R.id.save);
        dob=findViewById(R.id.dob);
        address=findViewById(R.id.address);
        imageView=findViewById(R.id.profileImage);
        storage = FirebaseStorage.getInstance();
        fillInfo();
        storageReference = storage.getReference();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 1);
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.DATE,dayOfMonth);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.YEAR,year);
                        date=String.valueOf(dayOfMonth)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year);
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(StudentInfo.this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dob.setText(date);
                    }
                });

            }
        });
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        reference= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("Name");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(number.getText().toString()) ||
                        TextUtils.isEmpty(dob.getText().toString()))
                {
                    Toast.makeText(StudentInfo.this,"All Fields must be filled",Toast.LENGTH_LONG).show();
                }

                else
                {
                    if(uriImage!=null) {
                    reference.setValue(name.getText().toString());
                    reference.getParent().child("Number").setValue(number.getText().toString());
                    reference.getParent().child("DOB").setValue(dob.getText().toString());
                    reference.getParent().child("Address").setValue(address.getText().toString());
                    final ProgressDialog progressDialog = new ProgressDialog(StudentInfo.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                            imageView.setDrawingCacheEnabled(true);
                            imageView.buildDrawingCache();
                            Bitmap bmp = imageView.getDrawingCache();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                        byte[] array = byteArrayOutputStream.toByteArray();

                        final StorageReference ref = storageReference.child("images/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                        UploadTask uploadTask = ref.putBytes(array);
                        uploadTask
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        progressDialog.dismiss();
                                        ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                reference.getParent().child("image").setValue(task.getResult().toString());
                                            }
                                        });
                                        Toast.makeText(StudentInfo.this, "Saved", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(StudentInfo.this, Profile.class));
                                            finish();
                                        Toast.makeText(StudentInfo.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(StudentInfo.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                                .getTotalByteCount());
                                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                    }
                                });
                    }
                    else
                    {
                        Toast.makeText(StudentInfo.this,"Choose Image First",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void fillInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("image");
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                Picasso.with(StudentInfo.this).load(dataSnapshot.getValue().toString()).networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        //progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        Picasso.with(StudentInfo.this).load(dataSnapshot.getValue().toString()).into(imageView);
                       // progressBar.setVisibility(View.GONE);
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1 && resultCode==RESULT_OK && data!=null) {

            try{
            uriImage = data.getData();
            InputStream inputStream;
            inputStream = getContentResolver().openInputStream(uriImage);
            Bitmap imageMap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(imageMap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(this,"Error in importing",Toast.LENGTH_LONG).show();
        }
    }
}

