package com.akshit.ontime;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class SyllabusList extends AppCompatActivity {
//
//    RecyclerView list,list2;
//    ArrayList<String> booksList;
//    ArrayList<String> syllabusList;
//    ArrayList<Integer> booksImages;
//    ArrayList<Integer> syllabusImages;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_syllabus_list);
//        Intent i=getIntent();
//        String a=i.getStringExtra("Subject");
//        String a1[]=a.split(",");
//        list=findViewById(R.id.recycler_books);
//        list2=findViewById(R.id.recycler_syllabus);
//        Toast.makeText(this,"Retrieving...",Toast.LENGTH_LONG).show();
//        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Semsester").child(a1[0]).child("Subjects").child(a1[1])
//                .child("Syllabus").child("Books");
//        databaseReference.keepSynced(true);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                booksList=new ArrayList<>();
//                booksImages=new ArrayList<>();
//                    for(DataSnapshot ds:dataSnapshot.getChildren())
//                    {
//                        booksList.add(ds.getValue().toString());
//                    }
//                    addBooksImages();
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//        databaseReference = databaseReference.getParent().child("Syllabus");
//        databaseReference.keepSynced(true);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //Toast.makeText(SyllabusList.this,"In",//Toast.LENGTH_LONG).show();
//                syllabusList=new ArrayList<>();
//                syllabusImages=new ArrayList<>();
//                for(DataSnapshot ds:dataSnapshot.getChildren())
//                {
//                    //Toast.makeText(SyllabusList.this,"IZn",//Toast.LENGTH_LONG).show();
//                    syllabusList.add(ds.getValue().toString());
//
//                }
//                addSyllabusImages();
//                showList();
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//
//    }
//
//    private void addBooksImages() {
//        for(int i=0;i<booksList.size();i++)
//            booksImages.add(R.drawable.blur5);
//    }
//    private  void addSyllabusImages()
//    {
//        for(int i=0;i<syllabusList.size();i++)
//            syllabusImages.add(R.drawable.blur4);
//    }
//    private void showList() {
//
////        list.setHasFixedSize(true);
////        list2.setHasFixedSize(true);
//        list.setLayoutManager(new LinearLayoutManager(this));
//        list2.setLayoutManager(new LinearLayoutManager(this));
//        list.setAdapter(new RecyclerAdapter(SyllabusList.this,booksList,booksImages));
//        list2.setAdapter(new RecyclerAdapter(SyllabusList.this,syllabusList,syllabusImages));
//
//    }
}
