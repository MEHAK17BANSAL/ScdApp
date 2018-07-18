package com.example.scdapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scdapp.R;
import com.example.scdapp.model.Notice1;

import com.example.scdapp.model.User1;
import com.firebase.ui.auth.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRegistrationActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{

    EditText editname,editemail,editpassword,editrollno,editphone;
    TextView login;
    Button btn;
    FirebaseAuth auth;
    User1 user;
    Notice1 notice;
    FirebaseFirestore firestore;
    CollectionReference userCollection;
    ProgressDialog progressDialog;
    String uid;
public int r=0;
public int r1=0;
    RadioButton rMale,rFemale;
    Spinner spClass,spCity;
    ArrayAdapter<String> adapter,adapterCity;


    void initViews(){

        editname=findViewById(R.id.editTextName12);
        editemail=findViewById(R.id.editTextEmail12);
        editpassword=findViewById(R.id.editTextPassword12);
        editrollno=findViewById(R.id.editTextRollNo12);
        editphone=findViewById(R.id.editTextPhoneNo12);
        btn=findViewById(R.id.button4);
        login=findViewById(R.id.loggedIn);
        login.setPaintFlags(login.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        rMale=findViewById(R.id.radioMale);
        rFemale=findViewById(R.id.radioFemale);
        rMale.setOnCheckedChangeListener(this);
        rFemale.setOnCheckedChangeListener(this);
        spClass=findViewById(R.id.spinClass);
        spCity=findViewById(R.id.selectCity);

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        adapter.add("--Select Class--");
        adapter.add("BCA");
        adapter.add("BCom");
        adapter.add("Ba");
        adapter.add("BTech");
        adapter.add("MA");
        adapter.add("BBA");
        adapter.add("PGDCA");
        adapter.add("Msc.IT");
        spClass.setAdapter(adapter);

        adapterCity=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        adapterCity.add("--Select City--");
        adapterCity.add("Ludhiana");
        adapterCity.add("Phillur");
        adapterCity.add("Jalandhar");
        adapterCity.add("Chandigarh");
        spCity.setAdapter(adapterCity);



        spClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    user.class1=adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.city=adapterCity.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn.setOnClickListener(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserRegistrationActivity.this,UserLoginActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        initViews();
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        userCollection=firestore.collection("users");
        user=new User1();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait... ");
        progressDialog.setCancelable(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
       int id=buttonView.getId();
       if(isChecked){
           if(id==R.id.radioMale){
               user.gender="Male";

               r++;
           }else {
               user.gender="Female";
               r++;
           }
       }
    }

    void saveUser(){
        userCollection.document(uid).set(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UserRegistrationActivity.this,"User Saved in DB",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(UserRegistrationActivity.this,UserHomeActivity.class);
                startActivity(intent);
                finish();
                progressDialog.dismiss();
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserRegistrationActivity.this,"Error While Saving User",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    void registerUser(){
        progressDialog.show();
        auth.createUserWithEmailAndPassword(user.email,user.password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    uid=task.getResult().getUser().getUid();
                    sendEmailverification();
                   // Toast.makeText(UserRegistrationActivity.this,user.name+"Registere Successfully",Toast.LENGTH_LONG).show();
                    Log.i("User","User Registered: "+uid);

                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               Log.i("User","User Registration Failed: "+e.getMessage());
               e.printStackTrace();
               progressDialog.dismiss();
            }
        });
    }

    boolean validateFields(){
        boolean flag=true;
        if(user.name.isEmpty()){
            flag=false;
        }
        if(user.email.isEmpty()){
            flag=false;
        }
        if(user.password.isEmpty()){
            flag=false;
        }else {
            if(user.password.length()<6){
                flag=false;
                Toast.makeText(UserRegistrationActivity.this,"Password Length Too Short!!!!",Toast.LENGTH_LONG).show();
            }
        }
        if(user.rollno.isEmpty()){
            flag=false;
        }
        if(user.phoneno.isEmpty()){
            flag=false;
        }else {
            if(user.phoneno.length()!=10){
                flag=false;
            }
        }

        if(user.class1.isEmpty()){
            flag=false;
        }
        if(user.city.isEmpty()){
            flag=false;
        }
        if(r==0){
            flag=false;
        }
        return flag;
    }

    @Override
    public void onClick(View v) {
        user.name=editname.getText().toString().trim();
        user.email=editemail.getText().toString().trim();
        user.password=editpassword.getText().toString().trim();
        user.phoneno=editphone.getText().toString().trim();
        user.rollno=editrollno.getText().toString().trim();

        if(validateFields()){
            registerUser();
        }
        else {
            Toast.makeText(this,"Please Enter Correct Details..",Toast.LENGTH_LONG).show();
        }
    }

    void sendEmailverification(){
        FirebaseUser firebaseUser=auth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        saveUser();
                        Toast.makeText(UserRegistrationActivity.this,"successfully registered,verfication mail sent",Toast.LENGTH_LONG).show();
                        auth.signOut();
                        Intent intent=new Intent(UserRegistrationActivity.this,UserLoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(UserRegistrationActivity.this,"verification email has not been sent",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


}
