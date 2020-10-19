package com.example.doubtsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class selectedQuestionActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private TextView edtSelQuesUser,edtSelQuesDescription;
    private ImageView imgQuesView;
    private Button btnPostTextAnswer,btnPostImageAnswer,btnViewTextAnswer,btnViewImageAnswer;
    private int check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_question);

        edtSelQuesUser=findViewById(R.id.selQuesUser);
        edtSelQuesDescription=findViewById(R.id.selQuesDescription);
        imgQuesView=findViewById(R.id.imgQuesView);
        btnPostImageAnswer=findViewById(R.id.btnPostImgAns);
        btnPostTextAnswer=findViewById(R.id.btnPostTextAns);
        btnViewImageAnswer=findViewById(R.id.btnViewImageAnswer);
        btnViewTextAnswer=findViewById(R.id.btnViewTextAnswer);

        check=getIntent().getIntExtra("check",-1);


        String id=getIntent().getStringExtra("ID");
        reference= FirebaseDatabase.getInstance().getReference().child("Questions").child("DBMS");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String user= snapshot.child(id).child("User").getValue().toString().trim();
                edtSelQuesUser.setText(user);

                String description=snapshot.child(id).child("description").getValue().toString().trim();
                edtSelQuesDescription.setText(description);

                String imagestr=snapshot.child(id).child("image").getValue().toString().trim();

                Glide.with(imgQuesView.getContext()).load(imagestr).into(imgQuesView);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnPostTextAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),postTextAnswer.class);
                intent.putExtra("check",check);
                intent.putExtra("ID",id);
                startActivity(intent);
            }
        });

        btnViewTextAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),viewTextAnswer.class);
                intent.putExtra("check",check);
                intent.putExtra("RID",id);
                startActivity(intent);
            }
        });

        btnPostImageAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),postImageAnswer.class);
                intent.putExtra("check",check);
                intent.putExtra("QUID",id);
                startActivity(intent);
            }
        });

        btnViewImageAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),viewImageAnswer.class);
                intent.putExtra("check",check);
                intent.putExtra("imgUID",id);
                startActivity(intent);
            }
        });




    }

}