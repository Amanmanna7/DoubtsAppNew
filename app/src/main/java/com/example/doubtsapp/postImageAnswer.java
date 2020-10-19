package com.example.doubtsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class postImageAnswer extends AppCompatActivity {

    // views for button
    private Button btnSelect, btnUpload;

    // view for image view
    private ImageView imageView;

    // Uri indicates, where the image will be picked from
    private Uri imageuri=null;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    private static final int GALLERY_REQUEST=1;

    private EditText edtDescription;
    private FirebaseUser user;

    // instance for firebase storage and StorageReference
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String username;

    private String uid;

    private int check;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image_answer);
        try {
            ActionBar actionBar;
            actionBar = getSupportActionBar();
            ColorDrawable colorDrawable
                    = new ColorDrawable(
                    Color.parseColor("#0F9D58"));
            actionBar.setBackgroundDrawable(colorDrawable);

            // initialise views
            btnSelect = findViewById(R.id.btnChooseAns);
            btnUpload = findViewById(R.id.btnUploadAns);
            imageView = findViewById(R.id.imgPostViewAns);
            edtDescription= findViewById(R.id.edtAnsDescription);
            // get the Firebase  storage reference
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            user= FirebaseAuth.getInstance().getCurrentUser();
            uid=user.getUid();
            check=getIntent().getIntExtra("check",-1);

            if(check==0){

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Students").child(uid);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        username= snapshot.child("Name").getValue().toString();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else if(check==1){


                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Teachers").child(uid);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        username= snapshot.child("Name").getValue().toString();
                        username="T-"+username;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }






            // on pressing btnSelect SelectImage() is called
            btnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    SelectImage();
                }
            });

            // on pressing btnUpload uploadImage() is called
            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    uploadImage();
                }
            });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST&&resultCode==RESULT_OK){

            Uri imageUri=data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageuri = result.getUri();
                imageView.setImageURI(imageuri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    // Select Image method
    private void SelectImage() {

        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_REQUEST);

    }





    // UploadImage method
    private void uploadImage()
    {
        try{
            if (imageuri != null) {

                String description=edtDescription.getText().toString().trim();

                if(TextUtils.isEmpty(description)){
                    Toast.makeText(getApplicationContext(),"Enter the description",Toast.LENGTH_LONG).show();
                }

                else{
                    ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    String quid=getIntent().getStringExtra("QUID");

                    // Defining the child of storageReference
                    StorageReference ref = storageReference.child("Answer").child(imageuri.getLastPathSegment());

                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference().
                            child("Answer").child("DBMS").child(quid).child("ImageAnswer");




                    // adding listeners on upload
                    // or failure of image
                    ref.putFile(imageuri).addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            try {
                                                Uri downloadUri=uri;
                                                HashMap<String ,Object> map= new HashMap<>();
                                                map.put("description",description);
                                                map.put("User",username);
                                                map.put("image",downloadUri.toString().trim());

                                                reference.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        progressDialog.dismiss();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                                                        progressDialog.dismiss();
                                                    }
                                                });


                                                Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();


                                            }catch (Exception e){
                                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                            }


                                        }
                                    });
                                    finish();
                                    progressDialog.dismiss();

                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {

                                    // Error, Image not uploaded
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(
                                    new OnProgressListener<UploadTask.TaskSnapshot>() {

                                        // Progress Listener for loading
                                        // percentage on the dialog box
                                        @Override
                                        public void onProgress(
                                                UploadTask.TaskSnapshot taskSnapshot)
                                        {
                                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                            progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                        }
                                    });

                }




            }
        }catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

}