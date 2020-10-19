package com.example.doubtsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.Edits;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class discussionActivity extends AppCompatActivity {

    Button btnSendMsg;
    EditText edtMsg;
    ListView lstDiscussion;
    ArrayList<String> lstConversation = new ArrayList<>();
    private ArrayAdapter arrayAdptr;
    private String UserName,SelectedTopics,user_msg_key;

    private DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        btnSendMsg= (Button) findViewById(R.id.btnSendMsg);
        edtMsg=(EditText) findViewById(R.id.edtMsg);
        lstDiscussion= (ListView) findViewById(R.id.lstConversation);
        arrayAdptr =new ArrayAdapter(this,android.R.layout.simple_list_item_1,lstConversation);
        lstDiscussion.setAdapter(arrayAdptr);

        UserName = getIntent().getExtras().get("user_name").toString();
        SelectedTopics= getIntent().getExtras().get("selected_topic").toString();
        setTitle(SelectedTopics);

        int check =getIntent().getIntExtra("check",-1);

        dbr= FirebaseDatabase.getInstance().getReference().child("DiscussionTopics").child(SelectedTopics);

        try {

            btnSendMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Map<String, Object> map= new HashMap<>();
                    user_msg_key = dbr.push().getKey();
                    dbr.updateChildren(map);

                    DatabaseReference dbr2= dbr.child(user_msg_key);
                    Map<String,Object> map2=new HashMap<>();
                    map2.put("msg",edtMsg.getText().toString());

                    if(check==0){
                        map2.put("user", UserName);
                    }
                    else if(check==1){
                        map2.put("user", "T-"+UserName);
                    }



                    dbr2.updateChildren(map2);


                }
            });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

        try {
            dbr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    updateConversation(snapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.join_video,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.joinVideoItem:
                Intent intent =new Intent(getApplicationContext(),VideoConference.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }


    public void updateConversation(DataSnapshot snapshot) {


        try {
            String user, msg, convo;
            for(DataSnapshot msgKey: snapshot.getChildren()){
                user=msgKey.child("user").getValue(String.class);
                msg=msgKey.child("msg").getValue(String.class);

                convo = user+":"+ msg;
                arrayAdptr.insert(convo,0);
                arrayAdptr.notifyDataSetChanged();


            }

        } catch (Exception e) {


        }


    }

}