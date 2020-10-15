package com.example.doubtsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class StudentYear extends AppCompatActivity {

    Button v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_student_year);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        v= findViewById(R.id.fifth);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Students").child(uid)
                        .child("Semester").setValue(5);

                Intent intent= new Intent(StudentYear.this, student_dashboard.class);
                startActivity(intent);
            }
        });
    }
}