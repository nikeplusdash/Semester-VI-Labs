package com.zespr.gallery;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private RecyclerView rvh,rvm;
    private ProgressBar pbh,pbm;
    private ArrayList<Bitmap> photos;
    private ArrayList<Uri> uris,begin;
    private static final String INFO = "100";
    private static final String ERROR = "101";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getPermission(Manifest.permission.READ_EXTERNAL_STORAGE,PERMISSION_REQUEST_CODE);

        rvh = findViewById(R.id.rvh);
        rvm = findViewById(R.id.rvm);
        pbh = findViewById(R.id.pbh);
        pbm = findViewById(R.id.pbm);

        fetch();
        LinearLayoutManager lm = new LinearLayoutManager(this);
        GridLayoutManager gm = new GridLayoutManager(this,4);
        lm.setOrientation(RecyclerView.HORIZONTAL);
        gm.setOrientation(RecyclerView.VERTICAL);

        rvh.setNestedScrollingEnabled(true);
        rvh.setLayoutManager(lm);
        rvm.setLayoutManager(gm);
        rvh.setAdapter(new Adapt(this,begin,begin.size(),0));
        rvm.setAdapter(new Adapt(this,uris,uris.size(),1));
        pbh.setVisibility(View.GONE);
        pbm.setVisibility(View.GONE);
    }
    protected void fetch() {
        Cursor cursor;
        int imagesQuantity;
        String[] projection = new String[]{
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.MIME_TYPE,
        };
        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
        cursor.moveToFirst();
        imagesQuantity = cursor.getCount();
        uris = new ArrayList<>(imagesQuantity);
        begin = new ArrayList<>(5);
        Log.i(INFO,"> Count: " + cursor.getCount());
        for (int i = 1; i <= imagesQuantity; i++) {
            Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)));
            if(i <= 5) begin.add(imageUri);
            else uris.add(imageUri);
            cursor.moveToNext();
            Log.i(INFO,"<URI>: / " + i + " / = " + imageUri);
        }
        Log.i(INFO,begin.size()+" "+uris.size());
        pbh.setVisibility(View.GONE);
        pbm.setVisibility(View.GONE);
        cursor.close();
    }
    public void getPermission(String permission, int code) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {permission},code);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {}
            else {
                Toast.makeText(getApplicationContext(),"Allow access to read memory",Toast.LENGTH_SHORT).show();
            }
        }
    }
}