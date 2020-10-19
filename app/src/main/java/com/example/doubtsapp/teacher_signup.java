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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class teacher_signup extends AppCompatActivity {

    private EditText edtTeachUsernameSignup, edtTeachPasswordSignup, edtTeachEmailSignup,edtTeachSubject,edtTeachBranch,edtTeachUniversity;
    private Button btnTeachLogin2, btnTeachSignup2;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_signup);
        mAuth=FirebaseAuth.getInstance();
        try {
            btnTeachLogin2 = (Button) findViewById(R.id.btnTeachLogin2);
            btnTeachSignup2 = (Button) findViewById(R.id.btnTeachSignup2);
            edtTeachUsernameSignup= findViewById(R.id.edtTeachUsernameSignup);
            edtTeachPasswordSignup = findViewById(R.id.edtTeachPasswordSignup);
            edtTeachEmailSignup =findViewById(R.id.edtTeachEmailSignup);
            edtTeachBranch=findViewById(R.id.edtTeachBranch);
            edtTeachSubject=findViewById(R.id.edtTeachSubject);
            edtTeachUniversity=findViewById(R.id.edtTeachUniversity);
            btnTeachSignup2.setOnClickListener(new View.OnClickListener() {



                @Override
                public void onClick(View v) {

                    String name,branch,subject,university;

                    name=edtTeachUsernameSignup.getText().toString();
                    branch=edtTeachBranch.getText().toString();
                    subject=edtTeachSubject.getText().toString();
                    university=edtTeachUniversity.getText().toString();




                    mAuth.createUserWithEmailAndPassword(edtTeachEmailSignup.getText().toString(),edtTeachPasswordSignup.getText().toString()).addOnCompleteListener(teacher_signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                HashMap<String,Object> map=new HashMap<>();
                                map.put("Name",name);
                                map.put("Branch",branch);
                                map.put("Subject",subject);
                                map.put("University",university);

                                DatabaseReference reference=FirebaseDatabase.getInstance().getReference()
                                        .child("Teachers").child(task.getResult().getUser().getUid());

                                reference.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(),"Updated Data",Toast.LENGTH_LONG).show();
                                    }
                                });



                                Transition();
                                Toast.makeText(teacher_signup.this,"Signup Successful",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(teacher_signup.this,"Signup Failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });






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

    private void Transition() {

        Intent intent = new Intent(getApplicationContext(),TeacherDashboard.class);
        startActivity(intent);


    }
}