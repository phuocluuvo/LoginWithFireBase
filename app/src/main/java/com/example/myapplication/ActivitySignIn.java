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
import com.google.firebase.auth.FirebaseUser;

public class ActivitySignIn extends AppCompatActivity {
    private EditText edtEmail_Login, edtPassword_Login;
    private Button btnSignIn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private TextView  twRegister_LogIn;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivityDone.class);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        edtEmail_Login = (EditText) findViewById(R.id.edtEmail_login);
        edtPassword_Login = (EditText) findViewById(R.id.edtPassword_login);
        twRegister_LogIn = (TextView) findViewById(R.id.tvwRegister_login);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_Login);
        progressBar.setVisibility(View.GONE);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });


        twRegister_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ActivityRegister.class));
            }
        });
    }
    /**
     * Login using real time database by firebase
     */
    private void userLogin() {
        String email = edtEmail_Login.getText().toString().trim();
        String password = edtPassword_Login.getText().toString().trim();

        if (email.isEmpty()) {
            edtEmail_Login.setError("Email must be filled");
            edtEmail_Login.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail_Login.setError("Email invalid");
            edtEmail_Login.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edtPassword_Login.setError("Password must be filled");
            edtPassword_Login.requestFocus();
            return;
        } else if (password.length() < 6) {
            edtPassword_Login.setError("Password must be more than 6 characters");
            edtPassword_Login.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getBaseContext(), MainActivityDone.class));
                    //Xác nhận email
/*                   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified())
                        startActivity(new Intent(getBaseContext(), MainActivityDone.class));
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(ActivitySignIn.this, "Check your email to verify!", Toast.LENGTH_LONG).show();
                    }*/
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(ActivitySignIn.this, "Login Fail! Check your password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}