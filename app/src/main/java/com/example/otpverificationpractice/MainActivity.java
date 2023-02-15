package com.example.otpverificationpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
/*OTP first part*/
public class MainActivity extends AppCompatActivity {
    EditText enterNumber;
    Button getOtpBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //condition check for if it is a valid phone number or not. is ph no is empty or not
                if(!enterNumber.getText().toString().trim().isEmpty()){
                    //is ph. no. is of 10 digits or not
                    if((enterNumber.getText().toString().trim()).length() == 10) {

                        progressBar.setVisibility(View.VISIBLE);
                        getOtpBtn.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"
                                        + enterNumber.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                MainActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtpBtn.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtpBtn.setVisibility(View.VISIBLE);
                                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtpBtn.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
                                        intent.putExtra("mobile", enterNumber.getText().toString());
                                        intent.putExtra("backendOtp", backendOtp);
                                        //the above variable has the Firebase OTP code sent to next activity for verification
                                        startActivity(intent);
                                    }
                                });
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Enter mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        progressBar=findViewById(R.id.progressbar_sending_otp);
        enterNumber=findViewById(R.id.input_mobile_number);
        getOtpBtn=findViewById(R.id.buttonOtp);
    }
}