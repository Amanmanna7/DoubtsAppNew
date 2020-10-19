package com.example.doubtsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class viewImageAnswer extends AppCompatActivity {

    private RecyclerView recViewImg;
    imgAnsAdapter adapterImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image_answer);

        recViewImg=(RecyclerView)findViewById(R.id.recViewImgAns);
        recViewImg.setLayoutManager(new LinearLayoutManager(this));

        String imguid=getIntent().getStringExtra("imgUID");

        FirebaseRecyclerOptions<modelImgAns> optionsImg=new FirebaseRecyclerOptions.Builder<modelImgAns>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Answer").child("DBMS").child(imguid).child("ImageAnswer")
                        ,modelImgAns.class).build();

        adapterImg= new imgAnsAdapter(optionsImg);
        recViewImg.setAdapter(adapterImg);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterImg.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterImg.stopListening();
    }
}