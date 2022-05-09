package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListAccount extends AppCompatActivity {
    DatabaseReference databaseReference;
    ListView lvwListAccount;
    ArrayList<Account> list = new ArrayList<>();
    AccountsAdapter arrayAdapter;

    Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_account);


        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        lvwListAccount = findViewById(R.id.lvwAccounts);

        databaseReference = FirebaseDatabase.getInstance().getReference("Accounts");

        arrayAdapter = new AccountsAdapter(ListAccount.this, R.layout.item_account, list);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Account account = snapshot.getValue(Account.class);
                account.setId(snapshot.getKey());//Set id khong qua contructor
                list.add(account);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lvwListAccount.setAdapter(arrayAdapter);

        lvwListAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListAccount.this,MainActivityDone.class);
                intent.putExtra("id", list.get(i).getId());
                intent.putExtra("username", list.get(i).getUsername());
                intent.putExtra("email", list.get(i).getEmail());
                intent.putExtra("yourfeeling", list.get(i).getYourfeeling());
                intent.putExtra("sad",list.get(i).getSad());
                intent.putExtra("happy",list.get(i).getHappy());
                intent.putExtra("neutral",list.get(i).getNeutral());
                startActivity(intent);
            }
        });

    }
}