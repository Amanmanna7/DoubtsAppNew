package com.example.doubtsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class student_dashboard extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView WelcomeText ;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        mAuth= FirebaseAuth.getInstance();
        String uid=mAuth.getUid();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        DatabaseReference StudProfile=reference.child("Students").child(uid);

        StudProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username= snapshot.child("Name").getValue().toString();
                setTitle(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        try{

            PagerAdapter pagerAdapter= new PagerAdapter(getSupportFragmentManager());
            if(pagerAdapter.getCount()==0) {

                pagerAdapter.addFragment(new seeRecentQuestions());
                pagerAdapter.addFragment(new postDoubts());
                pagerAdapter.addFragment(new connectToFaculty());
            }
            viewPager=findViewById(R.id.viewPager);
            viewPager.setAdapter(pagerAdapter);

            tabLayout=findViewById(R.id.tab);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setText("See Recent Questions");
            tabLayout.getTabAt(1).setText("Post Doubts");
            tabLayout.getTabAt(2).setText("Connect To Faculty");


        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

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
                Intent intent=new Intent(student_dashboard.this,WelcomeScreen.class);
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
        Intent intent=new Intent(student_dashboard.this,WelcomeScreen.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }




}