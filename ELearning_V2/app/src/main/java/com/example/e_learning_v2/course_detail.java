package com.example.e_learning_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.e_learning_v2.adapter.BabAdapter;
import com.example.e_learning_v2.model.daftarBab;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class course_detail extends AppCompatActivity {
    BabAdapter babAdapter;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    ArrayList<daftarBab> daftarBabList;
    RecyclerView recycle_item;
    ImageView back, task_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Bundle extras = getIntent().getExtras();
        String key = extras.getString("Key");
        String course = extras.getString("Course");
        recycle_item = findViewById(R.id.course_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycle_item.setLayoutManager(layoutManager);
        recycle_item.setItemAnimator(new DefaultItemAnimator());
        back = findViewById(R.id.back_2);
        task_btn = findViewById(R.id.task_btn);
        back.setOnClickListener(v -> {
            finish();
        });
        task_btn.setOnClickListener(v -> {
            Intent i = new Intent(this, task_upload.class);
            Bundle extras1 = new Bundle();
            extras1.putString("Course", course);
            i.putExtras(extras1);
            startActivity(i);
        });



        tampilChapter(key);

    }

    private void tampilChapter(String key) {
        db.child("Course").child(key).child("daftarBab").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarBabList = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    daftarBab daftarbab = item.getValue(daftarBab.class);
                    daftarbab.setKey(item.getKey());
                    daftarBabList.add(daftarbab);
                }
                babAdapter = new BabAdapter(daftarBabList, course_detail.this);
                recycle_item.setAdapter(babAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}