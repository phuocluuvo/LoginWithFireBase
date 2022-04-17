package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivityDone extends AppCompatActivity {

    private FirebaseUser account;
    private String userId;
    private DatabaseReference databaseReference;

    private Button btnLogOut;
    //Sign in With Google
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_done);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getBaseContext(), ActivitySignIn.class));
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Accounts");

        account = FirebaseAuth.getInstance().getCurrentUser();

        userId = account.getUid();

        final TextView tvwName = (TextView) findViewById(R.id.tvwName_Done);
        final TextView tvwEmail = (TextView) findViewById(R.id.tvwEmail_Done);
        //Sign in With Google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String name = account.getDisplayName();
            String email = account.getEmail();
            tvwName.setText(name);
            tvwEmail.setText(email);
        }
        //Sign in with account in FireBase
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                if (account != null) {
                    String name = account.username;
                    String email = account.email;

                    tvwName.setText(name);
                    tvwEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivityDone.this, "Error Check again please!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), ActivitySignIn.class));
            }
        });
    }
}