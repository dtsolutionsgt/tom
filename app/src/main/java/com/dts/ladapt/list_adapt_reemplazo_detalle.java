package com.dts.ladapt;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dts.classes.Transacciones.Picking.clsBeStockReemplazo;
import com.dts.tom.R;

import java.util.List;

public class list_adapt_reemplazo_detalle extends RecyclerView.Adapter<list_adapt_reemplazo_detalle.ViewHolder> {

    private List<clsBeStockReemplazo> items;
    private boolean isClickable = true;
    private int selectedIndex = -1;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    private OnItemDoubleClickListener doubleClickListener;

    public interface OnItemClickListener {
        void onClick(clsBeStockReemplazo item, int position);
    }

    public interface OnItemLongClickListener {
        void onLongClick(clsBeStockReemplazo item, int position);
    }

    public interface OnItemDoubleClickListener {
        void onDoubleClick(clsBeStockReemplazo item, int position);
    }

    public list_adapt_reemplazo_detalle(List<clsBeStockReemplazo> items) {
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public void setOnItemDoubleClickListener(OnItemDoubleClickListener listener) {
        this.doubleClickListener = listener;
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
        notifyDataSetChanged();
    }

    public void refreshItems(List<clsBeStockReemplazo> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_adapt_detalle_reemplazo_picking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        clsBeStockReemplazo item = items.get(position);

        h.lblCodigoRe.setText(item.Codigo.isEmpty() ? "0" : item.Codigo);
        h.lblProductoRe.setText(item.Producto.isEmpty() ? "--" : item.Producto);
        h.lblPresRe.setText(item.Presentacion.isEmpty() ? "--" : item.Presentacion);
        h.lblUmbasRe.setText(item.UMBas.isEmpty() ? "--" : item.UMBas);
        h.lblCantRe.setText(item.Cant != 0 ? String.valueOf(item.Cant) : "0");
        h.lblUbicRe.setText(item.IdUbicacion != 0 ? item.NombreUbicacion : "0");
        h.lblVenceRe.setText(item.FechaVence.isEmpty() ? "--" : item.FechaVence);
        h.lblLpRe.setText(item.LicPlate.isEmpty() ? "--" : item.LicPlate);
        h.lblLoteRe.setText(item.Lote.isEmpty() ? "--" : item.Lote);
        h.lblCodPrRe.setText(item.CodigoProducto.isEmpty() ? "--" : item.CodigoProducto);
        h.lblPesoRe.setText(item.Peso != 0 ? String.valueOf(item.Peso) : "0");
        h.lblEstadoRe.setText(item.Estado.isEmpty() ? "--" : item.Estado);
        h.lblStock.setText(item.IdStock != 0 ? String.valueOf(item.IdStock) : "0");
        h.lblDespachar.setText(item.Despachar.isEmpty() ? "No" : item.Despachar);

        if (selectedIndex == position) {
            h.itemView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            h.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        h.bindEvents(item, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView lblCodigoRe, lblProductoRe, lblPresRe, lblUmbasRe, lblCantRe, lblUbicRe, lblVenceRe,
                lblLpRe, lblLoteRe, lblCodPrRe, lblPesoRe, lblEstadoRe, lblStock, lblDespachar;

        private long lastClickTime = 0;

        public ViewHolder(@NonNull View v) {
            super(v);
            lblCodigoRe = v.findViewById(R.id.lblCodigoRe);
            lblProductoRe = v.findViewById(R.id.lblProductoRe);
            lblPresRe = v.findViewById(R.id.lblPresRe);
            lblUmbasRe = v.findViewById(R.id.lblUmbasRe);
            lblCantRe = v.findViewById(R.id.lblCantRe);
            lblUbicRe = v.findViewById(R.id.lblUbicRe);
            lblVenceRe = v.findViewById(R.id.lblVenceRe);
            lblLpRe = v.findViewById(R.id.lblLpRe);
            lblLoteRe = v.findViewById(R.id.lblLoteRe);
            lblCodPrRe = v.findViewById(R.id.lblCodPrRe);
            lblPesoRe = v.findViewById(R.id.lblPesoRe);
            lblEstadoRe = v.findViewById(R.id.lblEstadoRe);
            lblStock = v.findViewById(R.id.lblStock);
            lblDespachar = v.findViewById(R.id.lblDespachar);
        }

        public void bindEvents(clsBeStockReemplazo item, int position) {
            itemView.setOnClickListener(v -> {
                if (!isClickable) return;

                isClickable = false;
                itemView.postDelayed(() -> isClickable = true, 500);

                if (clickListener != null) clickListener.onClick(item, position);
            });

            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    longClickListener.onLongClick(item, position);
                    return true;
                }
                return false;
            });
        }
    }
}
