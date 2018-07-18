package com.example.scdapp.holder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.scdapp.R;


public class NoticeHolder1 extends RecyclerView.ViewHolder {

    public TextView txtTitle;
    public TextView txtBody;
    public TextView txtdate;
    LinearLayout linearLayout;

    public NoticeHolder1(View itemView) {
        super(itemView);
        linearLayout=itemView.findViewById(R.id.linearlayoutrecycle);
        txtTitle = itemView.findViewById(R.id.textViewName7);
        txtBody = itemView.findViewById(R.id.textViewEmail7);
        txtdate=itemView.findViewById(R.id.textdate);
    }


}
