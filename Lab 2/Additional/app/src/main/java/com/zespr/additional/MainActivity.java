package com.zespr.additional;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.udojava.evalex.Expression;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ArrayList<Button> b = new ArrayList<>();
    Button equal,c;
    TextView main,left;
    String x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b.addAll(Arrays.asList(
                findViewById(R.id.button0),
                findViewById(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3),
                findViewById(R.id.button4),
                findViewById(R.id.button5),
                findViewById(R.id.button6),
                findViewById(R.id.button7),
                findViewById(R.id.button8),
                findViewById(R.id.button9),
                findViewById(R.id.buttonplus),
                findViewById(R.id.buttonminus),
                findViewById(R.id.buttonmul),
                findViewById(R.id.buttondiv)
        ));
        x = "";
        equal = findViewById(R.id.buttonequal);
        c = findViewById(R.id.buttonC);
        main = findViewById(R.id.main);
        left = findViewById(R.id.left);
        final int[] itr = {0};
        for(Button i: b) {
            i.setOnClickListener(v ->  {
                    Button b = (Button)v;
                    x += b.getText().toString();
                    main.setText(x);
            });
        }
        c.setOnClickListener(v -> {
            x = "";
            main.setText(x);
            left.setText(x);
        });
        equal.setOnClickListener(v -> {
            left.setText(x);
            main.setText("");
            Expression expression = new Expression(x);
            x = "" + expression.eval();
            main.setText(x);
        });
    }
}