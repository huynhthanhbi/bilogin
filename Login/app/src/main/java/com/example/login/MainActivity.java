package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText txt_email, txt_password;
    private Button login, gtreg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        login = findViewById(R.id.login);
        gtreg = findViewById(R.id.gtreg);
        mAuth = FirebaseAuth.getInstance();

        gtreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckLogin();
            }
        });


    }
    private void CheckLogin(){
        String email = txt_email.getText().toString();
        String password = txt_password.getText().toString();

        if(password.isEmpty() || password.length()<6){
            showError(txt_password,"sai password");
        }else if(email.isEmpty() || !email.contains("@")){
            showError(txt_email,"sai email");
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,VaoLogin.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Dang nhap lai", Toast.LENGTH_SHORT).show();
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