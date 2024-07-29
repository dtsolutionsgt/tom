package com.dts.ladapt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.Transacciones.Inventario.Inventario_Detalle.clsBeTrans_inv_detalle_grid;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_contados extends BaseAdapter {

    private static ArrayList<clsBeTrans_inv_detalle_grid> BeListContados;

    private int selectedIndex;

    private final LayoutInflater l_Inflater;

    public list_adapt_contados(Context context, ArrayList<clsBeTrans_inv_detalle_grid> results) {
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
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_contados, null);
            holder = new ViewHolder();

            holder.lblIdInvDet = convertView.findViewById(R.id.lblIdInvDet);
            holder.lblUbicCont = convertView.findViewById(R.id.lblUbicCont);
            holder.lblEstadoCont = convertView.findViewById(R.id.lblEstadoCont);
            holder.lblPresCont = convertView.findViewById(R.id.lblPresCont);
            holder.lblCantCont = convertView.findViewById(R.id.lblCantCont);
            holder.lblPesoCont = convertView.findViewById(R.id.lblPesoCont);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (BeListContados.get(position).Idinventariodet > 0) {
            holder.lblIdInvDet.setText("" + BeListContados.get(position).Idinventariodet);
        }

        if (!BeListContados.get(position).Ubic.isEmpty()) {
            holder.lblUbicCont.setText(BeListContados.get(position).Ubic);
        }

        if (!BeListContados.get(position).productoestado.isEmpty()) {
            holder.lblEstadoCont.setText(BeListContados.get(position).productoestado);
        }

        if (!BeListContados.get(position).presentacion.isEmpty()) {
            holder.lblPresCont.setText(BeListContados.get(position).presentacion);
        }

        if (BeListContados.get(position).Cantidad >= 0) {
            holder.lblCantCont.setText("" + BeListContados.get(position).Cantidad);
        }

        if (BeListContados.get(position).Peso > 0) {
            holder.lblPesoCont.setText("" + BeListContados.get(position).Peso);
        }

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            if (position==0){
                //convertView.setBackgroundResource(R.drawable.color_medium);
                //holder.lblIdInvDet.setTextColor(R.style.titlestyle);
            }else{
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }

        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblIdInvDet,lblUbicCont,lblEstadoCont,lblPresCont,lblCantCont,lblPesoCont;
    }
}
