package com.zespr.additionalq;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.zespr.additionalq.http.HTTPClient;
import com.zespr.additionalq.http.Result;
import com.zespr.additionalq.http.WeatherCallback;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String[] DAYS = {"Su", "M", "Tu", "W", "Th", "F", "Sa"};
    TextView[] tv = new TextView[5];
    TextView[] th = new TextView[5];
    TextView[] tl = new TextView[5];
    ImageView[] iv = new ImageView[5];
    double longitude, latitude;
    long epoch;
    int day;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setStatusBarColor(this.getResources().getColor(R.color.white));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Date date = Calendar.getInstance().getTime();
        Location location = getLastKnownLocation();

        tv[0] = findViewById(R.id.w1);
        tv[1] = findViewById(R.id.w2);
        tv[2] = findViewById(R.id.w3);
        tv[3] = findViewById(R.id.w4);
        tv[4] = findViewById(R.id.w5);
        iv[0] = findViewById(R.id.iv1);
        iv[1] = findViewById(R.id.iv2);
        iv[2] = findViewById(R.id.iv3);
        iv[3] = findViewById(R.id.iv4);
        iv[4] = findViewById(R.id.iv5);
        th[0] = findViewById(R.id.th1);
        th[1] = findViewById(R.id.th2);
        th[2] = findViewById(R.id.th3);
        th[3] = findViewById(R.id.th4);
        th[4] = findViewById(R.id.th5);
        tl[0] = findViewById(R.id.tl1);
        tl[1] = findViewById(R.id.tl2);
        tl[2] = findViewById(R.id.tl3);
        tl[3] = findViewById(R.id.tl4);
        tl[4] = findViewById(R.id.tl5);

        longitude = location.getLongitude();
        latitude = location.getLatitude();
        epoch = date.getTime() / 1000;
        day = date.getDay();

        int[] days = new int[5];
        days[4] = day;
        for (int i = 3; i >= 0; i--) {
            days[i] = (Math.floorMod(days[i + 1] - 1, 7));
        }
        int k = 0;
        for (TextView i : tv) i.setText(DAYS[days[k++]]);
        loadData();
    }

    private void loadData() {
        new HTTPClient(epoch,latitude,longitude,getResources().getString(R.string.weatherAPI)).loadWeather(new WeatherCallback() {
            @Override
            public void onSuccess(Result[] weather) {
                runOnUiThread(() -> showData(weather));
            }

            @Override
            public void onError() {
                // handle error
            }
        });
    }

    private void showData(Result[] weather) {
        int j = 0;
        for(TextView i:th) i.setText(weather[j++].getTemp()+"");
        j = 0;
        for(TextView i:tl) i.setText(weather[j++].getFeels_like()+"");
        j = 0;
        for(ImageView i:iv) Picasso.get().load("https://openweathermap.org/img/w/"+weather[j++].getImg()+".png").into(i);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
//            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }

    private Location getLastKnownLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = lm.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }
}