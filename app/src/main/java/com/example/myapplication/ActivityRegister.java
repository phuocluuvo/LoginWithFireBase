package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityRegister extends AppCompatActivity {
    TextView twSignIn;
    Button btnRegister;
    EditText edtName, edtEmail, edtPassword, edtPassword_Re;
    ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        twSignIn = (TextView) findViewById(R.id.tvwSignIn_register);
        edtPassword = (EditText) findViewById(R.id.edtPassword_register);
        edtName = (EditText) findViewById(R.id.edtName_register);
        edtEmail = (EditText) findViewById(R.id.edtEmail_register);
        edtPassword_Re = (EditText) findViewById(R.id.edtPassword_Re_register);
        btnRegister = (Button) findViewById(R.id.btnRegister_register);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        twSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ActivitySignIn.class));
            }
        });
    }

    /**
     * ????ng k?? user
     *
     */
    private void registerUser() {
        String email = edtEmail.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (name.isEmpty()) {
            edtName.setError("Name must be filled");
            edtName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            edtEmail.setError("Email must be filled");
            edtEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email invalid");
            edtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edtPassword.setError("Password must be filled");
            edtPassword.requestFocus();
            return;
        } else if (!password.equals(edtPassword_Re.getText().toString().trim())) {
            edtPassword.setError("Password and Re-Password must be the same");
            edtPassword.requestFocus();
            return;
        } else if (password.length() < 6) {
            edtPassword.setError("Password must be more than 6 characters");
            edtPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        //Th??m s??? ki???n khi t???o xong user
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //n???u x??c th???c th??nh c??ng th??nh c??ng
                        if (task.isSuccessful()) {
                            //Account account = new Account(name, password, email,0,0,0,"");// n???u mu???n kh???i t???o gi?? tr??? ?????u th?? m??? c??i n??y ra
                            Account account = new Account();
                            FirebaseDatabase.getInstance()
                                    .getReference("Accounts")//T??n Database
                                    .child(FirebaseAuth
                                            .getInstance()
                                            .getCurrentUser().getUid())//id ph??n bi???t fire base user v???i authentication
                                    .setValue(account)//t???o d??? li???u r???ng tr?????c n???u kh??ng th?? t???o d??? li???u ban ?????u l?? account ??? tr??n
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isComplete()) {
                                                Toast.makeText(ActivityRegister.this, "User register successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(ActivityRegister.this, "Register fail. Please try again", Toast.LENGTH_SHORT).show();
                                            }
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    });
                        } else {
                            //T???o th???t b???i
                            Toast.makeText(ActivityRegister.this, "Register fail. Please try again", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }
        );
    }
}