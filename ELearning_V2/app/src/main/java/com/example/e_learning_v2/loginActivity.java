package com.example.e_learning_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    private TextView skip_btn, reg_btn;
    private EditText editEmail, editPassword;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPasswordLogin);
        btnLogin = findViewById(R.id.login_btn);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(loginActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Tunggu sebentar");
        progressDialog.setCancelable(false);

        skip_btn = findViewById(R.id.skip);
        reg_btn = findViewById(R.id.reg_btn);
        skip_btn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
        reg_btn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        });
        btnLogin.setOnClickListener(v -> {
            if(editEmail.getText().length()>0 && editPassword.getText().length()>0){
                login(editEmail.getText().toString(), editPassword.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void login(String email, String password){
        //Login disini
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && task.getResult()!= null){
                    if(task.getResult().getUser()!=null){
                        reload();
                    }else {
                        Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Password salah atau akun tidak terdaftar", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    private void reload(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

}