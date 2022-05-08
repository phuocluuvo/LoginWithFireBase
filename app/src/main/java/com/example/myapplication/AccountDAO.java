package com.example.myapplication;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class AccountDAO {

    private DatabaseReference databaseReference;
    public AccountDAO(){
        FirebaseDatabase db= FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Account.class.getSimpleName());
    }

    public Task<Void> add(Account account){
        return databaseReference.push().setValue(account);
    }

    /**
     * update object have a id with new thuoctinhmoi(hashmap)
     * @param id
     * @param thuoctinhmoi
     * @return
     */
    public  Task<Void> update(String id, HashMap<String,Object> thuoctinhmoi){
        return databaseReference.child(id).updateChildren(thuoctinhmoi);
    }

    public Query getAccount(){
        return databaseReference;
    }
}
