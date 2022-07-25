package com.example.e_learning_v2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class task_upload extends AppCompatActivity {
    TextView title;
    FirebaseStorage storage;
    Button uploadFile;
    ImageView selectFile, back;
    ProgressDialog progressDialog;
    Uri pdfUri;
    TextView information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_upload);
        Bundle extras = getIntent().getExtras();
        String course = extras.getString("Course");
        title = findViewById(R.id.task_title);
        title.setText("Upload Tugas "+course);

        storage = FirebaseStorage.getInstance();
        selectFile = findViewById(R.id.selectFile);
        uploadFile = findViewById(R.id.uploadFile);
        information = findViewById(R.id.information);
        back = findViewById(R.id.back_4);
        back.setOnClickListener(v -> {
            finish();
        });
        selectFile.setOnClickListener(v ->{
            selectPdf();
        });
        uploadFile.setOnClickListener(v ->{
            if (pdfUri != null){
                uploadPdf(course);
            }
            else {
                Toast.makeText(task_upload.this, "Select a file", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void uploadPdf(String course) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Sedang mengunggah file...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String filename = "Tugas_"+course+"_"+System.currentTimeMillis();
        StorageReference storageReference = storage.getReference();

        storageReference.child("Tugas").child(course).child(filename).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        Toast.makeText(task_upload.this, "File berhasil diunggah", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(task_upload.this, "Upload gagal, pastikan aplikasi diperbolehkan mengakses penyimpanan", Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                int currentProgress = (int) (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);


            }
        });
    }
    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 86 && resultCode == RESULT_OK && data!= null){
            pdfUri = data.getData();
            String fileName = pdfUri.getLastPathSegment().substring(pdfUri.getLastPathSegment().lastIndexOf("/")+1);
            information.setText("File :"+fileName);
        }else {
            Toast.makeText(task_upload.this, "Silahkan memilih pdf dahulu", Toast.LENGTH_SHORT).show();
        }
    }
}