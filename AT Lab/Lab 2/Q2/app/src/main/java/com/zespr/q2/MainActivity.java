package com.zespr.q2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et,et2;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.et);
        et2 = findViewById(R.id.et2);
        tv = findViewById(R.id.tv);

        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int x;
                String s = et2.getText().toString(),s2 = et.getText().toString(),s3 = "";
                try {
                    if(s.isEmpty()||s2.isEmpty()) throw new NumberFormatException("Error");
                    x = Integer.parseInt(s);
                }
                catch(NumberFormatException e) {
                    return false;
                }
                run(x,s2);
                return false;
            }
        });
        et2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int x;
                String s = et2.getText().toString(),s2 = et.getText().toString(),s3 = "";
                try {
                    if(s.isEmpty()||s2.isEmpty()) throw new NumberFormatException("Error");
                    x = Integer.parseInt(s)%26;
                }
                catch(NumberFormatException e) {
                    return false;
                }
                run(x,s2);
                return false;
            }
        });
    }

    private void run(int x,String s2) {
        String s3 = "";
        for(int i=0;i<s2.length();i++) {
            char c = s2.charAt(i);
            if(c >= 'a' && c<='z') {
                c+=x;
                if(c >'z') c = (char)(c + 'a' - 'z' - 1);
                s3 += c + "";
            }
            else if(c >= 'A' && c<='Z') {
                c+=x;
                if(c > 'Z') c = (char)(c + 'A' - 'Z' - 1);
                s3 += c + "";
            }
            else s3 += c + "";
        }
        tv.setText(s3);
    }
}