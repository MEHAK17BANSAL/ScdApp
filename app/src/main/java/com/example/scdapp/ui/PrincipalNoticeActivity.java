package com.example.scdapp.ui;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.scdapp.R;
import com.example.scdapp.adapter.PrincipalAdapter1;
import com.example.scdapp.listener.RecyclerAdapterClickListener1;
import com.example.scdapp.model.PrincipalNotice1;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
public class PrincipalNoticeActivity extends AppCompatActivity implements RecyclerAdapterClickListener1 {
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String uid;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PrincipalAdapter1 principalAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_notice);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerView2);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final ArrayList<PrincipalNotice1> userList = new ArrayList<>();
        Query query = firestore.collection("principalnotice").orderBy("date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<PrincipalNotice1> options = new FirestoreRecyclerOptions.Builder<PrincipalNotice1>().setQuery(query, PrincipalNotice1.class).build();
        principalAdapter = new PrincipalAdapter1(options, PrincipalNoticeActivity.this);   //check
        principalAdapter.startListening();
        recyclerView.setAdapter(principalAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
    @Override
    public void onRecyclerAdapterClicked(int position) {

    }
}
