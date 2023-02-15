package com.example.otpverificationpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/*Starting Activity Login*/
public class LoginActivity extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mLogin, mRegistration;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.login);
        mRegistration = findViewById(R.id.registration);
        mAuth = FirebaseAuth.getInstance();

        /*Login code*/
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            Intent i = new Intent(LoginActivity.this, DemoActivity.class);
                            startActivity(i);

                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Sign in error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //new user code user will be directed to registration activity where he have to register
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    //When initializing your Activity, check to see if the user is currently signed in
    @Override
    protected void onStart() {
        super.onStart();
//        Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
//            reload();
        }
//        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    //this code will execute when we Leave this activity and at that time we are removing this firebaseAuth litener
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}