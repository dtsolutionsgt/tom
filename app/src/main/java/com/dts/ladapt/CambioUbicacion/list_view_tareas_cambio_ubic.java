package com.dts.ladapt.CambioUbicacion;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_enc.clsBeTrans_ubic_hh_enc;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_view_tareas_cambio_ubic extends BaseAdapter {

    private static ArrayList<clsBeTrans_ubic_hh_enc> items;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_view_tareas_cambio_ubic(Context context, ArrayList<clsBeTrans_ubic_hh_enc> results) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {

            convertView = l_Inflater.inflate(R.layout.activity_list_view_tareas_cambio_ubic, null);
            holder = new ViewHolder();

            holder.lblTarea  = (TextView) convertView.findViewById(R.id.lblTarea);
            holder.lblMotivo = (TextView) convertView.findViewById(R.id.lblMotivo);
            holder.lblObserva  = (TextView) convertView.findViewById(R.id.lblObserva);
            holder.lblEstado = (TextView) convertView.findViewById(R.id.lblEstado);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.lblTarea.setText(""+items.get(position).IdTareaUbicacionEnc);
        holder.lblMotivo.setText(items.get(position).DescripcionMotivo);
        holder.lblObserva.setText(items.get(position).Observacion);
        holder.lblEstado.setText(items.get(position).Estado);

        LinearLayout encabezado = (LinearLayout) convertView.findViewById(R.id.encabezado);

        if (position>0){
            encabezado.setVisibility(View.GONE);
        }else{
            encabezado.setVisibility(View.VISIBLE);
        }

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        }else{
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblTarea,lblMotivo,lblObserva,lblEstado;
        }

}
