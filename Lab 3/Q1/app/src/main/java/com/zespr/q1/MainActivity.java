package com.zespr.q1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gv;
    private int imagesQuantity = 4;
    private Context ctx;
    private Cursor cursor;
    private ArrayList<Bitmap> photos = new ArrayList<>(imagesQuantity);
    private ArrayList<Uri> uris = new ArrayList<>(imagesQuantity);

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(Color.rgb(255,255,255));
        setContentView(R.layout.activity_main);
        gv = findViewById(R.id.gridview);

        String[] projection = new String[]{
                    MediaStore.Images.ImageColumns._ID,
                    MediaStore.Images.ImageColumns.DATA,
                    MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.ImageColumns.DATE_TAKEN,
                    MediaStore.Images.ImageColumns.MIME_TYPE,
        };
        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                    null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
        cursor.moveToFirst();
        System.out.println("> Count: " + cursor.getCount());
        for (int i = 1; i < imagesQuantity; i++) {
            Bitmap bmp = BitmapFactory.decodeFile(cursor.getString(1));
            Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)));
                photos.add(bmp);
                uris.add(imageUri);
            cursor.moveToNext();
            System.out.println(imageUri);
        }
        gv.setAdapter(new ImageAdapter(this));
    }
    public class ImageAdapter extends BaseAdapter {
        private Context c;

        ImageAdapter(Context c) {
            this.c = c;
        }

        @Override
        public int getCount() {
            return imagesQuantity-1;
        }

        @Override
        public Object getItem(int position) {
            return photos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView iv;
            if (convertView == null) {
                iv = new ImageView(c);
                iv.setLayoutParams(new GridView.LayoutParams(400, 400));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                iv = (ImageView) convertView;
            }
            iv.setImageBitmap(photos.get(position));
            return iv;
        }
    }
}