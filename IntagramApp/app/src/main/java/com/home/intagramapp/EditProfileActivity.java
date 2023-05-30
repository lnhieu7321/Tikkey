package com.home.intagramapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import Model.User;

public class EditProfileActivity extends AppCompatActivity {

    ImageView close , image_profile, cover_media;
    Button save;
    TextView  tv_change, btn_open_gallery;
    EditText fullname,username,bio,birth,gender,occupation;

    FirebaseUser firebaseUser;
    Uri mImageUri, mImageCover;
    String myUrl = "";
    private StorageTask  uploadTask, uploadCover;
    StorageReference storageRef;




    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        close = findViewById(R.id.close);
        image_profile = findViewById(R.id.image_profile);
        cover_media = findViewById(R.id.cover_media);
        save = findViewById(R.id.save);
        tv_change = findViewById(R.id.tv_change);
        btn_open_gallery = findViewById(R.id.btn_open_gallery);
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        bio = findViewById(R.id.bio);
        birth = findViewById(R.id.birth);
        gender = findViewById(R.id.gender);
        occupation = findViewById(R.id.occupation);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference("uploads");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                fullname.setText(user.getFullname());
                username.setText(user.getUsername());
                bio.setText(user.getBio());
                birth.setText(user.getBirth());
                gender.setText(user.getGender());
                occupation.setText(user.getOccupation());
                Glide.with(getApplicationContext())
                        .load(user.getImageurl())
                        .into(image_profile);
                Glide.with(getApplicationContext())
                        .load(user.getImagecover())
                        .into(cover_media);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity()
                        .setAspectRatio(1,1)
                        .start(EditProfileActivity.this);
            }
        });

        btn_open_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity()
                        .setAspectRatio(1,1)
                        .start(EditProfileActivity.this);
            }
        });


        image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity()
                        .setAspectRatio(1,1)
                        .start(EditProfileActivity.this);
            }
        });
//==========================================
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                updateProfile(fullname.getText().toString(),
                        username.getText().toString(),
                        bio.getText().toString(),
                        birth.getText().toString(),
                        gender.getText().toString(),
                        occupation.getText().toString());
                uploadImage();

            }
        });


    }
//    ======================================Info Profile

    private void updateProfile(String fullname, String username, String bio, String birth, String gender, String occupation) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String,Object> hashMap = new HashMap<>();

        hashMap.put("username",username);
        hashMap.put("fullname",fullname);
        hashMap.put("bio",bio);
        hashMap.put("birth",birth);
        hashMap.put("gender",gender);
        hashMap.put("occupation",occupation);

        reference.updateChildren(hashMap);

    }

    private String getFileExtensions(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));

    }
// ======================================================================Image avata
    private  void uploadImage() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading");
        progressDialog.show();
        if (mImageUri != null) {

            final StorageReference filereferance = storageRef.child(System.currentTimeMillis()
                    + "." + getFileExtensions(mImageUri));

            uploadTask = filereferance.putFile(mImageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {

                        throw task.getException();

                    }
                    return filereferance.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if(task.isSuccessful()){

                        Uri downloadUrl = task.getResult();

                       String myUrl = downloadUrl.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                        HashMap<String,Object> hashMap = new HashMap<>();

                        hashMap.put("imageurl",""+myUrl);
                        hashMap.put("imagecover",""+myUrl);


                        reference.updateChildren(hashMap);


                        progressDialog.dismiss();

                    }
                    else {
                        Toast.makeText(EditProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }


                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                    Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(EditProfileActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }


//    ==============================================Cover Media

    private  void uploadImageCover() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading");
        progressDialog.show();
        if (mImageCover != null) {

            final StorageReference filereferance = storageRef.child(System.currentTimeMillis()
                    + "." + getFileExtensions(mImageCover));

            uploadCover = filereferance.putFile(mImageCover);
            uploadCover.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {

                        throw task.getException();

                    }
                    return filereferance.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if(task.isSuccessful()){

                        Uri downloadUrlCover = task.getResult();

                        String myUrlCover = downloadUrlCover.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                        HashMap<String,Object> hashMap = new HashMap<>();

                        hashMap.put("imageurl",""+myUrlCover);
                        hashMap.put("imagecover",""+myUrlCover);


                        reference.updateChildren(hashMap);


                        progressDialog.dismiss();

                    }
                    else {
                        Toast.makeText(EditProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }


                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                    Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(EditProfileActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

//    ==============================================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE || requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE ){
            if (resultCode == RESULT_OK){
                switch (requestCode){
                    case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                        CropImage.ActivityResult result = CropImage.getActivityResult(data);

                        mImageUri = result.getUri();
                        image_profile.setImageURI(mImageUri);
                    case  CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE:
                        CropImage.ActivityResult results = CropImage.getActivityResult(data);

                        mImageCover = results.getUri();
                        cover_media.setImageURI(mImageCover);

                }


            }
        }else {
            Toast.makeText(EditProfileActivity.this,"Searching gone wrong!",Toast.LENGTH_SHORT).show();


        }
    }

}
