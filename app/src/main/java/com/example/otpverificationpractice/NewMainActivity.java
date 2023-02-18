package com.example.otpverificationpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/*New firestore Registration Code Logic part 2*/
public class NewMainActivity extends AppCompatActivity {
    Button readBtn;
    FirebaseFirestore db;
    EditText firstName, lastName, phone, email;
    MaterialButton registrationBtn;
    TextView emailTV;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        init();

        /*redirect to read Activity of Firestore db will remove*/
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        final String USER_ID = intent.getStringExtra("Unique ID");
        final String EMAIL_ID = intent.getStringExtra("Email ID");
        emailTV.setText(EMAIL_ID);

        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Firstname = firstName.getText().toString().trim();
                String Lastname = lastName.getText().toString().trim();
                String PhoneNo = phone.getText().toString().trim();
                Map<String, Object> userData = new HashMap<>();
                userData.put("Email ID", EMAIL_ID);
                userData.put("First Name", Firstname);
                userData.put("Last Name", Lastname);
                userData.put("Phone Number", PhoneNo);

                DocumentReference userRef = db.collection("users").document(USER_ID);
                userRef.set(userData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("User","Profile created for " + USER_ID);
                                Intent i = new Intent(NewMainActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("FireStore", e.getMessage());
                                Toast.makeText(NewMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void init() {
        emailTV = findViewById(R.id.emailTV);
        email = findViewById(R.id.email);
        readBtn = findViewById(R.id.readBtn);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        registrationBtn = findViewById(R.id.btnRegister);
        db = FirebaseFirestore.getInstance();
    }
}