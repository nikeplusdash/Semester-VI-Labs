package com.zespr.feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout name,phone;
    private TextView tv;
    private RatingBar rb;
    private Button b;
    private RadioGroup rg;
    private CheckBox cb,cb2,cb3;
    private String[] Image = {"","☹","🙁","😐","🙂","😊"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rb = findViewById(R.id.ratingBar);
        name = findViewById(R.id.Name);
        phone = findViewById(R.id.Phone);
        b = findViewById(R.id.button);
        tv = findViewById(R.id.heading);
        cb = findViewById(R.id.checkBox);
        cb2 = findViewById(R.id.checkBox2);
        cb3 = findViewById(R.id.checkBox3);
        rg = findViewById(R.id.radioGroup);

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int rate = (int) Math.ceil(rating);
                tv.setText("Rate our service" + Image[rate]);
            }
        });

        b.setOnClickListener(v -> validate());
    }

    private void validate() {
        String ni,pi;
        ni = name.getEditText().getText().toString().trim();
        pi = phone.getEditText().getText().toString().trim();

        if(ni.isEmpty()) {
            name.setError("Enter a valid Name");
            new Handler().postDelayed(()->{
                name.setError(null);
            },2000);
        }
        else if(pi.isEmpty() || !Pattern.matches(String.valueOf(Patterns.PHONE),pi)) {
            phone.setError("Enter a valid Phone Number");
            new Handler().postDelayed(()->{
                phone.setError(null);
            },2000);
        }
        else if(rb.getRating() == 0) {
            Toast.makeText(getApplicationContext(),"Give a rating 😡",Toast.LENGTH_SHORT).show();
        }
        else if(!cb.isChecked() && !cb2.isChecked() && !cb3.isChecked()) {
            Toast.makeText(getApplicationContext(),"Choose at least one gender 😡",Toast.LENGTH_SHORT).show();
        }
        else if(rg.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(),"Are you a child or an adult 😡",Toast.LENGTH_SHORT).show();
        }
        else {
            String text = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString().trim();
            text = text.equals("Child")? "a " + text : "an " + text;
            Toast.makeText(getApplicationContext(), String.format("Thanks for feedback %s\nwho is %s 💋",ni,text),Toast.LENGTH_SHORT).show();
        }
    }
}