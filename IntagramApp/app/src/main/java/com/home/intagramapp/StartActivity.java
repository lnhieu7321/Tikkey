package com.home.intagramapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    Button login,ragister;
    FirebaseUser firebaseUser;
    // Khai báo biến CountDownTimer
    CountDownTimer timer;

    // Khai báo biến SharedPreferences
    SharedPreferences preferences;

//    @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (firebaseUser != null){
//
//            startActivity(new Intent(StartActivity.this,MainActivity.class));
//            finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

//        login = findViewById(R.id.login_btn);
//        ragister = findViewById(R.id.Ragister);
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(StartActivity.this,LoginActivity.class));
//
//
//            }
//        });
//
//        ragister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(StartActivity.this,RagisterActivity.class));
//
//            }
//        });

        // Tạo một CountDownTimer mới với 3 giây
        timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Cập nhật giao diện với thời gian còn lại
            }

            @Override
            public void onFinish() {
                // Lấy trạng thái đăng nhập của người dùng
                preferences = getSharedPreferences("login", MODE_PRIVATE);
                boolean isLogged = preferences.getBoolean("isLogged", false);

                // Nếu người dùng đã đăng nhập, chuyển sang trang home
                if (isLogged) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                // Nếu người dùng chưa đăng nhập, chuyển sang trang login
                else {
                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };

// Bắt đầu đếm ngược
        timer.start();

    }
}
