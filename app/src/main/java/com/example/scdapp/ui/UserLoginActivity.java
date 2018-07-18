package com.example.scdapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scdapp.R;

import com.example.scdapp.model.User1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView signUp,forgetPass;
    FirebaseAuth auth;
    User1 user;
    String uid;
    EditText editemail,editpassword;
    FirebaseFirestore firestore;
    CollectionReference userCollection;
    ProgressDialog progressDialog;
    Button btn;
    void initViews(){
        btn=findViewById(R.id.button3);
        btn.setOnClickListener(this);
         editemail=findViewById(R.id.editTextEmail11);
         editpassword=findViewById(R.id.editTextPass11);
        signUp=findViewById(R.id.signUp);
        signUp.setPaintFlags(signUp.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        forgetPass=findViewById(R.id.forgetPass);
        forgetPass.setPaintFlags(forgetPass.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserLoginActivity.this,UserRegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserLoginActivity.this,PasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        initViews();
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        userCollection=firestore.collection("users");
        user=new User1();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
    }

    void loginUser(){
        progressDialog.show();
        auth.signInWithEmailAndPassword(user.email,user.password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    chechEmailVerification();
                    progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("User","User Login Failed"+e.getMessage());
                e.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

    boolean validateFields(){
        boolean flag=true;
        if(user.email.isEmpty()){
            flag=false;
        }
        if(user.password.isEmpty()){
            flag=false;
        }
        return flag;
    }

    @Override
    public void onClick(View v) {
        user.email=editemail.getText().toString().trim();
        user.password=editpassword.getText().toString().trim();
        if(validateFields()) {
            loginUser();
        }
        else {
            Toast.makeText(this,"Please Enter Correct Details",Toast.LENGTH_LONG).show();
        }
    }

    void chechEmailVerification(){
        FirebaseUser firebaseUser=auth.getInstance().getCurrentUser();
        boolean emailflag=firebaseUser.isEmailVerified();
        if(emailflag){
            Toast.makeText(UserLoginActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(UserLoginActivity.this,UserHomeActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this,"Verify your Email",Toast.LENGTH_LONG).show();
            auth.signOut();
        }
    }
}
