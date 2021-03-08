package com.zespr.gallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class Adapt extends RecyclerView.Adapter<Adapt.VH> {
    private Context ctx;
    private int count;
    private ArrayList<Uri> photos;
    private int type;
    private int[] layout = {R.layout.image_highlight,R.layout.image_box};
    private int[] view = {R.id.hview,R.id.mview};
    Adapt(Context c, ArrayList<Uri> photos, int count,int type) {
        this.ctx = c;
        this.count = count;
        this.photos = photos;
        this.type = type;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout[type],parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Glide.with(ctx).load(photos.get(position)).diskCacheStrategy(DiskCacheStrategy.DATA).into(holder.iv);
        holder.iv.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), FullImage.class);
            i.putExtra("uri", photos.get(position).toString());
            ctx.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class VH extends RecyclerView.ViewHolder{
        ImageView iv = null;
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public VH(@NonNull View itemView) {
            super(itemView);
                iv = itemView.findViewById(view[type]);
                if(type == 0) iv.setClipToOutline(true);
        }
    }
}