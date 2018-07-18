package com.example.scdapp.ui;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.scdapp.R;
import com.example.scdapp.adapter.NoticeAdapterr1;
import com.example.scdapp.listener.RecyclerAdapterClickListener1;
import com.example.scdapp.model.Notice1;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
public class DailyNoticeActivity extends AppCompatActivity implements RecyclerAdapterClickListener1 {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String uid;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    NoticeAdapterr1 noticeAdapterr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_notice);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerView1);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final ArrayList<Notice1> userList = new ArrayList<>();
        Query query = firestore.collection("notice").orderBy("date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Notice1> options = new FirestoreRecyclerOptions.Builder<Notice1>().setQuery(query, Notice1.class).build();
        noticeAdapterr = new NoticeAdapterr1(options,DailyNoticeActivity.this);   //check
        noticeAdapterr.startListening();
        recyclerView.setAdapter(noticeAdapterr);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
    @Override
    public void onRecyclerAdapterClicked(int position) {

    }
}
