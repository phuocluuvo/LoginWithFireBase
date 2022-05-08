package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

    private FirebaseUser fb_user;
    private String userId, name, email, yourfeeling;
    final private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Button btnLogOut, btnSaveYourFeeling, btnDeleteUser;
    private ImageView ivHappy, ivSad, ivNeutral;
    private TextView tvwHappy, tvwSad, tvwNeutral;
    private int countHappy;
    private int countSad = 0;
    private int countNeutral = 0;
    private EditText edtYourFeeling;

    private Account accountEdit = new Account();
    private TextView tvwName, tvwEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_done);

        btnSaveYourFeeling = (Button) findViewById(R.id.btnSaveYourFeeling);
        edtYourFeeling = (EditText) findViewById(R.id.edtFeeling);
        btnDeleteUser = (Button) findViewById(R.id.btnDeleteUser);
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

        btnSaveYourFeeling.setOnClickListener(view -> {
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        yourfeeling = edtYourFeeling.getText().toString();
                        snapshot.getRef().child("yourfeeling").setValue(yourfeeling);
                        Toast.makeText(MainActivityDone.this, "Save success! ", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivityDone.this, "Save unsuccessful!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        btnLogOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getBaseContext(), ActivitySignIn.class));
        });

        btnDeleteUser.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm");
            builder.setMessage("Are you sure? You want to delete this account?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //xoa user////////////////////////////////////
                    fb_user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                databaseReference.child(userId).setValue(null);
                                startActivity(new Intent(MainActivityDone.this,MainActivity.class));
                                Toast.makeText(MainActivityDone.this, "So sad you deleted me :((", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    /////////////////////////////////////////////////
                }

            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
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
                    yourfeeling = account.getYourfeeling();

                    tvwName.setText(name);
                    tvwEmail.setText(email);

                    tvwSad.setText(countHappy + "");
                    tvwNeutral.setText(countNeutral + "");
                    tvwHappy.setText(countSad + "");
                    edtYourFeeling.setText(yourfeeling);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivityDone.this, "Error Check again please!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}