package com.dts.ladapt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_enc;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_licenciaspacking extends RecyclerView.Adapter<list_adapt_licenciaspacking.ViewHolder> {

    private ArrayList<clsBeTrans_packing_enc> items;
    private Context context;
    private int selectedIndex;
    private ClickListener listener;

    public list_adapt_licenciaspacking(Context context, ArrayList<clsBeTrans_packing_enc> items) {
        this.context = context;
        this.items = items;
        this.selectedIndex = -1;
    }

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public void refreshItems() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_adapt_licenciaspacking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        clsBeTrans_packing_enc item = items.get(position);

        holder.lblLicencia.setText(item.No_linea);
        holder.itemView.setBackgroundColor(Color.TRANSPARENT);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position, v);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onItemLongClick(position, v);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(ClickListener l) {
        listener = l;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblLicencia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lblLicencia = itemView.findViewById(R.id.lblLicencia);
        }
    }

    public ArrayList<clsBeTrans_packing_enc> getItems() {
        return items;
    }

    public clsBeTrans_packing_enc getItem(int position) {
        return items.get(position);
    }
}
