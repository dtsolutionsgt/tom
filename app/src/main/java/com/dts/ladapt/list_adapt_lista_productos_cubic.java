package com.dts.ladapt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_lista_productos_cubic extends RecyclerView.Adapter<list_adapt_lista_productos_cubic.ViewHolder> {

    private ArrayList<clsBeProducto> items;
    private int selectedIndex;
    private final LayoutInflater l_Inflater;

    public list_adapt_lista_productos_cubic(Context context, ArrayList<clsBeProducto> results) {
        this.items = results;
        this.l_Inflater = LayoutInflater.from(context);
        this.selectedIndex = -1;
    }

    // Método para actualizar la lista de elementos
    public void refreshItems(ArrayList<clsBeProducto> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    // Métodos obligatorios del RecyclerView.Adapter

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout de cada elemento
        View itemView = l_Inflater.inflate(R.layout.activity_adapt_lista_productos_cubic, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Asignamos los datos a los componentes de la vista
        holder.lbl1.setText(items.get(position).Stock.getCodigo_Producto());
        holder.lbl2.setText(items.get(position).Stock.getNombre_Producto());

        double vFactorPres = (items.get(position).Stock.getFactor() == 0 ? 1 : items.get(position).Stock.getFactor());
        double vCantidadAUbicar = items.get(position).Stock.getCantidadUmBas() - items.get(position).Stock.CantidadReservadaUMBas;

        if (vFactorPres > 0) {
            vCantidadAUbicar = (vCantidadAUbicar / vFactorPres);
        }

        holder.lbl3.setText(String.valueOf(vCantidadAUbicar));
        holder.lbl4.setText(items.get(position).Stock.getLote());
        holder.lbl5.setText(items.get(position).Stock.getFecha_Vence());

        double vCantidadReservada = items.get(position).Stock.CantidadReservadaUMBas;
        if (vCantidadReservada != 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFDE7"));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Clase interna para el ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lbl1, lbl2, lbl3, lbl4, lbl5;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializamos los TextViews
            lbl1 = itemView.findViewById(R.id.lblCodigoProd);
            lbl2 = itemView.findViewById(R.id.lblProducto);
            lbl3 = itemView.findViewById(R.id.lblCantidad);
            lbl4 = itemView.findViewById(R.id.lblLote);
            lbl5 = itemView.findViewById(R.id.lblFechaVence);
        }
    }
}
