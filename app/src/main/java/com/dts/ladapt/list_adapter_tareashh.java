package com.dts.ladapt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.Transacciones.Recepcion.clsBeTareasIngresoHH;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapter_tareashh extends BaseAdapter {

    private static ArrayList<clsBeTareasIngresoHH> BeListTareasHH;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapter_tareashh(Context context, ArrayList<clsBeTareasIngresoHH> results) {
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

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = l_Inflater.inflate(R.layout.activity_list_adapter_tareashh, null);

            holder = new ViewHolder();

            holder.lblIdOrderCompraEnc = (TextView) convertView.findViewById(R.id.lblIdOC);
            holder.lblIdRecepcionEnc = (TextView) convertView.findViewById(R.id.lblRecepcionEnc);
            holder.lblNoReferenciaOC = (TextView) convertView.findViewById(R.id.lblNoRefOC);
            holder.lblNoDocumentoOc = (TextView) convertView.findViewById(R.id.lblNDocOc);
            holder.lblProveedor = (TextView) convertView.findViewById(R.id.lblProvee);
            holder.lblTipoIngresoOC = (TextView) convertView.findViewById(R.id.lblTipoIng);
            holder.lblTipoRecepcion = (TextView) convertView.findViewById(R.id.lblTipoRece);
            holder.lblNombrePropietario = (TextView) convertView.findViewById(R.id.lblNombrePropietario);
            holder.lblNumPoliza = (TextView) convertView.findViewById(R.id.lblNumPoliza);
            holder.lblNumOrden = (TextView) convertView.findViewById(R.id.lblNumOrden);
            holder.lblRutaDespacho = (TextView) convertView.findViewById(R.id.txtRutaDespacho);
            holder.lblObservacion = (TextView) convertView.findViewById(R.id.txtObservacion);
            holder.lblRequiereTarima = (TextView) convertView.findViewById(R.id.txtRequiereTarima);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.lblIdOrderCompraEnc.setText("0");
        holder.lblIdRecepcionEnc.setText("0");
        holder.lblNoReferenciaOC.setText("--");
        holder.lblNoDocumentoOc.setText("--");
        holder.lblNumOrden.setText("--");
        holder.lblNumPoliza.setText("--");
        holder.lblProveedor.setText("--");
        holder.lblTipoIngresoOC.setText("--");
        holder.lblTipoRecepcion.setText("--");
        holder.lblRutaDespacho.setText("--");
        holder.lblObservacion.setText("--");
        holder.lblRequiereTarima.setText("--");

        if(BeListTareasHH.get(position).IdOrderCompraEnc>0){
            holder.lblIdOrderCompraEnc.setText(""+BeListTareasHH.get(position).IdOrderCompraEnc);
        }
        if(BeListTareasHH.get(position).IdRecepcionEnc>0){
            holder.lblIdRecepcionEnc.setText(""+BeListTareasHH.get(position).IdRecepcionEnc);
        }

        if(BeListTareasHH.get(position).NombrePropietario!=null){
            holder.lblNombrePropietario.setText(BeListTareasHH.get(position).NombrePropietario);
        }

        if(BeListTareasHH.get(position).NoReferenciaOC!=null){
            holder.lblNoReferenciaOC.setText(BeListTareasHH.get(position).NoReferenciaOC);
        }

        if(BeListTareasHH.get(position).NoDocumentoOc!=null){
            holder.lblNoDocumentoOc.setText(BeListTareasHH.get(position).NoDocumentoOc);
        }

        if(BeListTareasHH.get(position).NombreProveedor!=null){
            holder.lblProveedor.setText(BeListTareasHH.get(position).NombreProveedor);
        }

        if(BeListTareasHH.get(position).NombreTipoIngresoOC!=null){
            holder.lblTipoIngresoOC.setText(BeListTareasHH.get(position).NombreTipoIngresoOC);
        }

        if(BeListTareasHH.get(position).NombreTipoRecepcion!=null){
            holder.lblTipoRecepcion.setText(BeListTareasHH.get(position).NombreTipoRecepcion);
        }

        if(BeListTareasHH.get(position).NumOrden!=null){
            holder.lblNumOrden.setText(BeListTareasHH.get(position).NumOrden);
        }

        if(BeListTareasHH.get(position).NumPoliza!=null){
            holder.lblNumPoliza.setText(BeListTareasHH.get(position).NumPoliza);
        }

        if(BeListTareasHH.get(position).RutaDespacho!=null){
            holder.lblRutaDespacho.setText(BeListTareasHH.get(position).RutaDespacho);
        }
        if(BeListTareasHH.get(position).Observacion!=null){
            holder.lblObservacion.setText(BeListTareasHH.get(position).Observacion);
        }

        holder.lblRequiereTarima.setText(BeListTareasHH.get(position).RequiereTarima+"");

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblIdOrderCompraEnc,
                lblIdRecepcionEnc,
                lblNombrePropietario,
                lblNoReferenciaOC,
                lblNoDocumentoOc,
                lblProveedor,
                lblTipoIngresoOC,
                lblTipoRecepcion,
                lblNumOrden,
                lblNumPoliza,
                lblRutaDespacho,
                lblObservacion,
                lblRequiereTarima;
    }

}