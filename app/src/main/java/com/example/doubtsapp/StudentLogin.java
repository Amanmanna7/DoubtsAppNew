package com.example.doubtsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentLogin extends AppCompatActivity {


    private EditText edtStudPasswordLogin,edtStudEmailLogin;
    private Button btnStudLogin1,btnStudSignup1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_student_login);
        mAuth = FirebaseAuth.getInstance();
        try {
            edtStudPasswordLogin = findViewById(R.id.edtStudPasswordLogin);
            edtStudEmailLogin =  findViewById(R.id.edtStudEmailLogin);
            btnStudLogin1 = (Button) findViewById(R.id.btnStudLogin);
            btnStudSignup1 = (Button) findViewById(R.id.btnStudSignup);

            btnStudLogin1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mAuth.signInWithEmailAndPassword(edtStudEmailLogin.getText().toString(),edtStudPasswordLogin.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                TransitionToDashboard();
                                Toast.makeText(StudentLogin.this,"Logged In",Toast.LENGTH_LONG).show();

                            }
                            else{
                                Toast.makeText(StudentLogin.this,"Log In failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }
            });

            btnStudSignup1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StudentLogin.this, student_Signup.class);
                    startActivity(intent);
                }
            });
        }
        catch (Exception e){
            Toast.makeText(StudentLogin.this,e.getMessage(),Toast.LENGTH_LONG).show();
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

    private void TransitionToDashboard(){
        Intent intent=new Intent(StudentLogin.this,student_dashboard.class);
        startActivity(intent);

    }
}

