package com.example.firstapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText RegEmail, RegPassword;
    private Button RegBtn;
    private TextView RegQues;
    private FirebaseAuth mAuth;

    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);;
        setContentView(R.layout.activity_registration);

        toolbar = findViewById(R.id.RegistrationToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration");

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        RegEmail = findViewById(R.id.RegistrationEmail);
        RegPassword = findViewById(R.id.RegistrationPassword);
        RegBtn = findViewById(R.id.RegistrationButton);
        RegQues = findViewById(R.id.RegistrationPageQuestion);

        RegQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = RegEmail.getText().toString().trim();
                String password = RegPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    RegEmail.setError("Email is Incorrect");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    RegPassword.setError("Password Required");
                    return;
                }else{
                    loader.setMessage("Registration in Progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    mAuth.createUserWithEmailAndPassword(email, password).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {

                                        Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                        loader.dismiss();
                                    }else{
                                        String error = task.getException().toString();
                                        Toast.makeText(RegistrationActivity.this, "Registration failled" + error, Toast.LENGTH_SHORT).show();
                                        loader.dismiss();
                                    }
                                }
                            });
                }

            }
        });
    }
}