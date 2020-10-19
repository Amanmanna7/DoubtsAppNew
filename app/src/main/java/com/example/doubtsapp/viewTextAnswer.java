package com.example.doubtsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class viewTextAnswer extends AppCompatActivity {

    RecyclerView recViewTextAns;
    ansAdapter adapterans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text_answer);
        recViewTextAns=(RecyclerView) findViewById(R.id.recViewTextAns);
        recViewTextAns.setLayoutManager(new LinearLayoutManager(this));
        String qid = getIntent().getStringExtra("RID");

        FirebaseRecyclerOptions<modelAns> options1= new FirebaseRecyclerOptions.Builder<modelAns>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Answer").child("DBMS").child(qid).child("TextAnswer"), modelAns.class)
                .build();
        adapterans=new ansAdapter(options1);
        recViewTextAns.setAdapter(adapterans);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterans.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterans.stopListening();
    }
}