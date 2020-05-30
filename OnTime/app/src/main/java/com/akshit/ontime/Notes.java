package com.akshit.ontime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Notes extends AppCompatActivity {
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseReference;
    ImageView imageView;
    EditText editText,editText2;
    Uri imgUri;
    Button button,button1,button2;
    public static final String FB_STORAGE_PATH="image/";
    public static final String FB_DATABASE_PATH="image";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        mStorageRef= FirebaseStorage.getInstance().getReference();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);
        imageView=findViewById(R.id.image);
        editText=findViewById(R.id.text);
        button=findViewById(R.id.choose);
        button1=findViewById(R.id.upload);
        editText2=findViewById(R.id.path);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),1234);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgUri!=null)
                {
                    final String path[] = editText2.getText().toString().split(" ");
                    if(path.length!=4)
                    {
                        Toast.makeText(Notes.this,"Illegal Path",Toast.LENGTH_LONG).show();
                        return;
                    }
                    final ProgressDialog dialog=new ProgressDialog(Notes.this);
                    dialog.setTitle("Uploading");
                    dialog.show();
                    final StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + path[0]+"/"
                    +path[1]+"/"+path[2]+"/"+path[3]);
                    ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                 dialog.dismiss();
                            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    ImageUpload imageUpload=new ImageUpload(editText.getText().toString(),task.getResult().toString());
                                    String uploadId=mDatabaseReference.push().getKey();
                                    if (uploadId != null) {
                                        mDatabaseReference.child( path[0]+"/"
                                                +path[1]+"/"+path[2]+"/"+path[3]).child(uploadId).setValue(imageUpload);
                                    }
                                }
                            });
                            Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_LONG).show();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            dialog.setMessage("Uploaded "+(int)progress+"%");
                        }

                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Choose Image",Toast.LENGTH_LONG).show();
                }

            }
        });
        button2=findViewById(R.id.show);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ImageListActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1234&&resultCode==RESULT_OK && data!=null && data.getData()!=null){
            try{
            imgUri=data.getData();
            InputStream inputStream;
            inputStream = getContentResolver().openInputStream(imgUri);
            final Bitmap imageMap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(imageMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
        else {
            Toast.makeText(this,"Error importing Image",Toast.LENGTH_LONG).show();
        }
    }

}
