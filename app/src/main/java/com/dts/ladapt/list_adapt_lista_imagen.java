package com.dts.ladapt;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.dts.tom.R;
import com.dts.tom.Transacciones.ProcesaImagen.ImagenZoom;

import java.util.ArrayList;
import java.util.Objects;

public class list_adapt_lista_imagen extends RecyclerView.Adapter<list_adapt_lista_imagen.ViewHolder> {

    private final Context ctx;
    private final ArrayList<Bitmap> items;

    public list_adapt_lista_imagen(Context ctx, ArrayList<Bitmap> result) {
        this.items = result;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.lista_imagen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /*Glide.with(this.ctx)
                .load(items.get(position))
                .thumbnail()
                .into(holder.imagen);*/
        holder.imagen.setImageBitmap(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen);
        }
    }
}