package com.example.e_learning_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class course_media extends AppCompatActivity {

    PDFView pdfView;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_media);
        Bundle extras = getIntent().getExtras();
        String bab = extras.getString("Bab");
        String desc = extras.getString("Desc");
        String video = extras.getString("Video");
        String pdfUrl = extras.getString("Pdf");
        TextView media_name = findViewById(R.id.media_name);
        TextView media_desc = findViewById(R.id.media_desc);
        back = findViewById(R.id.back_3);
        back.setOnClickListener(v -> {
            finish();
        });

        media_name.setText(bab);
        media_desc.setText(desc);
        Uri uri = Uri.parse(video);
        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoURI(uri);
        //videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video_sample);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        pdfView = findViewById(R.id.pdf_view);
//        pdfView.fromAsset("Tugas PD_22.pdf")
//                .enableSwipe(true)
//                .enableDoubletap(true)
//                .load();

        new RetrivePdfStream().execute(pdfUrl);


    }
    class RetrivePdfStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {

                // adding url
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                // if url connection response code is 200 means ok the execute
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            // if error return null
            catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        // Here load the pdf and dismiss the dialog box
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    }
}