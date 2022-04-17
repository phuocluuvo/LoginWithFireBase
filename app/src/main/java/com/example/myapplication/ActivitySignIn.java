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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.annotations.Nullable;

public class ActivitySignIn extends AppCompatActivity {
    private EditText edtEmail_Login, edtPassword_Login;
    private Button btnSignIn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private TextView twSignInWithGG, twRegister_LogIn;
    //Login with google
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

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
        //Login with google
        twSignInWithGG = (TextView) findViewById(R.id.tvwSignGoogle);

        createRequest();

        twSignInWithGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

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

    private void createRequest() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent i = gsc.getSignInIntent();
        startActivityForResult(i, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account =
                        task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                startActivity(new Intent(getApplicationContext(), MainActivityDone.class));

            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error:" + e, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), MainActivityDone.class));
                        } else {
                            Toast.makeText(ActivitySignIn.this, "Login Fail! Check your password", Toast.LENGTH_SHORT).show();

                        }
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
                    //Xác nhận email
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified())
                        startActivity(new Intent(getBaseContext(), MainActivityDone.class));
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(ActivitySignIn.this, "Check your email to verify!", Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(ActivitySignIn.this, "Login Fail! Check your password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}