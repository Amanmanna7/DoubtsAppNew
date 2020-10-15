package com.example.doubtsapp;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class student_Signup extends AppCompatActivity {

    private EditText edtStudUsernameSignup, edtStudPasswordSignup, edtStudEmailSignup;
    private Button btnStudLogin2, btnStudSignup2;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__signup);
        mAuth = FirebaseAuth.getInstance();
        try {
            edtStudUsernameSignup= findViewById(R.id.edtStudUsernameSignup);
            edtStudPasswordSignup = findViewById(R.id.edtStudPasswordSignup);
            edtStudEmailSignup = findViewById(R.id.edtStudEmailSignup);
            btnStudLogin2 = (Button) findViewById(R.id.btnStudLogin2);
            btnStudSignup2 = (Button) findViewById(R.id.btnStudSignup2);
            btnStudSignup2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.createUserWithEmailAndPassword(edtStudEmailSignup.getText().toString(),edtStudPasswordSignup.getText().toString()).addOnCompleteListener(student_Signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseDatabase.getInstance().getReference().child("Students").
                                        child(task.getResult().getUser().getUid()).child("Name").
                                        setValue(edtStudUsernameSignup.getText().toString());
                                Transition();
                                Toast.makeText(student_Signup.this,"Signup Successful",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(student_Signup.this,"Signup Failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            });

            btnStudLogin2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(student_Signup.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            //not now
        }
    }

    private void Transition(){

        Intent intent=new Intent(student_Signup.this,StudentUniversity.class);
        startActivity(intent);
    }
}