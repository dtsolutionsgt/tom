package com.dts.tom;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.TextView;

import com.dts.classes.clsBeDetalleCambioUbicacion;

import java.util.ArrayList;

public class list_adapter_detalle_cambio_ubic extends BaseAdapter {

    private static ArrayList<clsBeDetalleCambioUbicacion> items;

    private int selectedIndex;

    private LayoutInflater l_Inflater;


    public list_adapter_detalle_cambio_ubic(Context context, ArrayList<clsBeDetalleCambioUbicacion> results) {
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

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {

            convertView = l_Inflater.inflate(R.layout.activity_list_adapter_detalle_cambio_ubic, null);
            holder = new ViewHolder();

            holder.lblTareaDet  = (TextView) convertView.findViewById(R.id.lblTareaDet);
            holder.lblIdStock = (TextView) convertView.findViewById(R.id.lblIdStock);
            holder.lblCodigo  = (TextView) convertView.findViewById(R.id.lblCodigo);
            holder.lblProducto = (TextView) convertView.findViewById(R.id.lblProducto);
            holder.lblPresentacion = (TextView) convertView.findViewById(R.id.lblPresentacion);
            holder.lblOrigen = (TextView) convertView.findViewById(R.id.lblOrigen);
            holder.lblDestino = (TextView) convertView.findViewById(R.id.lblDestino);
            holder.lblCantidad = (TextView) convertView.findViewById(R.id.lblCantidad);
            holder.lblRecibido = (TextView) convertView.findViewById(R.id.lblRecibido);
            holder.lblOperador = (TextView) convertView.findViewById(R.id.lblOperador);


            convertView.setTag(holder);


        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(26,138,198));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblTareaDet,lblIdStock,lblCodigo,lblProducto,lblPresentacion,lblOrigen,lblDestino,lblCantidad,lblRecibido,lblOperador;
    }

}
