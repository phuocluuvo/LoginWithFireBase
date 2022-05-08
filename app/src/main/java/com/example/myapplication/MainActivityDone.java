package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivityDone extends AppCompatActivity {

    private FirebaseUser fb_user;
    private String userId, name, email;
    final private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Button btnLogOut;
    private ImageView ivHappy, ivSad, ivNeutral;
    private TextView tvwHappy, tvwSad, tvwNeutral;
    private int countHappy;
    private int countSad = 0;
    private int countNeutral = 0;

    private Account accountEdit = new Account();
    private TextView tvwName, tvwEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_done);

        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        ivHappy = (ImageView) findViewById(R.id.btnHappyFace);
        ivSad = (ImageView) findViewById(R.id.btnSadFace);
        ivNeutral = (ImageView) findViewById(R.id.btnNeutral);


        final DatabaseReference databaseReference = database.getReference("Accounts");
        fb_user = FirebaseAuth.getInstance().getCurrentUser();
        userId = fb_user.getUid();

        ivNeutral.setOnClickListener(view -> {
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        countNeutral++;
                        snapshot.getRef().child("neutral").setValue(countNeutral);
                        tvwNeutral.setText(countNeutral + "");
                        Toast.makeText(MainActivityDone.this, "Update success! " + countNeutral, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivityDone.this, "Update unsuccessful!" + countNeutral, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        ivSad.setOnClickListener(view -> {
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        countSad++;
                        snapshot.getRef().child("sad").setValue(countSad);
                        tvwSad.setText(countSad + "");
                        Toast.makeText(MainActivityDone.this, "Update success! " + countSad, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivityDone.this, "Update unsuccessful!" + countSad, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        ivHappy.setOnClickListener(view -> {
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        countHappy++;
                        snapshot.getRef().child("happy").setValue(countHappy);
                        tvwHappy.setText(countHappy + "");
                        Toast.makeText(MainActivityDone.this, "Update success! " + countHappy, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivityDone.this, "Update unsuccessful!" + countHappy, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });


        btnLogOut.setOnClickListener(view -> {
            logOut();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getBaseContext(), ActivitySignIn.class));
        });


        tvwName = (TextView) findViewById(R.id.tvwName_Done);
        tvwEmail = (TextView) findViewById(R.id.tvwEmail_Done);
        tvwNeutral = (TextView) findViewById(R.id.tvwNeutral);
        tvwHappy = (TextView) findViewById(R.id.tvwHappy);
        tvwSad = (TextView) findViewById(R.id.tvwSad);

        //Sign in with account in FireBase
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                if (account != null) {

                    name = account.getUsername();
                    email = account.getEmail();
                    countHappy = account.getHappy();
                    countNeutral = account.getNeutral();
                    countSad = account.getSad();

                    tvwName.setText(name);
                    tvwEmail.setText(email);

                    tvwSad.setText(countHappy+ "");
                    tvwNeutral.setText(countNeutral + "");
                    tvwHappy.setText(countSad + "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivityDone.this, "Error Check again please!", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void logOut() {

    }

}