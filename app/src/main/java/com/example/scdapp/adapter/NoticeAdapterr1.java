package com.example.scdapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.scdapp.R;
import com.example.scdapp.holder.NoticeHolder1;
import com.example.scdapp.model.Notice1;
import com.example.scdapp.ui.ViewDailyNoticeActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


public class NoticeAdapterr1 extends FirestoreRecyclerAdapter<Notice1, NoticeHolder1> {

    String options[] = {"View"};
    Context context;
    FirebaseFirestore firestore;
    String docId;

    public NoticeAdapterr1(FirestoreRecyclerOptions<Notice1> options, Context context ) {
        super(options);
        this.context  = context;
        firestore = FirebaseFirestore.getInstance();
    }


    @Override
    protected void onBindViewHolder(NoticeHolder1 holder, int position,final Notice1 model) {
        holder.txtTitle.setText(model.title);
        holder.txtBody.setText(model.body);
        holder.txtdate.setText(model.date);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firestore.collection("notice").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for(DocumentSnapshot doc : documentSnapshots){
                            final String uid = doc.getId();
                            DocumentReference docRef = firestore.collection("notice").document(uid);
                            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Notice1 client = documentSnapshot.toObject(Notice1.class);
                                    if(client.title.equals(model.title))
                                        docId = uid;
                                }
                            });
                        }
                    }
                });

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                final View convertView = inflater.inflate(R.layout.custom_list, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("List");
                ListView lv = convertView.findViewById(R.id.listViewCustom);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,options);
                lv.setAdapter(adapter);
                alertDialog.show();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:

                                Intent intent =new Intent(context, ViewDailyNoticeActivity.class);
                                intent.putExtra("KeyUid",docId);
                                intent.putExtra("key",  model);
                                context.startActivity(intent);
                                break;

                        }
                    }
                });
            }
        });

    }

    @NonNull
    @Override
    public NoticeHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item1, parent, false);
        NoticeHolder1 clientHolder = new NoticeHolder1(view);
        return clientHolder;
    }
}
