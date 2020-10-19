package com.example.doubtsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class StudentUniversity extends AppCompatActivity {

    private Button aktu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_student_university);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        final String uid= user.getUid();
        aktu= findViewById(R.id.aktu);
        aktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Students").child(uid)
                        .child("University").setValue("A.K.T.U.");
                Intent intent= new Intent(StudentUniversity.this, StudentBranch.class);
                startActivity(intent);
            }
        });
    }
}

