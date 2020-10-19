package com.example.doubtsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class seeRecentActivity extends AppCompatActivity {

    RecyclerView recView;
    private myAdapter adapter;
    private int check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_recent);

        try{

            recView=(RecyclerView)findViewById(R.id.recView);
            recView.setLayoutManager(new LinearLayoutManager(this));
            check=getIntent().getIntExtra("check",1);

            FirebaseRecyclerOptions<model> options =
                    new FirebaseRecyclerOptions.Builder<model>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("Questions").child("DBMS"), model.class)
                    .build();


            adapter=new myAdapter(options);
            recView.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();
        }

        adapter.setOnClickListener(new myAdapter.ClickListener() {
            @Override
            public void onItemClick(DataSnapshot snapshot, int position) {
                String id=snapshot.getKey();
                Intent intent =new Intent(getApplicationContext(),selectedQuestionActivity.class);
                intent.putExtra("check",check);
                intent.putExtra("ID",id);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        try{

            adapter.startListening();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try{

            adapter.stopListening();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}