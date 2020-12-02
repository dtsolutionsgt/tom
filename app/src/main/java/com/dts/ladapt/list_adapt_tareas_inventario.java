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

import com.dts.classes.Transacciones.Inventario.clsBeTrans_inv_enc;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_tareas_inventario extends BaseAdapter {

    private ArrayList<clsBeTrans_inv_enc> pListInv= new ArrayList<clsBeTrans_inv_enc>();

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapt_tareas_inventario(Context context, ArrayList<clsBeTrans_inv_enc> results) {
        pListInv = results;
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
        return pListInv.size();
    }

    public Object getItem(int position) {
        return pListInv.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_tareas_inventario, null);
            holder = new ViewHolder();

            holder.lblIdTarea = (TextView) convertView.findViewById(R.id.lblIdTarea);
            holder.lblTipo = (TextView) convertView.findViewById(R.id.lblTipo);
            holder.lblEstadoInv = (TextView) convertView.findViewById(R.id.lblEstadoInv);
            holder.lblInicio = (TextView) convertView.findViewById(R.id.lblInicio);
            holder.lblTranscurrido = (TextView) convertView.findViewById(R.id.lblTranscurrido);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position==0){

            holder.lblIdTarea.setText("IdTarea");
            holder.lblIdTarea.setTextColor(R.style.titlestyle);
            holder.lblTipo.setText("Tipo");
            holder.lblTipo.setTextColor(R.style.titlestyle);
            holder.lblEstadoInv.setText("Estado");
            holder.lblEstadoInv.setTextColor(R.style.titlestyle);
            holder.lblInicio.setText("Inicio");
            holder.lblInicio.setTextColor(R.style.titlestyle);
            holder.lblTranscurrido.setText("Transcurrido");
            holder.lblTranscurrido.setTextColor(R.style.titlestyle);

        }else{

            holder.lblIdTarea.setText("0");
            holder.lblTipo.setText("--");
            holder.lblEstadoInv.setText("--");
            holder.lblInicio.setText("--");
            holder.lblTranscurrido.setText("--");

            if (pListInv.get(position).Idinventarioenc>0){
                holder.lblIdTarea.setText(""+pListInv.get(position).Idinventarioenc);
            }

            if (pListInv.get(position).Inicial){
                holder.lblTipo.setText("Inicial");
            }else{
                holder.lblTipo.setText("Ciclico");
            }

            if (!pListInv.get(position).Estado.isEmpty()){
                holder.lblEstadoInv.setText(pListInv.get(position).Estado);
            }

            if (!pListInv.get(position).Transcurrido.isEmpty()){
                holder.lblTranscurrido.setText(pListInv.get(position).Transcurrido);
            }



        }

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            if (position==0){
                convertView.setBackgroundResource(R.drawable.color_medium);
                holder.lblIdTarea.setTextColor(R.style.titlestyle);
            }else{
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }

        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblIdTarea,lblTipo,lblEstadoInv,lblInicio,lblTranscurrido;
    }

}
