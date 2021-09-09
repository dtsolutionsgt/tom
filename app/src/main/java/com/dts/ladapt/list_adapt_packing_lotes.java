package com.dts.ladapt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.Transacciones.Inventario.InventarioTramo.clsBeTrans_inv_tramo;
import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_lotes;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_packing_lotes extends BaseAdapter {

    private static ArrayList<clsBeTrans_packing_lotes> items;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapt_packing_lotes(Context context, ArrayList<clsBeTrans_packing_lotes> results) {
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
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_packing_lotes, null);
            holder = new ViewHolder();

            holder.lblLote =  convertView.findViewById(R.id.lblIdTramo);
            holder.lblFecha = convertView.findViewById(R.id.textView102);
            holder.lblCant =  convertView.findViewById(R.id.lblEstadoDetalle);
            holder.lblEst =   convertView.findViewById(R.id.textView121);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.lblLote.setText("Lote : "+items.get(position).lote);
        holder.lblFecha.setText("Vence : "+fechaCorta(items.get(position).fecha));
        holder.lblCant.setText(items.get(position).disp+" / "+items.get(position).cant+" "+items.get(position).presentacion);
        holder.lblEst.setText(""+items.get(position).estado);

        if (selectedIndex != -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblLote, lblFecha, lblCant, lblEst;
    }

    private String fechaCorta(String fecha) {
        try {
            int pp=fecha.indexOf("T");
            return fecha.substring(0,pp);
        } catch (Exception e) {
            return "";
        }
    }
}
