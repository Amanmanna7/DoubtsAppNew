package com.example.doubtsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TeacherLogin extends AppCompatActivity {

    private EditText edtTeachPasswordLogin,edtTeachEmailLogin;
    Button btnTeachLogin1,btnTeachSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        try {
            btnTeachSign = (Button) findViewById(R.id.btnTeachSignup);
            edtTeachPasswordLogin = findViewById(R.id.edtTeachPasswordLogin);
            edtTeachEmailLogin = findViewById(R.id.edtTeachEmailLogin);
            btnTeachLogin1 = (Button) findViewById(R.id.btnTeachLogin);

            btnTeachLogin1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TeacherLogin.this, TeacherUniversity.class);
                    startActivity(intent);
                }
            });

            btnTeachSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TeacherLogin.this, teacher_signup.class);
                    startActivity(intent);
                }
            });
        } catch (Exception e){
            Toast.makeText(TeacherLogin.this,e.getMessage(),Toast.LENGTH_LONG);
        }
    }
}
