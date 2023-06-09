package com.home.intagramapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import Model.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    RelativeLayout logout, edit_profile;
    TextView txt_Name, txt_Gmail;
    StorageReference storageRef;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logout = findViewById(R.id.logout);
        edit_profile = findViewById(R.id.edit_profile);
        txt_Name = findViewById(R.id.txt_Name);
        txt_Gmail = findViewById(R.id.txt_Gmail);
        ImageView ava_profile = findViewById(R.id.ava_profile);
//        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference("uploads");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                txt_Name.setText(user.getFullname());

                txt_Gmail.setText(user.getBio());

                Glide.with(getApplicationContext())
                        .load(user.getImageurl())
                        .into(ava_profile);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this,StartActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Lấy giá trị dữ liệu từ dataSnapshot và cập nhật TextView và ImageView tương ứng
//                String textValue = dataSnapshot.child("fullname").getValue(String.class);
//                txt_Name.setText(textValue);
//                String textbio = dataSnapshot.child("bio").getValue(String.class);
//                txt_Gmail.setText(textbio);
//
//                String imageUrl = dataSnapshot.child("imageurl").getValue(String.class);
//                Glide.with(ProfileActivity.this)
//                        .load(imageUrl)
//                        .into(ava_profile);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Xử lý lỗi nếu có
//            }
//        });









    }


}