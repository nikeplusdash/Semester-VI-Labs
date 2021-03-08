package com.zespr.gallery;

import android.app.ActionBar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class FullImage extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_full);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ImageView iv = findViewById(R.id.fview);

        Uri uri = Uri.parse(getIntent().getExtras().getString("uri"));
        Glide.with(getApplicationContext()).load(uri).into(iv);
        iv.setOnTouchListener(new OnSwipeTouchListener(FullImage.this) {
            @Override
            public void onSwipeDown() {
                super.onSwipeDown();
                finish();
            }
        });

    }
}
