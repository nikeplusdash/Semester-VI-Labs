package com.zespr.labq_merged;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    LoginActivity LA;
    private String email, mobile;
    TextView ID, MOB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ID = findViewById(R.id.IDView);
        MOB = findViewById(R.id.MobileView);
        LA = new LoginActivity(this);
        email = LA.getEmail();
        mobile = LA.getMobile();
        ID.setText(email);
        MOB.setText(mobile);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        preferences.removeStatus();
    }
}