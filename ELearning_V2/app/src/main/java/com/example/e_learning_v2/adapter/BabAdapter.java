package com.example.e_learning_v2.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learning_v2.R;
import com.example.e_learning_v2.course_detail;
import com.example.e_learning_v2.course_media;
import com.example.e_learning_v2.model.Course;
import com.example.e_learning_v2.model.daftarBab;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BabAdapter extends RecyclerView.Adapter<BabAdapter.ViewHolder> {
    private List<daftarBab> daftarBabList;
    private Context context;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public BabAdapter(List<daftarBab> daftarBabList, Context context) {
        this.daftarBabList = daftarBabList;
        this.context = context;
    }

    @NonNull
    @Override
    public BabAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.activity_course_detail_items, parent, false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull BabAdapter.ViewHolder holder, int position) {

        final daftarBab data = daftarBabList.get(position);
        final String key = data.getKey();
        String bab = data.getBab();
        String desc = data.getDeskripsi();
        String video = data.getVideo();
        String pdf = data.getPdf();
        holder.contentChapter.setText(data.getBab());
        holder.contentDesc.setText(data.getDeskripsi());
        holder.contentNumber.setText(Integer.toString(position+1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, course_media.class);
                Bundle extras = new Bundle();
                extras.putString("Bab", bab);
                extras.putString("Desc", desc);
                extras.putString("Key", key);
                extras.putString("Video", video);
                extras.putString("Pdf", pdf);
                i.putExtras(extras);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return daftarBabList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView contentNumber, contentChapter, contentDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contentDesc = itemView.findViewById(R.id.content_desc);
            contentChapter = itemView.findViewById(R.id.content_chapter);
            contentNumber = itemView.findViewById(R.id.content_number);
        }
    }
}
