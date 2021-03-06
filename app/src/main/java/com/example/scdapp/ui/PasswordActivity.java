package com.example.scdapp.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scdapp.R;

import com.example.scdapp.model.User1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

     EditText resetText;
     Button resetPassword;
     FirebaseAuth auth;
     User1 user;

    void initViews(){
        resetText=findViewById(R.id.passwordEmail);
        resetPassword=findViewById(R.id.resetPassword);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        initViews();
        auth=FirebaseAuth.getInstance();
        user=new User1();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.email=resetText.getText().toString().trim();
                if(user.email.equals("")){
                    Toast.makeText(PasswordActivity.this,"please enter your registered email id",Toast.LENGTH_LONG).show();
                }else {
                    auth.sendPasswordResetEmail(user.email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordActivity.this,"password reset email sent! ",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(PasswordActivity.this,UserLoginActivity.class);
                                startActivity(intent);
                                finish();

                            }else {
                                Toast.makeText(PasswordActivity.this," Erroe in sending password reset email ",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    }
}
