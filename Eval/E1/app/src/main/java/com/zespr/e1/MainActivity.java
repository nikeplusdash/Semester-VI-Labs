package com.zespr.e1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int N;
    private EditText et;
    private Button b;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(Color.rgb(255,255,255));
        setContentView(R.layout.activity_main);
        N = new Random().nextInt(10)+1;
        b = findViewById(R.id.button2);
        et = findViewById(R.id.editTextNumber2);
        System.out.println("> Number: " + N);
        b.setOnClickListener(v -> validate());
    }
    public void validate() {
        try {
            int i = Integer.parseInt(et.getText().toString().trim());
            System.out.println("> Guessed Number: " + i);
            if (i < N) {
                Toast.makeText(getApplicationContext(),"Low",Toast.LENGTH_SHORT).show();;
            } else if (i > N) {
                Toast.makeText(getApplicationContext(),"High",Toast.LENGTH_SHORT).show();;
            }
            else {
                Toast.makeText(getApplicationContext(),"Congrats! You guessed it correct 🥳",Toast.LENGTH_SHORT).show();
                N = new Random().nextInt(10)+1;
                System.out.println("> Number: " + N);
            }
        }
        catch(NumberFormatException e) {
            Toast.makeText(getApplicationContext(),"Enter a valid Number",Toast.LENGTH_SHORT).show();;
        }
        finally {

        }
    }
}