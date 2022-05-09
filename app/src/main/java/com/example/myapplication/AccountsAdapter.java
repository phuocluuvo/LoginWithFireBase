package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AccountsAdapter extends ArrayAdapter<Account> {
    Context context;
    ArrayList<Account> listaccounts ;
    int idLayoutList;

    public AccountsAdapter( Context context, int idLayoutList, ArrayList<Account> accounts) {
        super(context, idLayoutList, accounts);
        this.context = context;
        this.listaccounts = accounts;
        this.idLayoutList  = idLayoutList;
    }

    @NonNull
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(idLayoutList,null);

        TextView tvFeeling = view.findViewById(R.id.tvwYourFeeling);
        tvFeeling.setText(listaccounts.get(i).getYourfeeling());
        TextView tvName = view.findViewById(R.id.tvwUsername);
        tvName.setText(listaccounts.get(i).getUsername());

        return view;
    }
}
