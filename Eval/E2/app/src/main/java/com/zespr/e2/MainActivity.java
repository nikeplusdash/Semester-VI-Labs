package com.zespr.e2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private List<String> categories;
    private ArrayAdapter<String> dataAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ImageButton b1 = findViewById(R.id.add),b2 = findViewById(R.id.sub);
        TextView tv = findViewById(R.id.textInput);

        spinner.setOnItemSelectedListener(this);

        categories = new ArrayList<String>();
        categories.add("i1");
        categories.add("i2");
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        b1.setOnClickListener(v -> {
            String s = tv.getText().toString().trim();
            if(!s.isEmpty())
            dataAdapter.add(s);
            dataAdapter.notifyDataSetChanged();
        });

        b2.setOnClickListener(v -> {
            String s = tv.getText().toString().trim();
            if(!s.isEmpty())
                dataAdapter.remove(s);
            dataAdapter.notifyDataSetChanged();
        });

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}