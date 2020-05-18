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

import com.dts.classes.Transacciones.Inventario.Inventario_Detalle.clsBeTrans_inv_detalle_grid;

import java.util.ArrayList;

public class list_adapt_contados extends BaseAdapter {

    private static ArrayList<clsBeTrans_inv_detalle_grid> BeListContados;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

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

            holder.lblIdInvDet = (TextView) convertView.findViewById(R.id.lblIdInvDet);
            holder.lblUbicCont = (TextView) convertView.findViewById(R.id.lblUbicCont);
            holder.lblEstadoCont = (TextView) convertView.findViewById(R.id.lblEstadoCont);
            holder.lblPresCont = (TextView) convertView.findViewById(R.id.lblPresCont);
            holder.lblCantCont = (TextView) convertView.findViewById(R.id.lblCantCont);
            holder.lblPesoCont = (TextView) convertView.findViewById(R.id.lblPesoCont);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {

            holder.lblIdInvDet.setText("IdInventarioDet");
            holder.lblIdInvDet.setTextColor(R.style.titlestyle);
            holder.lblUbicCont.setText("Ubicación");
            holder.lblUbicCont.setTextColor(R.style.titlestyle);
            holder.lblEstadoCont.setText("Estado");
            holder.lblEstadoCont.setTextColor(R.style.titlestyle);
            holder.lblPresCont.setText("Presentación");
            holder.lblPresCont.setTextColor(R.style.titlestyle);
            holder.lblCantCont.setText("Cantidad");
            holder.lblCantCont.setTextColor(R.style.titlestyle);
            holder.lblPesoCont.setText("Peso");
            holder.lblPesoCont.setTextColor(R.style.titlestyle);

        }else{

            holder.lblIdInvDet.setText("0");
            holder.lblUbicCont.setText("--");
            holder.lblEstadoCont.setText("--");
            holder.lblPresCont.setText("--");
            holder.lblCantCont.setText("0");
            holder.lblPesoCont.setText("0");

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

            if (BeListContados.get(position).Cantidad > 0) {
                holder.lblCantCont.setText("" + BeListContados.get(position).Cantidad);
            }

            if (BeListContados.get(position).Peso > 0) {
                holder.lblPesoCont.setText("" + BeListContados.get(position).Peso);
            }

        }

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            if (position==0){
                convertView.setBackgroundResource(R.drawable.color_medium);
                holder.lblIdInvDet.setTextColor(R.style.titlestyle);
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
