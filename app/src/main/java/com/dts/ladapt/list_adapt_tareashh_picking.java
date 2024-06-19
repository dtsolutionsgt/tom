package com.dts.ladapt;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_enc;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_tareashh_picking extends BaseAdapter {

    private static ArrayList<clsBeTrans_picking_enc> BeListTareasHH;

    private int selectedIndex;

    private final LayoutInflater l_Inflater;
    private View convertView;
    private ObjectAnimator animator = null;

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

            holder.lblIdPickingEnc = convertView.findViewById(R.id.lblIdPickingEnc);
            holder.lblBodegaPick = convertView.findViewById(R.id.lblBodegaPick);
            holder.lblPropietarioPick = convertView.findViewById(R.id.lblPropietarioPick);
//            holder.lblUbicacionPick = (TextView) convertView.findViewById(R.id.lblUbicacionPick);
            holder.lblEstadoPick = convertView.findViewById(R.id.lblEstadoPick);
            holder.lblFechaPick = convertView.findViewById(R.id.lblFechaPick);
            holder.lblPrioridad = convertView.findViewById(R.id.lblPrioridad);
//            holder.lblOperadorPick = (TextView) convertView.findViewById(R.id.lblOperadorPick);
//            holder.lblHoraInicial = (TextView) convertView.findViewById(R.id.lblHoraInicial);
//            holder.lblHoraFinal = (TextView) convertView.findViewById(R.id.lblHoraFinal);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }



            holder.lblIdPickingEnc.setText("0");
            holder.lblBodegaPick.setText("0");
            holder.lblPropietarioPick.setText("--");
//            holder.lblUbicacionPick.setText("--");
            holder.lblEstadoPick.setText("--");
            holder.lblFechaPick.setText("1900-01-01");
//            holder.lblOperadorPick.setText("--");
//            holder.lblHoraInicial.setText("00:00:01");
//            holder.lblHoraFinal.setText("00:00:01");

            if (BeListTareasHH.get(position).IdPickingEnc>0){
                holder.lblIdPickingEnc.setText(""+BeListTareasHH.get(position).IdPickingEnc);
            }

            if (!BeListTareasHH.get(position).NombreBodega.isEmpty()){
                holder.lblBodegaPick.setText(BeListTareasHH.get(position).NombreBodega);
            }

            if (!BeListTareasHH.get(position).NombrePropietarioPicking.isEmpty()){
                holder.lblPropietarioPick.setText(BeListTareasHH.get(position).NombrePropietarioPicking);
            }

//            if (!BeListTareasHH.get(position).NombreUbicacionPicking.isEmpty()){
//                holder.lblUbicacionPick.setText(BeListTareasHH.get(position).NombreUbicacionPicking);
//            }

            if (!BeListTareasHH.get(position).Estado.isEmpty()){
                holder.lblEstadoPick.setText(BeListTareasHH.get(position).Estado);
            }

//            if (BeListTareasHH.get(position).Detalle_operador){
//                holder.lblOperadorPick.setText(""+BeListTareasHH.get(position).Detalle_operador);
//            }

            if (!BeListTareasHH.get(position).Fecha_picking.isEmpty()){
                holder.lblFechaPick.setText(""+BeListTareasHH.get(position).Fecha_picking);
            }

        if (!BeListTareasHH.get(position).NombrePrioridad.isEmpty()  && BeListTareasHH.get(position).IdPrioridadPicking != 0){
            holder.lblPrioridad.setText(""+BeListTareasHH.get(position).NombrePrioridad);
        } else {
            holder.lblPrioridad.setText("--");
        }

        int IdPrioridadPicking = BeListTareasHH.get(position).IdPrioridadPicking;
        boolean Tiene_Manufactura = BeListTareasHH.get(position).Tiene_Manufactura;
        int IdPicking = BeListTareasHH.get(position).IdPickingEnc;
        String color1 = "";
        String color2 = "";

        switch (IdPrioridadPicking) {
            case 2:
                color1 = "#FF0399D5";
                if(Tiene_Manufactura) {
                    color2 = "#FF7F50";
                }else{
                    color2 = "#00FFFFFF";
                }
                break;
            case 1:
                color1 = "#FFF9C4";
                if(Tiene_Manufactura) {
                    color2 = "#FF7F50";
                }else{
                    color2 = "#00FFFFFF";
                }
                break;
            default:
                color1 = "#00FFFFFF";
                if(Tiene_Manufactura) {
                    color2 = "#FF7F50";
                }else{
                    color2 = "#00FFFFFF";
                }
                break;
        }

        convertView.setBackgroundColor(Color.WHITE);

        if (IdPrioridadPicking == 0 && !Tiene_Manufactura) {
            if (animator != null && animator.isRunning()) {
                animator.cancel();
            }
            convertView.setBackgroundColor(Color.WHITE);
        }else{
            animator = ObjectAnimator.ofArgb(convertView, "backgroundColor",
                    Color.parseColor(color1), Color.parseColor(color2), Color.parseColor(color1));
            animator.setDuration(1500);
            animator.setRepeatCount(ObjectAnimator.INFINITE);
            animator.start();
        }

        notifyDataSetChanged();

        return convertView;
    }

    static class ViewHolder {
        TextView lblIdPickingEnc,lblBodegaPick,lblPropietarioPick,lblEstadoPick,lblFechaPick, lblPrioridad;
    }

}
