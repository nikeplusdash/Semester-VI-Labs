package com.zespr.labq_merged;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity{
    private LoginActivity LA;
    private TextInputLayout emailLayout, mobileLayout, passwordLayout;
    private Button regButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        LA = new LoginActivity(this);
        if(LA.getStatus()) {
            startMainActivity();
            finish();
            return;
        }
        emailLayout = findViewById(R.id.EmailLayout);
        mobileLayout = findViewById(R.id.MobileLayout);
        passwordLayout = findViewById(R.id.PasswordLayout);
        regButton = findViewById(R.id.RegisterButton);
        progressBar = findViewById(R.id.progressBar);

        regButton.setOnClickListener(v -> validate());
        emailLayout.getEditText().addTextChangedListener(createTextWatcher(emailLayout));
        mobileLayout.getEditText().addTextChangedListener(createTextWatcher(mobileLayout));
        passwordLayout.getEditText().addTextChangedListener(createTextWatcher(passwordLayout));
    }
    private TextWatcher createTextWatcher(TextInputLayout layout){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
    private void validate() {
        String emailID, mobile, password;
        emailID = emailLayout.getEditText().getText().toString();
        mobile = mobileLayout.getEditText().getText().toString();
        password = passwordLayout.getEditText().getText().toString();

        if (emailID.isEmpty() | !Patterns.EMAIL_ADDRESS.matcher(emailID).matches()) {
            emailLayout.setError("Enter a valid Email ID");
        } else if (mobile.isEmpty() | !Patterns.PHONE.matcher(mobile).matches()) {
            mobileLayout.setError("Enter a valid Mobile Number");
        } else if (password.isEmpty()) {
            passwordLayout.setError("Enter a valid Password");
        } else if (!password.equals("admin")) {
            Toast.makeText(getApplicationContext(),"Invalid Password",Toast.LENGTH_SHORT).show();
        }
        else {
            emailLayout.setEnabled(false);
            mobileLayout.setEnabled(false);
            passwordLayout.setEnabled(false);
            regButton.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            LA.setStatus();
            LA.setEmail(emailID);
            LA.setMobile(mobile);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                startMainActivity();
                finish();
            }, 2000);
        }
    }
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
