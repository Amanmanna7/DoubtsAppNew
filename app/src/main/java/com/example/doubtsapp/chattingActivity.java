package com.example.doubtsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class chattingActivity extends AppCompatActivity {

    ListView DiscussionTopics;
    ArrayList<String> listOfDiscussion= new ArrayList<>();
    private ArrayAdapter arrayAdapter;
    private String UserName;
    private int check;

    private DatabaseReference dbr=FirebaseDatabase.getInstance().getReference().getRoot().child("DiscussionTopics");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        try{

            DiscussionTopics=(ListView) findViewById(R.id.DiscussionTopics);
            arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,listOfDiscussion);
            DiscussionTopics.setAdapter(arrayAdapter);

            check= getIntent().getIntExtra("check",0);

            if(check==0){
                getUsername();
            }
            else {
                getTeachUsername();
            }



            dbr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Set<String> set = new HashSet<>();

                    if(snapshot.exists()) {
                        int i = 0;
                        for(DataSnapshot d : snapshot.getChildren()) {
                            set.add(d.getKey());
                            i++;
                        }
                    }

                    arrayAdapter.clear();
                    arrayAdapter.addAll(set);
                    arrayAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            DiscussionTopics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getApplicationContext(),discussionActivity.class);
                    i.putExtra("selected_topic",((TextView)view).getText().toString());
                    i.putExtra("user_name",UserName);
                    i.putExtra("check",check);
                    startActivity(i);
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void getTeachUsername() {

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("Teachers");
        String uid = user.getUid();
        DatabaseReference myRef= Ref.child(uid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tempName = snapshot.child("Name").getValue().toString();
                String strArr[]=tempName.split(" ");
                UserName=strArr[0];
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getUsername(){

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("Students");
        String uid = user.getUid();
        DatabaseReference myRef= Ref.child(uid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tempName = snapshot.child("Name").getValue().toString();
                String strArr[]=tempName.split(" ");
                UserName=strArr[0];
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}