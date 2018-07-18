package com.example.scdapp.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.scdapp.R;

/**
 * Created by admin on 4/22/2018.
 */

public class PrincipalHolder1 extends RecyclerView.ViewHolder {

    public TextView txtTitle;
    public TextView txtBody;
    public TextView txtdate;

    LinearLayout linearLayout;
    public PrincipalHolder1(@NonNull View itemView) {
        super(itemView);

        txtTitle = itemView.findViewById(R.id.textViewName8);
        txtBody = itemView.findViewById(R.id.textViewEmail8);
        txtdate=itemView.findViewById(R.id.textdate8);
        linearLayout=itemView.findViewById(R.id.linearlayoutrecycle1);


    }


}
