package com.example.otpverificationpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ForgetPasswordActivity extends AppCompatActivity {
    Button submitBtn;
    FirebaseFirestore db;
    String email, phoneNo;
    EditText emailEdit;
    FirebaseAuth fAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emailEdit = findViewById(R.id.emailEdt);
        submitBtn = findViewById(R.id.submitBtn);
        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEdit.getText().toString().trim().toLowerCase();
                if (email.equals("")) {
                    Toast.makeText(ForgetPasswordActivity.this, "pls Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else {
//                    db.collection("users")
//                            .whereEqualTo("Email ID", email)
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        if (task.getResult().size() > 0) {
//                                            QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
//                                            phoneNo = document.get("Phone Number").toString();
//                                            Toast.makeText(ForgetPasswordActivity.this, "OTP will be sent to : " + phoneNo, Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(ForgetPasswordActivity.this, MainActivity.class);
//                                            intent.putExtra("Phone_Number", phoneNo);
//                                            startActivity(intent);
//                                        } else {
//                                            Toast.makeText(ForgetPasswordActivity.this, "Please Enter a Registered Email ID", Toast.LENGTH_SHORT).show();
//                                        }
//                                    } else {
//                                        Toast.makeText(ForgetPasswordActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
                    /*Send password reset mail  logic*/
                    fAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ForgetPasswordActivity.this, "Reset Link Sent your Email : "+email, Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ForgetPasswordActivity.this, "Error! Reset Link not sent : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }
}
