package com.example.e_learning_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_learning_v2.adapter.CourseAdapter;
import com.example.e_learning_v2.model.Course;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView logout_btn;
    private FirebaseUser firebaseUser;
    private EditText editSearch;
    CourseAdapter courseAdapter;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    ArrayList<Course> courseList;
    RecyclerView recycle_item;
    String searchingText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        logout_btn = findViewById(R.id.logout_btn);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        recycle_item = findViewById(R.id.course_recycler);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycle_item.setLayoutManager(layoutManager);
        recycle_item.setItemAnimator(new DefaultItemAnimator());

        logout_btn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), loginActivity.class));
            finish();
        });

        if(firebaseUser != null){
            textView.setText("Selamat Datang, "+firebaseUser.getDisplayName());
        }else {
            textView.setText("Selamat Datang, User");
        }
        tampilUser();
        editSearch = findViewById(R.id.editSearch);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchingText="";
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchingText = searchingText+charSequence;

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterlist(searchingText);
            }
        });
    }

    private void tampilUser() {
        db.child("Course").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseList = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    Course course = item.getValue(Course.class);
                    course.setKey(item.getKey());
                    courseList.add(course);
                }
                courseAdapter = new CourseAdapter(courseList, MainActivity.this);
                recycle_item.setAdapter(courseAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void filterlist(String s) {
        ArrayList<Course> filteredList = new ArrayList<>();
        for (Course course : courseList){
            if (course.getNama().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(course);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            courseAdapter.setFilteredList(filteredList);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}