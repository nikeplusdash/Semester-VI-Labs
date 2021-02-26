package com.zespr.q3;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "69";
    private static final int REDUCTION_FACTOR = 5;
    private View view;
    private View wheel;
    private TextView tv;
    private boolean status;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setStatusBarColor(Color.rgb(255,255,255));
        status = true;
        tv = findViewById(R.id.textView);
        view = findViewById(R.id.v2);
        wheel = findViewById(R.id.iv);
        wheel.setDrawingCacheEnabled(true);
        wheel.buildDrawingCache(true);
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(tv.getVisibility() == View.VISIBLE)
                    tv.setVisibility(View.INVISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    tv.setVisibility(View.VISIBLE);
                }, 5000);
                return true;
            }
        });
        view.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                if(!status) return;
                wheel.animate().rotationBy(getEffort()/REDUCTION_FACTOR).setDuration((int)(550000/getEffort())).setInterpolator(new AccelerateDecelerateInterpolator()).start();
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if(!status) return;
                wheel.animate().rotationBy(getEffort()/REDUCTION_FACTOR).setDuration((int)(550000/getEffort())).setInterpolator(new AccelerateDecelerateInterpolator()).start();
            }
            @Override
            public void onSwipeUp() {
                super.onSwipeUp();
                wheel.animate().scaleX(1.5f).scaleY(1.5f).setDuration(175).setInterpolator(new AccelerateDecelerateInterpolator()).start();
                status = true;
            }

            @Override
            public void onSwipeDown() {
                super.onSwipeDown();
                status = false;
                wheel.animate().scaleX(0).scaleY(0).setDuration(175).setInterpolator(new AccelerateDecelerateInterpolator()).start();
            }
        });
        wheel.setOnTouchListener((View v,MotionEvent e)->{
            if(!status) return false;
            bm = wheel.getDrawingCache();
            int pixel = bm.getPixel((int)e.getX(),(int)e.getY());
            int r = Color.red(pixel),g = Color.green(pixel),b = Color.blue(pixel);
            view.setBackgroundColor(Color.rgb(r,g,b));
            MainActivity.this.getWindow().setStatusBarColor(Color.rgb(r,g,b));
            String hex = String.format("#%02x%02x%02x", r, g,b);
            tv.setText(hex.toUpperCase());
            tv.setTextColor(Color.rgb(255-(int)(r/2),(int)(g/2),b));
            return false;
        });
    }
}