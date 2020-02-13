package com.dts.ladapt.CambioUbicacion;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.clsBeCambioUbicacion;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_view_tareas_cambio_ubic extends BaseAdapter {

    private static ArrayList<clsBeCambioUbicacion> items;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_view_tareas_cambio_ubic(Context context, ArrayList<clsBeCambioUbicacion> results) {
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

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(26,138,198));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblTarea,lblMotivo,lblObserva,lblEstado;
        }

}
