package com.example.doubtsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class seeRecentActivity extends AppCompatActivity {

    RecyclerView recView;
    myAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_recent);

        try{

            recView=(RecyclerView)findViewById(R.id.recView);
            recView.setLayoutManager(new LinearLayoutManager(this));
            FirebaseRecyclerOptions<model> options =
                    new FirebaseRecyclerOptions.Builder<model>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Questions").child("DBMS"), model.class)
                            .build();


            adapter=new myAdapter(options);
            recView.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        try{

            adapter.startListening();
        }catch (Exception e){
            Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try{

            adapter.stopListening();
        }catch (Exception e){
            Toast.makeText(this,"3",Toast.LENGTH_LONG).show();
        }
    }
}