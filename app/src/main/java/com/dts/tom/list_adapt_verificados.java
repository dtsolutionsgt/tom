package com.dts.tom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.Transacciones.Inventario.Inventario_Resumen.clsBeTrans_inv_resumen_grid;

import java.util.ArrayList;

public class list_adapt_verificados extends BaseAdapter {

    private static ArrayList<clsBeTrans_inv_resumen_grid> BeListContados;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapt_verificados(Context context, ArrayList<clsBeTrans_inv_resumen_grid> results) {
        BeListContados = results;
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
        return BeListContados.size();
    }

    public Object getItem(int position) {
        return BeListContados.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_verificados, null);
            holder = new ViewHolder();

            holder.lblInvRes = (TextView) convertView.findViewById(R.id.lblInvRes);
            holder.lblEstadoRes = (TextView) convertView.findViewById(R.id.lblEstadoRes);
            holder.lblPresRes = (TextView) convertView.findViewById(R.id.lblPresRes);
            holder.lblCantRes = (TextView) convertView.findViewById(R.id.lblCantRes);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {

            holder.lblInvRes.setText("IdInventarioRes");
            holder.lblInvRes.setTextColor(R.style.titlestyle);
            holder.lblEstadoRes.setText("Estado");
            holder.lblEstadoRes.setTextColor(R.style.titlestyle);
            holder.lblPresRes.setText("Presentación");
            holder.lblPresRes.setTextColor(R.style.titlestyle);
            holder.lblCantRes.setText("Cantidad");
            holder.lblCantRes.setTextColor(R.style.titlestyle);

        }else{

            holder.lblInvRes.setText("0");
            holder.lblEstadoRes.setText("--");
            holder.lblPresRes.setText("--");
            holder.lblCantRes.setText("0");

            if (BeListContados.get(position).Idinventariores > 0) {
                holder.lblInvRes.setText("" + BeListContados.get(position).Idinventariores);
            }

            if (!BeListContados.get(position).productoestado.isEmpty()) {
                holder.lblEstadoRes.setText(BeListContados.get(position).productoestado);
            }

            if (!BeListContados.get(position).presentacion.isEmpty()) {
                holder.lblPresRes.setText(BeListContados.get(position).presentacion);
            }

            if (BeListContados.get(position).Cantidad > 0) {
                holder.lblCantRes.setText("" + BeListContados.get(position).Cantidad);
            }

        }

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            if (position==0){
                convertView.setBackgroundResource(R.drawable.color_medium);
                holder.lblInvRes.setTextColor(R.style.titlestyle);
            }else{
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }

        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblInvRes,lblEstadoRes,lblPresRes,lblCantRes;
    }
}
