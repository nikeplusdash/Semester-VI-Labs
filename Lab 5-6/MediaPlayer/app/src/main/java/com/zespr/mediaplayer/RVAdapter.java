package com.zespr.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.VH> {
    private String[] song_names,song_artists;
    private int[] songs_images, songs;
    private MediaPlayer mediaPlayer;
    private MainActivity ctx;

    public RVAdapter(String[] song_names, String[] song_artists, int[] songs_images, int[] songs, MediaPlayer mediaPlayer, MainActivity ctx) {
        this.song_names = song_names;
        this.song_artists = song_artists;
        this.songs_images = songs_images;
        this.songs = songs;
        this.mediaPlayer = mediaPlayer;
        this.ctx = ctx;
    }

    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_list_layout, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(RVAdapter.VH holder, int position) {
        holder.song_image_placeholder.setImageResource(songs_images[position]);
        holder.song_name_placeholder.setText(song_names[position]);
        holder.song_artist_placeholder.setText(song_artists[position]);
        holder.song_layout.setOnClickListener(v -> {
            try {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    Uri mediaPath = Uri.parse("android.resource://" + ctx.getPackageName() + "/" + songs[position]);
                    mediaPlayer.setDataSource(ctx.getApplicationContext(), mediaPath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }
                else {
                    Uri mediaPath = Uri.parse("android.resource://" + ctx.getPackageName() + "/" + songs[position]);
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(ctx.getApplicationContext(), mediaPath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }
                ctx.position = position;
                ctx.setCurrentPlaying(song_names[position],song_artists[position],songs_images[position]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs_images.length;
    }

    public class VH extends RecyclerView.ViewHolder {
        protected TextView song_name_placeholder,song_artist_placeholder;
        protected ImageView song_image_placeholder;
        protected ConstraintLayout song_layout;
        public VH(View itemView) {
            super(itemView);
            song_name_placeholder = itemView.findViewById(R.id.song_name_placeholder);
            song_artist_placeholder = itemView.findViewById(R.id.song_artist_placeholder);
            song_image_placeholder = itemView.findViewById(R.id.song_image_placeholder);
            song_layout = itemView.findViewById(R.id.frameLayout);
        }
    }
}
