package com.zespr.mediaplayer;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    protected Runnable updateSongDeets;
    protected TextView songName, songArtist, songElapsed, songLeft;
    protected ImageView play_pause, foward, rewind, loop, volume;
    protected ImageView songImage;
    protected SeekBar seekBar, volumeBar;
    protected MediaPlayer mediaPlayer;
    public Handler mediaHandler = new Handler();
    public int startTime = 0, endTime = 0;

    String[] song_names = {"Attack on Titan - Final Season : Ashes on The Fire", "YOUSEEBIGGIRL-T_T", "Shingeki No Kyojin - Vogel im Käfig", "APETITAN", "凸】♀】♂】←巨人", "Saath Nibhana Saathiya"};
    String[] song_artists = {"Kohta Yamamoto", "Sawano Hiroyuki", "Sawano Hiroyuki", "Sawano Hiroyuki", "Sawano Hiroyuki", "Unknown"};
    int[] songs = {R.raw.aots4, R.raw.youseebiggirl, R.raw.vogelimkafig, R.raw.apetitan, R.raw.titansong, R.raw.sns};
    int[] song_images = {R.drawable.aot, R.drawable.aots2, R.drawable.aots1, R.drawable.aots3, R.drawable.aots1, R.drawable.unknown};
    int position = 0;
    int skipTime = 15000;
    int maxVolume = 100;
    boolean mute = false;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        songName = findViewById(R.id.song_name);
        songArtist = findViewById(R.id.song_artist);
        songImage = findViewById(R.id.song_image);
        seekBar = findViewById(R.id.song_bar);
        volumeBar = findViewById(R.id.volumeBar);
        songElapsed = findViewById(R.id.song_elapsed);
        songLeft = findViewById(R.id.song_left);
        play_pause = findViewById(R.id.play_pause);
        foward = findViewById(R.id.forward);
        rewind = findViewById(R.id.rewind);
        loop = findViewById(R.id.loop);
        volume = findViewById(R.id.volume);

        mediaPlayer = MediaPlayer.create(this, songs[position]);
        songName.setText(song_names[position]);
        songName.setSelected(true);
        songArtist.setText(song_artists[position]);
        songImage.setImageResource(song_images[position]);
        endTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();
        seekBar.setMax(endTime);
        volumeBar.setMax(maxVolume);
        volumeBar.setProgress(100);
        setTime();
        mediaPlayer.getDuration();
        RVAdapter rvAdapter = new RVAdapter(song_names, song_artists, song_images, songs, mediaPlayer, MainActivity.this);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mediaPlayer.setOnCompletionListener(mPlayer -> {
            playNext(1);
        });
        mediaPlayer.setOnPreparedListener(mPlayer -> {
            mediaHandler.postDelayed(updateSongDeets, 1000);
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
            }
        });
        play_pause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                play_pause.setImageResource(android.R.drawable.ic_media_play);
            } else {
                mediaPlayer.start();
                play_pause.setImageResource(android.R.drawable.ic_media_pause);
            }
        });
        loop.setOnClickListener(v -> {
            mediaPlayer.seekTo(0);
        });
        foward.setOnClickListener(v -> {
            if (startTime + skipTime < endTime) {
                mediaPlayer.seekTo(startTime + skipTime);
            } else {
                playNext(1);
            }
        });
        foward.setOnLongClickListener(v -> {
            playNext(1);
            return true;
        });
        rewind.setOnClickListener(v -> {
            if (startTime > skipTime) {
                mediaPlayer.seekTo(startTime - skipTime);
            } else {
                playNext(-1);
            }
        });
        rewind.setOnLongClickListener(v -> {
            playNext(-1);
            return true;
        });
        volume.setOnClickListener(v -> {
            volumeBar.setProgress(mute ? 50 : 0);
            float vol = (float) (1 - (Math.log(maxVolume - (mute ? 50 : 0)) / Math.log(maxVolume)));
            mediaPlayer.setVolume(vol, vol);
            mute = !mute;
        });
        volume.setOnLongClickListener(v -> {
            System.out.println("CLICKED");
            volumeBar.animate().alpha(0.7f).scaleX(1f).setDuration(500).setInterpolator(new DecelerateInterpolator()).setListener(null).start();
            volumeBar.setVisibility(View.VISIBLE);
            return true;
        });
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (volumeBar.getScaleX() == 0) return;
                float vol = (float) (1 - (Math.log(maxVolume - progress) / Math.log(maxVolume)));
                mediaPlayer.setVolume(vol, vol);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                volumeBar.setAlpha(1f);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                volumeBar.animate().scaleX(0f).alpha(0f).setDuration(500).setInterpolator(new DecelerateInterpolator()).setListener(null).start();
                volumeBar.setVisibility(View.GONE);
            }
        });
        updateSongDeets = new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                endTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();
                System.out.println(startTime + endTime + " ");
                setTime();
                seekBar.setProgress((int) startTime);
                mediaHandler.postDelayed(this, 1000);
            }
        };
    }

    public void setCurrentPlaying(String name, String artist, int imgRes) {
        songName.setText(name);
        songArtist.setText(artist);
        songImage.setImageResource(imgRes);
        endTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();
        setTime();
        play_pause.setImageResource(android.R.drawable.ic_media_pause);
        seekBar.setClickable(false);
        seekBar.setMax(endTime);
        mediaHandler.postDelayed(updateSongDeets, 1000);
    }

    @SuppressLint("DefaultLocale")
    public void setTime() {
        songElapsed.setText(String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
        );
        songLeft.setText(String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes((long) endTime),
                TimeUnit.MILLISECONDS.toSeconds((long) endTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) endTime)))
        );
    }

    public void playNext(int byAmount) {
        try {
            if (position + byAmount < 0) position = songs.length;
            position = (position + byAmount) % songs.length;
            mediaPlayer.stop();
            mediaPlayer.reset();
            Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + songs[position]);
            mediaPlayer.setDataSource(getApplicationContext(), mediaPath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            setCurrentPlaying(song_names[position], song_artists[position], song_images[position]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}