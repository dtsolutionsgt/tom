package com.dts.ladapt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.tom.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class list_adapt_lista_productos_recubic extends RecyclerView.Adapter<list_adapt_lista_productos_recubic.ViewHolder> {

    private ArrayList<clsBeVW_stock_res> items;
    private final LayoutInflater inflater;

    public list_adapt_lista_productos_recubic(Context context, ArrayList<clsBeVW_stock_res> results) {
        items = results;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_adapt_lista_productos_cubic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        clsBeVW_stock_res item = items.get(position);
        holder.lbl1.setText(item.Codigo_Producto);
        holder.lbl2.setText(item.Nombre_Producto);

        if (item.IdPresentacion > 0) {
            holder.lbl3.setText(String.valueOf(item.CantidadPresentacion));
        } else {
            holder.lbl3.setText(String.valueOf(item.CantidadReservadaUMBas));
        }

        holder.lbl4.setText(item.Lote);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date;
        try {
            date = dateFormat.parse(item.Fecha_Vence);
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String vFecha = dateFormat.format(date);
            holder.lbl5.setText(vFecha);
        } catch (ParseException e) {
            holder.lbl5.setText("");
        }

        holder.itemView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lbl1, lbl2, lbl3, lbl4, lbl5;

        ViewHolder(View itemView) {
            super(itemView);
            lbl1 = itemView.findViewById(R.id.lblCodigoProd);
            lbl2 = itemView.findViewById(R.id.lblProducto);
            lbl3 = itemView.findViewById(R.id.lblCantidad);
            lbl4 = itemView.findViewById(R.id.lblLote);
            lbl5 = itemView.findViewById(R.id.lblFechaVence);
        }
    }

    public void refreshItems(ArrayList<clsBeVW_stock_res> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }
}
