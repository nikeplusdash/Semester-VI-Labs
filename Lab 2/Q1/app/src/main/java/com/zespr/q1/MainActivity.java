package com.zespr.q1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private String[] groceries = {"Onion","Cabbage","Cucumber","Lettuce","Tomato","Capsicum","Mushrooms","Pumpkin","Potato","Lady finger","Eggplant","Olives"};
    private ListView lv;
    private EditText et;
    private ArrayAdapter<String> adapter;
    private Boolean editable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setStatusBarColor(this.getResources().getColor(R.color.white));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> al = new ArrayList<>();
        editable = false;
        et = findViewById(R.id.tv);
        lv = findViewById(R.id.lv);
        al.addAll(Arrays.asList(groceries));
        adapter = new ArrayAdapter<>(this,R.layout.list_layout,al);

        et.setEnabled(false);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((adapt,view,position,id) -> {
            if(editable) et.setEnabled(false);
            et.setText(adapt.getItemAtPosition(position).toString());
        });
        lv.setOnItemLongClickListener((adapt,view,position,id) -> edit(adapt,position));
    }
    private Boolean edit(AdapterView adapt,int position) {
        editable = true;
        String s = adapt.getItemAtPosition(position).toString();
        et.setText(s);
        et.setEnabled(true);
        et.requestFocus();
        et.setOnKeyListener((v,k,e) -> {
            if(k == 66) {
                adapter.remove(s);
                adapter.insert("" + et.getText(),position);
                adapter.notifyDataSetChanged();
                et.setEnabled(false);
                editable = false;
                return true;
            }
            return false;
        });
        return true;
    }
}