package com.example.doubtsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class postTextAnswer extends AppCompatActivity {

    private Button btnPostAnswer;
    private DatabaseReference reference, ref;
    private String Username;
    private EditText edtTextAnswer;
    private String uid;
    private int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_text_answer);
        try {

            btnPostAnswer = findViewById(R.id.btnPostT);
            String id = getIntent().getStringExtra("ID");
            edtTextAnswer = (EditText) findViewById(R.id.edtTextAnswer);
            reference = FirebaseDatabase.getInstance().getReference().child("Answer").child("DBMS").child(id).child("TextAnswer");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            uid = user.getUid();
            check = getIntent().getIntExtra("check", -1);

            getUserName();


            btnPostAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ans = edtTextAnswer.getText().toString();
                    if (ans != null) {

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("Answer", ans);
                        map.put("User", Username);

                        reference.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Posted Your Answer", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                            }
                        });


                    } else {
                        Toast.makeText(getApplicationContext(), "Enter the text first", Toast.LENGTH_LONG).show();
                    }

                }
            });

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error here", Toast.LENGTH_SHORT).show();
        }


    }

    private void getUserName() {


        if (check == 0) {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Students").child(uid);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Username = snapshot.child("Name").getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else if (check == 1) {


            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Teachers").child(uid);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Username = snapshot.child("Name").getValue().toString();
                    Username="T-"+Username;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }
}