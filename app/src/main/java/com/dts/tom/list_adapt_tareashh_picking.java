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

import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_enc;

import java.util.ArrayList;

public class list_adapt_tareashh_picking extends BaseAdapter {

    private static ArrayList<clsBeTrans_picking_enc> BeListTareasHH;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapt_tareashh_picking(Context context, ArrayList<clsBeTrans_picking_enc> results) {
        BeListTareasHH = results;
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
        return BeListTareasHH.size();
    }

    public Object getItem(int position) {
        return BeListTareasHH.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_tareashh_picking, null);
            holder = new ViewHolder();

            holder.lblIdPickingEnc = (TextView) convertView.findViewById(R.id.lblIdPickingEnc);
            holder.lblBodegaPick = (TextView) convertView.findViewById(R.id.lblBodegaPick);
            holder.lblPropietarioPick = (TextView) convertView.findViewById(R.id.lblPropietarioPick);
            holder.lblUbicacionPick = (TextView) convertView.findViewById(R.id.lblUbicacionPick);
            holder.lblEstadoPick = (TextView) convertView.findViewById(R.id.lblEstadoPick);
            holder.lblOperadorPick = (TextView) convertView.findViewById(R.id.lblOperadorPick);
            holder.lblFechaPick = (TextView) convertView.findViewById(R.id.lblFechaPick);
            holder.lblHoraInicial = (TextView) convertView.findViewById(R.id.lblHoraInicial);
            holder.lblHoraFinal = (TextView) convertView.findViewById(R.id.lblHoraFinal);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position==0){
            holder.lblIdPickingEnc.setText("Código");
            holder.lblBodegaPick.setText("Bodega");
            holder.lblBodegaPick.setTextColor(R.style.titlestyle);
            holder.lblPropietarioPick.setText("Propietario");
            holder.lblPropietarioPick.setTextColor(R.style.titlestyle);
            holder.lblUbicacionPick.setText("Ubicación");
            holder.lblUbicacionPick.setTextColor(R.style.titlestyle);
            holder.lblEstadoPick.setText("Estado");
            holder.lblEstadoPick.setTextColor(R.style.titlestyle);
            holder.lblOperadorPick.setText("DetalleOperador");
            holder.lblOperadorPick.setTextColor(R.style.titlestyle);
            holder.lblFechaPick.setText("Fecha");
            holder.lblFechaPick.setTextColor(R.style.titlestyle);
            holder.lblHoraInicial.setText("HoraInicial");
            holder.lblHoraInicial.setTextColor(R.style.titlestyle);
            holder.lblHoraFinal.setText("HoraFinal");
            holder.lblHoraFinal.setTextColor(R.style.titlestyle);
        }else{

            holder.lblIdPickingEnc.setText("0");
            holder.lblBodegaPick.setText("0");
            holder.lblPropietarioPick.setText("--");
            holder.lblUbicacionPick.setText("--");
            holder.lblEstadoPick.setText("--");
            holder.lblOperadorPick.setText("--");
            holder.lblFechaPick.setText("1900-01-01");
            holder.lblHoraInicial.setText("00:00:01");
            holder.lblHoraFinal.setText("00:00:01");

            if (BeListTareasHH.get(position).IdPickingEnc>0){
                holder.lblIdPickingEnc.setText(""+BeListTareasHH.get(position).IdPickingEnc);
            }

            if (!BeListTareasHH.get(position).NombreBodega.isEmpty()){
                holder.lblBodegaPick.setText(BeListTareasHH.get(position).NombreBodega);
            }

            if (!BeListTareasHH.get(position).NombrePropietarioPicking.isEmpty()){
                holder.lblPropietarioPick.setText(BeListTareasHH.get(position).NombrePropietarioPicking);
            }

            if (!BeListTareasHH.get(position).NombreUbicacionPicking.isEmpty()){
                holder.lblUbicacionPick.setText(BeListTareasHH.get(position).NombreUbicacionPicking);
            }

            if (!BeListTareasHH.get(position).Estado.isEmpty()){
                holder.lblEstadoPick.setText(BeListTareasHH.get(position).Estado);
            }

            if (BeListTareasHH.get(position).Detalle_operador){
                holder.lblOperadorPick.setText(""+BeListTareasHH.get(position).Detalle_operador);
            }

            if (!BeListTareasHH.get(position).Fecha_picking.isEmpty()){
                holder.lblFechaPick.setText(""+BeListTareasHH.get(position).Fecha_picking);
            }

            if (!BeListTareasHH.get(position).Hora_ini.isEmpty()){
                holder.lblHoraInicial.setText(""+BeListTareasHH.get(position).Hora_ini);
            }

            if (!BeListTareasHH.get(position).Hora_fin.isEmpty()){
                holder.lblHoraFinal.setText(""+BeListTareasHH.get(position).Hora_fin);
            }

        }

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            if (position==0){
                convertView.setBackgroundResource(R.drawable.color_medium);
                holder.lblIdPickingEnc.setTextColor(R.style.titlestyle);
            }else{
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }

        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblIdPickingEnc,lblBodegaPick,lblPropietarioPick,lblUbicacionPick,lblEstadoPick,lblOperadorPick,lblFechaPick,lblHoraInicial,lblHoraFinal;
    }

}
