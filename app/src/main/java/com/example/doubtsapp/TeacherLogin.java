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

public class TeacherLogin extends AppCompatActivity {

    private EditText edtTeachPasswordLogin,edtTeachEmailLogin;
    Button btnTeachLogin1,btnTeachSign;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        try {
            btnTeachSign = (Button) findViewById(R.id.btnTeachSignup);
            edtTeachPasswordLogin = findViewById(R.id.edtTeachPasswordLogin);
            edtTeachEmailLogin = findViewById(R.id.edtTeachEmailLogin);
            btnTeachLogin1 = (Button) findViewById(R.id.btnTeachLogin);
            mAuth=FirebaseAuth.getInstance();

            btnTeachLogin1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signInWithEmailAndPassword(edtTeachEmailLogin.getText().toString(),edtTeachPasswordLogin.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                TransitionToDashboard();
                                Toast.makeText(TeacherLogin.this,"Logged In",Toast.LENGTH_LONG).show();

                            }
                            else{
                                Toast.makeText(TeacherLogin.this,"Log In failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }
            });

            btnTeachSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TeacherLogin.this, teacher_signup.class);
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Toast.makeText(TeacherLogin.this,e.getMessage(),Toast.LENGTH_LONG);
        }
    }

    private void TransitionToDashboard() {
        Intent intent = new Intent(getApplicationContext(),TeacherDashboard.class);
        startActivity(intent);

    }
}
