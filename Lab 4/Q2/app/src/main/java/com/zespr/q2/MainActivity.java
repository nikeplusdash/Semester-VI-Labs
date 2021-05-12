package com.zespr.q2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Calendar calendar;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = Calendar.getInstance();
        findViewById(R.id.button).setOnClickListener(v -> {
            new TimePickerDialog(this,(timepicker,hr,m)->{
                calendar.set(Calendar.HOUR_OF_DAY,hr);
                calendar.set(Calendar.MINUTE,m);
                calendar.set(Calendar.SECOND,0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                Intent intent = new Intent(this,Receiver.class);
                PendingIntent pintent = PendingIntent.getBroadcast( this, 0, intent, 0 );
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pintent);

                Toast.makeText(this,String.format(Locale.getDefault(),"Alarm set for %s", java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT).format(calendar.getTime())), Toast.LENGTH_SHORT).show();
            }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE), DateFormat.is24HourFormat(this)).show();
        });
    }

    public static class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent i )
        {
            NotificationClass ncx = new NotificationClass(context);
            ncx.sendNotification("You are the reason I was born","Don't you dare swipe me out");
        }
    }
}

class NotificationClass extends ContextWrapper {
    NotificationManager manager;
    public NotificationClass(Context ctx) {
        super(ctx);
        createNotificationChannel();
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("Q2", "AT Lab Channel", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(R.color.purple_500);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            manager.createNotificationChannel(channel);
        }
    }
    public void sendNotification(String title,String text) {
        manager.notify(0,notif(title,text).build());
    }
    private NotificationCompat.Builder notif(String title,String text){
        return new NotificationCompat.Builder(getApplicationContext(),"Q2")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL);
    }
}