package com.example.doubtsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class teacher_signup extends AppCompatActivity {

    private EditText edtTeachUsernameSignup, edtTeachPasswordSignup, edtTeachEmailSignup;
    private Button btnTeachLogin2, btnTeachSignup2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_signup);
        try {
            edtTeachUsernameSignup= findViewById(R.id.edtTeachUsernameSignup);
            edtTeachPasswordSignup = findViewById(R.id.edtTeachPasswordSignup);
            edtTeachEmailSignup = findViewById(R.id.edtTeachEmailSignup);
            btnTeachLogin2 = (Button) findViewById(R.id.btnTeachLogin2);
            btnTeachSignup2 = (Button) findViewById(R.id.btnTeachSignup2);
            btnTeachSignup2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent= new Intent(teacher_signup.this,TeacherUniversity.class);
                    startActivity(intent);

                }
            });

            btnTeachLogin2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(teacher_signup.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}