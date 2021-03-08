package com.zespr.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapt extends RecyclerView.Adapter<Adapt.VH> {
    private Context c;
    private int resource;
    private ArrayList<Bitmap> photos;

    Adapt(Context c, ArrayList<Bitmap> p, int resource) {
        this.c = c;
        photos = p;
        this.resource = resource;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(resource,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        for(Bitmap i:photos) System.out.println(" >>>> "+i+" (" + position+"/"+photos.size()+")");
        holder.iv.setImageBitmap(photos.get(position));
        return;
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        ImageView iv;
        public VH(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.mview);
        }
    }
}