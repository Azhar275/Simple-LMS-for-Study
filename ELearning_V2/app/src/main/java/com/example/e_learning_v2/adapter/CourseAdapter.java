package com.example.e_learning_v2.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_learning_v2.R;
import com.example.e_learning_v2.course_detail;
import com.example.e_learning_v2.model.Course;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private List<Course> courseList;
    private Context context;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public CourseAdapter(List<Course> courseList, Context context) {
        this.courseList = courseList;
        this.context = context;
    }
    public void setFilteredList(ArrayList<Course> filteredList){
        this.courseList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.category_row_items, parent, false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {

        final Course data = courseList.get(position);
        final String key = data.getKey();
        final String course = data.getNama();
        holder.courseName.setText(data.getNama());
        holder.totalChapter.setText(Integer.toString(data.getJumlah_bab()));
        Glide.with(context).load(courseList.get(position).getLogo()).into(holder.courseImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, course_detail.class);
                Bundle extras = new Bundle();
                extras.putString("Key", key);
                extras.putString("Course", course);
                i.putExtras(extras);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView courseImage;
        TextView courseName, totalChapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.course_image);
            courseName = itemView.findViewById(R.id.course_name);
            totalChapter = itemView.findViewById(R.id.total_course);
        }
    }
}
