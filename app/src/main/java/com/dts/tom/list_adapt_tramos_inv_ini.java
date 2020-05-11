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

import com.dts.classes.Transacciones.Inventario.InventarioTramo.clsBeTrans_inv_tramo;

import java.util.ArrayList;

public class list_adapt_tramos_inv_ini extends BaseAdapter {

    private static ArrayList<clsBeTrans_inv_tramo> BeListTramos;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapt_tramos_inv_ini(Context context, ArrayList<clsBeTrans_inv_tramo> results) {
        BeListTramos = results;
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
        return BeListTramos.size();
    }

    public Object getItem(int position) {
        return BeListTramos.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_tramos_inv_ini, null);
            holder = new ViewHolder();

            holder.lblIdTramo = (TextView) convertView.findViewById(R.id.lblIdTramo);
            holder.lblNombreTramo = (TextView) convertView.findViewById(R.id.lblNombreTramo);
            holder.lblEstadoDetalle = (TextView) convertView.findViewById(R.id.lblEstadoDetalle);
            holder.lblEstadoVeri = (TextView) convertView.findViewById(R.id.lblEstadoVeri);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position==0){

            holder.lblIdTramo.setText("IdTramo");
            holder.lblIdTramo.setTextColor(R.style.titlestyle);
            holder.lblNombreTramo.setText("Nombre");
            holder.lblNombreTramo.setTextColor(R.style.titlestyle);
            holder.lblEstadoDetalle.setText("Detalle");
            holder.lblEstadoDetalle.setTextColor(R.style.titlestyle);
            holder.lblEstadoVeri.setText("Verificación");
            holder.lblEstadoVeri.setTextColor(R.style.titlestyle);

        }else{

            holder.lblIdTramo.setText("0");
            holder.lblNombreTramo.setText("--");
            holder.lblEstadoDetalle.setText("--");
            holder.lblEstadoVeri.setText("--");

            if (BeListTramos.get(position).Idtramo>0){
                holder.lblIdTramo.setText(""+BeListTramos.get(position).Idtramo);
            }

            if (!BeListTramos.get(position).Nombre_Tramo.isEmpty()){
                holder.lblNombreTramo.setText(BeListTramos.get(position).Nombre_Tramo);
            }

            if (!BeListTramos.get(position).Det_estado.isEmpty()){
                holder.lblEstadoDetalle.setText(BeListTramos.get(position).Det_estado);
            }

            if (!BeListTramos.get(position).Res_estado.isEmpty()){
                holder.lblEstadoVeri.setText(BeListTramos.get(position).Res_estado);
            }

        }

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            if (position==0){
                convertView.setBackgroundResource(R.drawable.color_medium);
                holder.lblIdTramo.setTextColor(R.style.titlestyle);
            }else{
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }

        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblIdTramo,lblNombreTramo,lblEstadoDetalle,lblEstadoVeri;
    }
}
