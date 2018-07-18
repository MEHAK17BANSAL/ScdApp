package com.example.scdapp.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.scdapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            handler.sendEmptyMessageDelayed(201, 2500);
        } else {
            handler.sendEmptyMessageDelayed(101, 2500);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = null;
            if (msg.what == 101) {
                intent = new Intent(SplashActivity.this, UserLoginActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, UserHomeActivity.class);
            }

            startActivity(intent);
            finish();

        }
    };
}
