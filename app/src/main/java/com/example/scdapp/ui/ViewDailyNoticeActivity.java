package com.example.scdapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.scdapp.R;
import com.example.scdapp.model.Notice1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewDailyNoticeActivity extends AppCompatActivity {
     TextView textTitle,textbody,textdate;
    FirebaseAuth auth;
    Notice1 notice;
    String uid;
    Intent rcv;
    FirebaseFirestore firestore;
    CollectionReference usercollection;

     void initViews(){
         rcv=getIntent();
         uid=rcv.getStringExtra("KeyUid");
         textTitle=findViewById(R.id.textView8);
         textbody=findViewById(R.id.textView9);
         textdate=findViewById(R.id.textView10);
         auth=FirebaseAuth.getInstance();
         firestore=FirebaseFirestore.getInstance();
         usercollection=firestore.collection("notice");
         notice=new Notice1();
         notice=(Notice1) rcv.getSerializableExtra("key");
         textTitle.setText(notice.title);
         textbody.setText(notice.body);
         textdate.setText(notice.date);
         firestore=FirebaseFirestore.getInstance();
         usercollection=firestore.collection("notice");

     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_daily_notice);
        initViews();
    }
}
