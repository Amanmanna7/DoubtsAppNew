package com.example.doubtsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherDashboard extends AppCompatActivity {

    ViewPager viewPagerTeach;
    TabLayout tabLayoutTeach;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private String username;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard2);

        mAuth= FirebaseAuth.getInstance();
        uid=mAuth.getUid();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        DatabaseReference StudProfile=reference.child("Teachers").child(uid);

        StudProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username= snapshot.child("Name").getValue().toString();
                setTitle(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        teachPagerAdapter pagerAdapterTeach=new teachPagerAdapter(getSupportFragmentManager());
        if(pagerAdapterTeach.getCount()==0){
            pagerAdapterTeach.addFragment(new teachChatRoom());
            pagerAdapterTeach.addFragment(new teachSeeRecent());
            pagerAdapterTeach.addFragment(new connectToStudent());
        }

        viewPagerTeach=findViewById(R.id.viewPagerTeach);
        viewPagerTeach.setAdapter(pagerAdapterTeach);
        tabLayoutTeach=findViewById(R.id.tabTeach);
        tabLayoutTeach.setupWithViewPager(viewPagerTeach);
        tabLayoutTeach.getTabAt(0).setText("Create Chat Room");
        tabLayoutTeach.getTabAt(1).setText("See Recent Questions");
        tabLayoutTeach.getTabAt(2).setText("Connect To Students");

        db=FirebaseDatabase.getInstance();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.logoutItem:
                mAuth.signOut();
                Intent intent=new Intent(TeacherDashboard.this,WelcomeScreen.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAuth.signOut();
        Intent intent=new Intent(TeacherDashboard.this,WelcomeScreen.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }






}