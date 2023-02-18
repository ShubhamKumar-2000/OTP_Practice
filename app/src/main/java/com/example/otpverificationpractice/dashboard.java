package com.example.otpverificationpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*Password change implementation*/
public class dashboard extends AppCompatActivity {
    Button submitBtn;
    EditText newPasswordET;
    String newPassword, UserID;
    FirebaseAuth fAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        newPasswordET=findViewById(R.id.newPasswordET);
        submitBtn=findViewById(R.id.newPasswordSubmitBtn);
        fAuth = FirebaseAuth.getInstance();
        UserID = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword = newPasswordET.getText().toString();
                user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(dashboard.this, "Password Reset Successfully \n Redirect to Login page", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(dashboard.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(dashboard.this, "Password reset failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}