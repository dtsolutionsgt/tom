package com.dts.ladapt;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_enc;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_lista_productos_cubic extends BaseAdapter {

    private ArrayList<clsBeProducto> items;

    private int selectedIndex;

    private final LayoutInflater l_Inflater;

    public list_adapt_lista_productos_cubic(Context context, ArrayList<clsBeProducto> results) {
        items = results;
        l_Inflater = LayoutInflater.from(context);
        selectedIndex = -1;
    }

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public void refreshItems() {
        notifyDataSetChanged();
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_adapt_lista_productos_cubic, null);
            holder = new ViewHolder();

            holder.lbl1 = convertView.findViewById(R.id.lblCodigoProd);
            holder.lbl2 = convertView.findViewById(R.id.lblProducto);
            holder.lbl3 = convertView.findViewById(R.id.lblCantidad);
            holder.lbl4 = convertView.findViewById(R.id.lblLote);
            holder.lbl5 = convertView.findViewById(R.id.lblFechaVence);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.lbl1.setText(items.get(position).Stock.IdUbicacion+"  -   "+items.get(position).Stock.getCodigo_Producto());
        holder.lbl2.setText(items.get(position).Stock.getNombre_Producto());

        double vFactorPres = (items.get(position).Stock.getFactor() == 0 ? 1 : items.get(position).Stock.getFactor());
        double vCantidadAUbicar = items.get(position).Stock.getCantidadUmBas() - items.get(position).Stock.CantidadReservadaUMBas;

        if(vFactorPres > 0) {
            vCantidadAUbicar = (vCantidadAUbicar / vFactorPres);
        }

        holder.lbl3.setText(""+vCantidadAUbicar);

        holder.lbl4.setText(items.get(position).Stock.getLote());
        holder.lbl5.setText(items.get(position).Stock.getFecha_Vence());

        return convertView;
    }

    static class ViewHolder {
        TextView lbl1, lbl2, lbl3, lbl4,lbl5;
    }

}
