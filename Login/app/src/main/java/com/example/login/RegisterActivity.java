package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText reg_email,reg_password,reg_cfpassword;
    private Button register, gtlog;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reg_email = findViewById(R.id.reg_email);
        reg_password = findViewById(R.id.reg_password);
        reg_cfpassword = findViewById(R.id.reg_cfpassword);
        register = findViewById(R.id.register);
        gtlog = findViewById(R.id.gtlog);
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        gtlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });


    }

    private void PerforAuth() {
        String email = reg_email.getText().toString();
        String password = reg_password.getText().toString();
        String confirmPassword = reg_cfpassword.getText().toString();

        if(password.isEmpty() || password.length()<6){
            showError(reg_password,"Can dien lai password");
        }else if(email.isEmpty() || !email.contains("@")){
            showError(reg_email,"Nhap lai email");
        }else if(confirmPassword.isEmpty() || !confirmPassword.equals(password)){
            showError(reg_cfpassword,"mat khau khong khop");
        }else{

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "tao tai khoan thanh cong", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}