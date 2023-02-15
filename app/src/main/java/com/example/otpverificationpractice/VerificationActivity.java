package com.example.otpverificationpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
/*OTP verification 2nd part*/
public class VerificationActivity extends AppCompatActivity {
    EditText inputNumber1, inputNumber2, inputNumber3, inputNumber4, inputNumber5, inputNumber6;
    TextView textView, resendLabel;
    Button verifyButtonClick;
    String getOtpBackend;
    ProgressBar progressBarVerifyOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
//        getActionBar().hide();

        init();

        textView.setText(String.format("+91-%s", getIntent().getStringExtra("mobile")));
        //OTP code which we get from firebase backend passed via Main Activity Intent
        getOtpBackend = getIntent().getStringExtra("backendOtp");

        verifyButtonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checks if the user enters all the OTP field or not will proceed only when user had filled all OTP sections
                if (!inputNumber1.getText().toString().trim().isEmpty() && !inputNumber2.getText().toString().trim().isEmpty()
                        && !inputNumber3.getText().toString().trim().isEmpty() && !inputNumber4.getText().toString().trim().isEmpty()
                        && !inputNumber5.getText().toString().trim().isEmpty() && !inputNumber6.getText().toString().trim().isEmpty()) {

                    //OTP which user enters in OTP edit text section
                    String enterCodeOtp = inputNumber1.getText().toString()
                            + inputNumber2.getText().toString()
                            + inputNumber3.getText().toString()
                            + inputNumber4.getText().toString()
                            + inputNumber5.getText().toString()
                            + inputNumber6.getText().toString();

                    if (getOtpBackend != null) {
                        progressBarVerifyOtp.setVisibility(View.VISIBLE);
                        verifyButtonClick.setVisibility(View.INVISIBLE);
// the below methods/lines checks the backend otp and user's current entered otp
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getOtpBackend, enterCodeOtp);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBarVerifyOtp.setVisibility(View.GONE);
                                        verifyButtonClick.setVisibility(View.VISIBLE);
                                        //Apply here the password reset Logic
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(getApplicationContext(), dashboard.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(VerificationActivity.this, "Enter the correct Otp", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(VerificationActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(VerificationActivity.this, "pls enter al number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        numberOtpMove();

        /*Logic for - when user Resend OTP the below will be called */
        resendLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"
                                + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerificationActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(VerificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getOtpBackend=newbackendOtp;
                                Toast.makeText(VerificationActivity.this, "OTP Resent successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    private void init() {
        verifyButtonClick = findViewById(R.id.buttonOtpSubmit);
        inputNumber1 = findViewById(R.id.inputOtp1);
        inputNumber2 = findViewById(R.id.inputOtp2);
        inputNumber3 = findViewById(R.id.inputOtp3);
        inputNumber4 = findViewById(R.id.inputOtp4);
        inputNumber5 = findViewById(R.id.inputOtp5);
        inputNumber6 = findViewById(R.id.inputOtp6);
        textView = findViewById(R.id.textMobileNumberShowTV);
        progressBarVerifyOtp = findViewById(R.id.progressbar_verify_otp);
        resendLabel=findViewById(R.id.textResendOtpTV);
    }

    private void numberOtpMove() {
        inputNumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputNumber2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputNumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputNumber3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputNumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputNumber4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputNumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputNumber5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputNumber5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputNumber6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}